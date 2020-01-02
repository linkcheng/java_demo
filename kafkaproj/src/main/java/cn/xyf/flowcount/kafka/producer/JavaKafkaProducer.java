package cn.xyf.flowcount.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 使用Java代码实现的一个Kafka数据生成者，可以通过该类发送消息数据
 *
 */
public class JavaKafkaProducer {
    // 给定消息发到到topic的名称
    private String topicName;
    // 指定kafka连接的监听ip地址和端口号
    private String brokerList;
    // 指定producer等等kafka返回的结果数
    private int acks = 0;
    // 指定数据发送方式是否是同步
    private boolean isSync = true;

    // 连接kafka集群的producer类
    private KafkaProducer<String, String> producer = null;
    /**
     * 最小参数构造函数
     *
     * @param topicName  给定发送数据的Topic名称
     * @param brokerList 给定连接Kafka的服务器连接参数
     */
    public JavaKafkaProducer(String topicName,  String brokerList) {
        this.topicName = topicName;
        this.brokerList = brokerList;
    }

    /**
     * 构造函数
     *
     * @param topicName                   给定发送数据的Topic名称
     * @param brokerList                  给定连接Kafka的服务器连接参数
     * @param acks                        等等KafkaServer返回的结果值
     */
    public JavaKafkaProducer(String topicName,  String brokerList, int acks) {
        this.topicName = topicName;
        this.brokerList = brokerList;
        if (acks == 0 || acks == 1 || acks == -1) {
            // acks 的参数只支持-1,0,1
            this.acks = acks;
        }
        // 进行初始化操作
        this.initialJavaProducer();
    }

    /**
     * 进行连接kafka集群的producer初始化操作， 构建一个Producer对象
     * http://kafka.apache.org/10/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html
     */
    public void initialJavaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", this.brokerList);
        props.put("acks", this.acks);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
    }

}
