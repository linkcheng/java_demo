package cn.xinyongfei.flowcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 输入的 k-v 是 mapper 的输出类型
 * 输出的 k-v 是 reducer 的输入类型
 */
public class FlowCountCombiner extends Reducer<Text, FlowWritable, Text, FlowWritable> {
    private FlowWritable outputValue = new FlowWritable();

    @Override
    protected void reduce(Text key, Iterable<FlowWritable> values, Context context) throws IOException, InterruptedException {
        long totalUpFlow = 0;
        long totalDownFlow = 0;

        for(FlowWritable value: values) {
            totalUpFlow += value.getUpFlow();
            totalDownFlow += value.getDownFlow();
        }
        outputValue.set(totalUpFlow, totalDownFlow);
        context.write(key, outputValue);
    }
}
