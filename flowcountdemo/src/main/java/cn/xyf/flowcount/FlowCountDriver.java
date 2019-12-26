package cn.xyf.flowcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        args = new String[] {
            "hdfs://bigdata.example.com/test.data",
            "hdfs://bigdata.example.com/res1",
        };
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);

        // 设置程序主入口
        job.setJarByClass(FlowCountDriver.class);

        // 指定开启 combiner 功能，并指定 combiner 类
        job.setCombinerClass(FlowCountCombiner.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 设置自定义分区类
        job.setPartitionerClass(ProvinceIDPartitioner.class);
        // 设置分区数
        job.setNumReduceTasks(5);

        // 设置 map 类
        job.setMapperClass(FlowCountMapper.class);
        // 设置 reduce 类
        job.setReducerClass(FlowCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputKeyClass(FlowWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowWritable.class);

        int result = job.waitForCompletion(true) ? 0 : 1;

        System.exit(result);
    }
}
