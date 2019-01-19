package cn.xinyongfei.flowcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;


/**
 * Partitioner 的泛型类型是 map 输出的 key-value 类型
 */
public class ProvinceIDPartitioner extends Partitioner<Text, FlowWritable> {

    static HashMap<String, Integer> provinceDict = new HashMap<String, Integer>();
    static {
        provinceDict.put("136", 0);
        provinceDict.put("137", 1);
        provinceDict.put("138", 2);
        provinceDict.put("139", 3);
    }

    /**
     *
     * @param key: map 输出的 key
     * @param value: map 输出的 value
     * @param numPartitions
     * @return
     * @note 调用非常频繁，来一个 key 调用一次
     */
    public int getPartition(Text key, FlowWritable value, int numPartitions) {
        String phoneNumberString = key.toString();
        String prefix = phoneNumberString.substring(3);
        Integer provinceID = provinceDict.get(prefix);

        return provinceID == null ? 4 : provinceID;
    }
}
