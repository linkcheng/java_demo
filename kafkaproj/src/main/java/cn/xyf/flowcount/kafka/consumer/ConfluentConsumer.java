package cn.xyf.flowcount.kafka.consumer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * ConfluentConsumer.java
 * 使用Confluent实现的Schema Registry服务来消费Avro序列化后的对象
 */
public class ConfluentConsumer {
    private static String ip = "192.168.0.44";
//    private static String ip = "127.0.0.1";
    public static void consume() {
//        AvroConverter a = AvroConverter();
        Properties props = new Properties();
        props.put("bootstrap.servers", ip + ":9092");
        props.put("group.id", "dev-group3");
        props.put("key.deserializer", KafkaAvroDeserializer.class.getName());
        // 使用Confluent实现的KafkaAvroDeserializer
        props.put("value.deserializer", KafkaAvroDeserializer.class.getName());
        // 添加schema服务的地址，用于获取schema
        props.put("schema.registry.url", "http://" + ip + ":8081");
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<>(props);

//        consumer.subscribe(Collections.singletonList("common.common.user_menu"));
        consumer.subscribe(Collections.singletonList("common.common.user_menu"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {

            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                Map<TopicPartition,Long> beginningOffset = consumer.beginningOffsets(collection);

                //读取历史数据 --from-beginning
                for(Map.Entry<TopicPartition,Long> entry : beginningOffset.entrySet()){
                    // 基于seek方法
                    //TopicPartition tp = entry.getKey();
                    //long offset = entry.getValue();
                    //consumer.seek(tp,offset);

                    // 基于seekToBeginning方法
                    consumer.seekToBeginning(collection);
                }
            }
        });

        try {
            for (;;) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(1000);
                for (ConsumerRecord<String, GenericRecord> record : records) {
                    GenericRecord item = record.value();
                    System.out.println("value = [before=" + item.get("before")
                            + ", after=" + item.get("after")
                            + ", source=" + item.get("source")
                            + ", op=" + item.get("op")
                            + ", ts_ms=" + item.get("ts_ms")
                            + "], partition = " + record.partition() + ", offset = " + record.offset());
//                    System.out.println("value = [user.id = " + user.get("id") + ", " + "user.name = "
//                            + user.get("name") + ", " + "user.age = " + user.get("age") + "], "
//                            + "partition = " + record.partition() + ", " + "offset = " + record.offset());
                }
            }
        } finally {
            consumer.close();
        }

    }

    public static int maxSum(int[] arr, int end) {
        int sum = 0, tmp = 0;
        for (int i = 0; i < end; i++) {
            if (tmp > 0) {
                tmp += arr[i];
            } else {
                tmp = arr[i];
            }

            if (tmp > sum) {
                sum = tmp;
            }
        }

        return sum;
    }

    public static void main(String[] args) {
//        int[] arr = {1, -3, 7, 8, -4, 12, -10, 6};
//        int start = 0;
//        int length = arr.length;
//        int sum = maxSum(arr, length - 1);
//        System.out.println(sum);

        consume();
    }
}