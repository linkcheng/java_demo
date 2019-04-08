package cn.xinyongfei.flowcount;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/**
 * 大小写转换，0 代表转小写，1 代表转大写，默认转换为小写
 */
public class BigDataUDF extends UDF{
    public Text evaluate(Text str) {
        return this.evaluate(str, new IntWritable(0));
    }

    public Text evaluate(Text str, IntWritable flag) {
        if(str != null) {
            if(flag.get() == 0) {
                return new Text(str.toString().toLowerCase());
            } else if (flag.get() == 1) {
                return new Text(str.toString().toUpperCase());
            } else {
                return str;
            }
        } else {
            return null;
        }
    }

}
