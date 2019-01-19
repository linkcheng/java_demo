package com.xinyongfei.core

import java.sql.DriverManager

import org.apache.spark.{SparkConf, SparkContext}

object LogPVCount {
  def main(args: Array[String]): Unit = {
    // 1. 创建 SparkContext
    val conf = new SparkConf()
      .setAppName("LogPV")
//      .setMaster("local")
    val sc = new SparkContext(conf)

    // 2. 基于 sc 创建 RDD
    val path = "/data/spark/core/info.log"
    val rdd = sc.textFile(path)
//    rdd.count()

//    // 优化
//    val mrRdd = rdd.map(line => line.split("\t"))
//      .filter(arr => arr.length>=3 && arr(0).trim.length>10)
//      .map(arr => {
//        val date = arr(0).trim.substring(0, 10)
//        val url = arr(1).trim
//        val guid = arr(2).trim
//        (date, url, guid)
//      })
//    mrRdd.cache()
//    // pv
//    val prePV = mrRdd.filter(t => t._2.nonEmpty).map(t => (t._1, 1))
//    // uv
//    val preUV = mrRdd.filter(t => t._3.nonEmpty).map(t => (t._1, t._3))

    // 3. pv
//    val rdd1 = rdd.map(line => line.split("\t"))
//      .filter(arr => arr.length>=2 && arr(1).nonEmpty && arr(0).trim.length>10)
//      .map(arr => {
//        val date = arr(0).trim.substring(0, 10)
//        val url = arr(1).trim
//        (date, url)
//      })
//    //逻辑1.groupByKey
//    val pvRdd1 = rdd1.groupByKey()
//      .map(t => {
//        val date = t._1
//        val pv = t._2.size
//        (date, pv)
//      })
//    pvRdd1.collect()
    // 逻辑2.reduceByKey
    val rdd2 = rdd.map(line => line.split(" "))
      .filter(arr => arr.length>=2 && arr(1).nonEmpty && arr(0).trim.length>10)
      .map(arr => {
        val date = arr(0).trim.substring(0, 10)
        (date, 1)
      })
    val pvRdd = rdd2.reduceByKey(_+_)
    pvRdd.saveAsTextFile("/data/spark/coreResult")
    println("========================" + pvRdd.collect().mkString(";"))

//    // 4.uv
//    val rdd3 = rdd.map(line=>line.split("\t"))
//      .filter(arr => arr.length>=3 && arr(2).nonEmpty && arr(0).trim.length>10)
//      .map(arr => {
//        val date = arr(0).trim.substring(0,10)
//        val guid = arr(2).trim
//        (date, guid)
//      })
//    // 逻辑1.全部放到内存 set 中，容易 OOM
//    val rdd4 = rdd3.groupByKey()
//      .map(t => {
//        val date = t._1
//        val guids = t._2
//        val uv = guids.toSet.size
//        (date,uv)
//      })
//    // 逻辑2.reduceByKey
//    val rdd5 = rdd3.map(t => ((t._1, t._2), null))
//      .reduceByKey((a, b) => a)
//      .map(t => (t._1._1, 1))
//      .reduceByKey(_+_)
//    // 逻辑3.distinct
//    val uvRdd = rdd3.distinct()
//      .map(t => (t._1, 1))
//      .reduceByKey(_+_)
//
//    println("======================" + uvRdd.collect().mkString(";"))


//    // 5.合并结果
//    val pvuvRdd = pvRdd.fullOuterJoin(uvRdd)
//      .map(t => {
//        val date = t._1
//        val pv = t._2._1.getOrElse(0)
//        val uv = t._2._2.getOrElse(0)
//        (date, pv, uv)
//      })

//    // 6.输出结果
//    // 输出 Driver，返回数据的大小要求不能超过 spark.driver.maxResultSize 的大小，默认 1G
//    val result: Array[(String, Int, Int)] = pvuvRdd.collect()
//    println(result)
//    // 写入 HDFS
//    pvuvRdd.saveAsTextFile("hdfs:///data/spark/dir")
//    // 写入数据库
//    pvuvRdd.foreachPartition(iter => {
//      // -1 建立数据库连接对象
//      val conn = DriverManager.getConnection("", "", "")
//      // -2 创建数据输出 prepareStatement 对象
//      val ps = conn.prepareStatement("")
//      // -3 数据迭代输出
//      iter.foreach(t => {
//        val date = t._1
//        val pv = t._2
//        val uv = t._3
//        ps.setString(1, date)
//        ps.setInt(2, pv)
//        ps.setInt(3, uv)
//      })
//      // -4 关闭数据库连接
//      ps.close()
//      conn.close()
//    })
  }
}
