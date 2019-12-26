package cn.xyf.flowcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowWritable> {
    private Text outputKey = new Text();
    private FlowWritable outputValue = new FlowWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split("\t");

        String mobile = fields[0];
        Long upFlow = Long.valueOf(fields[7]);
        Long downFlow = Long.valueOf(fields[8]);

        outputKey.set(mobile);
        outputValue.set(upFlow, downFlow);

        context.write(outputKey, outputValue);
    }
}
