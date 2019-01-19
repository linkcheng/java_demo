package com.xinyongfei.core

import org.apache.spark.rdd.{RDD, ShuffledRDD}
import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object GroupSortedTopN {
  def main(args: Array[String]): Unit = {
    // 1. SparkContext 上下文创建
    val conf = new SparkConf()
      .setAppName("group sorted")
    // 一个 spark 应用中只能有一个 driver, SparkContext 构建的地方就是 driver 进程，
    // 要求JVM 中 SparkContext 对像只允许存在一个，否则报错
    // val sc = new SparkContext(conf)
    val sc = SparkContext.getOrCreate(conf)

    // 2. 读取数据形成RDD
    //由于没有core-site.xml和hdfs-site.xml等相关配置,而且给定的path路径没有给定明确的schema，
    // 那么使用默认文件系统，也就是本地文件系统
    //这里是一个相对路径，也就是相对于当前的项目的路径
    val path = "datas/groupsort.txt"
    val rdd = sc.textFile(path)

    //3 . 将RDD转换为key/value键值对rdd，并缓存
    val mapredRDD = rdd.map(line => line.split(" "))
        .filter(arr => arr.length==2)
        .map(arr => (arr(0).trim, arr(1).trim.toInt))
    mapredRDD.cache()

    val K = 3
    // 4.1 分区排序TopN 实现方式一
    /**
      * 思路：先按照单词（第一个字符）分组，然后对分组之后的数据进行处理，每组数据（第二个元素）形成的列表或者迭代器，
      * 进行排序操作，排序后获取数据最大的前三个值
      * 缺点1：使用groupByKey API在当前版本的实现中，相同key的数据形成的迭代器在后续的处理中会全部加载到内存中，
      * 所以当groupByKey操作过程中，如果一个key的数据特别多的情况下，可能会出现数据倾斜或者OOM异常
      * 缺点2：在同组key进行聚合的业务场景中，groupByKey的性能有点低，因为groupByKey没有先进行分区数据的一个聚合（减少shuffle的数据量），
      * 而groupByKeyAPI会将所有的数据进行shuffle操作，在聚合的业务场景中，shuffle数据有部分数据是一定不需要考虑的==>
      * 实际上在聚合的业务场景中，一个分区中，可能只有部分数据需要进行考虑
      */
    val result1 = mapredRDD.groupByKey()
      .map(tuple => {
        val key = tuple._1
        val values = tuple._2
        val topKValues = values.toList.sorted.takeRight(K).reverse
        (key, topKValues)
      })
    println(s"方式一:\n${result1.collect().mkString("\n")}")


    //4.2 分区排序TopN 实现方式二 ==》 解决groupByKey缺点1 ==》 分两阶段聚合（PPT52-53）
    /**
      * 思路：第一步，给key加一个随机前缀i,然后做局部的聚合;第二步，将随机前缀去掉，然后做一个全局的聚合操作
      */
    //driver中定义一个random变量，在executor中使用这个变量，要求将这个变量传递非executor，
    // 也就是说需要这个变量是可以进行序列化的(网络传输所有的对象必须是可序列化对象)，
    // 在scala2.11.0版本之前的random是不可以进行序列化操作，所以不能传递，在2.11.0之后的版本random是可以序列化的，
    // 所以这个取决于你使用的scala的版本
    /*    val random = Random //一个随机前缀
        val rdd1 = mapredRDD.map(t => {
          ((random.nextInt(10), t._1), t._2)
        }).take(10)
        rdd1.foreach(println(_))*/

    val result2 = mapredRDD
      .mapPartitions(iter => {
        val random = Random //这段代码的执行是在executor中间执行，不需要远程传递
        iter.map(t => ((random.nextInt(10), t._1), t._2))
      })
      .groupByKey()
      .flatMap{
        case ((_ ,key),values) => {
          //获取values中的前K个值,并返回==》局部聚合
          values.toList
            .sorted //默认升序
            .takeRight(K)
            .map(value => (key, value))
        }
      }
      .groupByKey()
      .map{
        case (key,values) => {
          val topKValues = values.toList.sorted.takeRight(K).reverse
          //返回结果
          (key,topKValues)
        }
      }

    println(s"方式二:\n${result2.collect().mkString("\n")}")
    //备注：方式二是先做一个局部的聚合，然后在做一个全局的聚合，至于随机数前缀是多少，则根据业务来定
    //如果在线上的时候某个task运行很慢，则考虑数据倾斜，例如90%的数据key为abc，其余10%数据为其他，那么可以考虑对90%的数据做随机前缀
    /**
      * val rdd : RDD[(String,Int)]= xxxx
      * rdd.cache
      * val rdd1 = rdd.filter(t=> t._1 == "abc")
      * val rdd2 = rdd.filter(t=> t._1 != "abc")
      * val result1: RDD[(String,List[Int])]= rdd1.mapPartitions(...).groupByKey().flatMap().groupByKey().map()
      * val result2: RDD[(String,List[Int])]=rdd2.groupByKey().map()
      * val finalResult = result1.union(result2)
      */


    //4.3 分区排序TopN 实现方式三 ==》 解决groupByKey缺点1和缺点2,在调用groupByKey之前能不能先做数据的聚合？

    /*    mapredRDD.mapPartitions(iter => {
          //这种实现方式，如果一个分区中key太多，有可能会出现内存溢出
          //TODO:感兴趣的自己实现
          import scala.collection.mutable
          iter.foldLeft(mutable.Map[String,mutable.ArrayBuffer[Int]]())((a,b) => {
            //把b添加到a中，如果a中对应key的数据数量小于K个，直接添加；如果大于等于K个，并且b的value的值比a中对应buffer中的数据的value大的，则使用b替换对应的buffer中的最小值
            a
          }).toIterator
        })
        //备注：上述这个操作求出每个分区的每个key的topK的值，然后直接进行groupByKey操作即可*/


    /**
      * 思路：在shuffle之前首先对每个分区做一次分组排序topN的操作（可以降低shuffle的数据量）--》类似combiner
      * def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U,
      * combOp: (U, U) => U)
      * zeroValue:初始值，每组key对应的初始值，即最初的聚合值
      * seqOp：对每组key对应的value数据进行迭代处理，将value和之前的聚合值进行聚合操作，并返回新的聚合值；
      * U就是聚合值对象类型，V就是value的数据类型 --》 该函数在每个分区进行数据聚合的时候被调用，也就是shuffle传输数据之前
      * ==> 在处理每个分区数据的时候会触发该方法
      * 该方法属于shuffle的一个阶段，所以如果内存不足的时候就会保存数据到磁盘，所以一定不会出现内存溢出的错误
      * combOp：对两个分区的聚合值进行聚合操作，返回一个新的聚合值
      * ==> 在shuffle发送数据之后，对多个分区的返回结果进行聚合的时候会触发调用
      * ==> 用于多个分区数据的合并;该API在内存不够的情况下，有可能在shuffle之前触发（会将seqOp聚合之后的数据保存到磁盘，
      * 然后再shuffle发送数据之前，会将所有seqOp操作输出U对象进行一个合并操作）
      *
      */

    //    mapredRDD.reduceByKey() --这个没法做
    import scala.collection.mutable
    val result3 = mapredRDD.aggregateByKey(mutable.ArrayBuffer[Int]())(
      (u,v) => {
        //对当前key的value数据v进行聚合操作 ==> 将v合并到u中
        //业务要求：获取当前key后对应的topK的value值，也就是u中保存的是最大的K个value值
        // u:当前key对应的已有上一个操作的聚合值（上一个操作后的结果或者初始值）
        // v:当前key对应的一个value数据
        u += v
        u.sorted.takeRight(3)
      },
      (u1,u2) => {
        //对任意两个局部的聚合值进行聚合操作，可能发生在combiner阶段和shuffle之后的最终数据聚合阶段
        u1 ++= u2
        u1.sorted.takeRight(3)//.reverse
      }
    ).map(t => (t._1,t._2.toList.reverse))
    println(s"方式三:\n${result3.collect().mkString("\n")}")



    //4.4 分区排序TopN 实现方式四 ==》 使用"二次排序"来实现
    //TODO:Spark没有专门的二次排序功能，所以二次排序加引号，实质上需要理解二次排序的原理，然后再对数据进行编码实现类似二次排序的效果出来
    //二次排序(Mapreduce中的专有名词)，在数据处理过程中，同组数据中，按照value进行数据排序 ==》 重点：同组数据中，value有序
    //隐含的内容:-1. 同组数据在一个分区中； -2. 数据先按照key进行排序，然后再按照value进行排序； -3. 数据是有序的； -4. 一个分区中的数据也是有序的； -5. 同一个分区中间，key相同的数据在一起（连续存在）-》同组数据是连续存在的
    //基于自定义的一个数据分区器即可完成任务 ==》 数据分区器的功能是：决定数据在shuffle操作后在哪一个分区中，和MapReduce的partitioner一样，spark默认分区器为HashPartitioner

    //得到一个默认的排序器，分区设置为2
    val partitioner = new GroupSortedPartitioner(2)

    //按照key进行排序,sortByKey源码
    //    mapredRDD.sortByKey()

    val ordering: Ordering[(String, Int)] = implicitly[scala.math.Ordering[(String, Int)]] //获取一个隐式转换对象
    val tmpRDD: RDD[((String, Int), None.type)] = mapredRDD.map(t => (t,None))
    val tmpRDD2 = new ShuffledRDD[(String,Int), Option[_], Option[_]](tmpRDD, partitioner)
      .setKeyOrdering(ordering.reverse)  //必须给定数据排序器

    val result4 = tmpRDD2.mapPartitions(iter => {
      //对当前分区数据进行统计，备注，一组数据一定在一个分区中，并且数据是连续的（递减的）
      //如果key的数量太多，那么就有可能出现内存溢出的问题 ==》 可以根据数据特征定义自定义的分区器来解决该问题
      import scala.collection.mutable //在进行下一步之前先构建一个case class PreValueMode
      iter
        .foldLeft((PreValueMode("",0), mutable.ArrayBuffer[(String, Int)]()))((a,b) => {
          //处理b的数据
          val preValueModel = a._1
          val buffer = a._2

          val currentKey = b._1._1
          val currentCount = b._1._2

          if(preValueModel.preKey == currentKey){ //表示是同一组key，而且已经开始进行处理了
            if(preValueModel.count >= K){
              //表示已经获取同组的前K个数据了 ==》 不需要任何操作
              //nothings
            } else {
              //表示还没有获取K个元素，直接将当前对象b添加到buffer中
              buffer += currentKey -> currentCount
              preValueModel.count += 1
            }

          } else {  //表示是一个新一组的数据进行处理操作，表示当前组还没有获取一条数据
            buffer += currentKey -> currentCount
            preValueModel.count = 1
            preValueModel.preKey = currentKey
          }

          //返回结果
          (preValueModel,buffer)
          //        a
        })
        ._2
        .toIterator
    })

    println(s"方式四:\n${result4.collect().mkString("\n")}")

    //缓存的rdd不使用后，进行清除缓存操作
    mapredRDD.unpersist()
  }

}


case class PreValueMode(var preKey: String, var count: Int)


class GroupSortedPartitioner(num: Int) extends Partitioner{
  //分区数量
  override def numPartitions: Int = num

  lazy val proxy = new HashPartitioner(numPartitions)

  override def getPartition(key: Any): Int = {
    key match {
      case (k, v) => {
        //由第一个元素值决定数据在哪一个分区
        proxy.getPartition(k)
      }
      case _ => 0
    }
  }
}