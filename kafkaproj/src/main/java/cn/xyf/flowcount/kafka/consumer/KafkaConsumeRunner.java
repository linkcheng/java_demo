package cn.xyf.flowcount.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Slf4j
public class KafkaConsumeRunner {
    private static String GROUP_ID = "simple";
    private static String BOOTSTRAP_SERVERS = "192.168.0.44:9092";
    private static String topic = "streams-plaintext-input";

    public static Properties getProps() {
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // earliest, none
        return props;
    }

    public static <S, T> void consume(KafkaConsumer<S, T> consumer) {
        try {
            while (true) {
                ConsumerRecords<S, T> records = consumer.poll(Duration.ofSeconds(2));
                for (ConsumerRecord<S, T> record: records) {
                    log.info("Key="+record.key()+", Value="+record.value()+", Partition="+record.partition()+", Offset="+record.offset());
                }
            }
        } finally {
            consumer.close();
        }
    }

    public static void simpleConsume() {
        Properties props = getProps();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(topic));

        consume(consumer);
    }

    public static void seekPartitionConsume() {
        Properties props = getProps();
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test1122");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        if(null != partitionInfos) {
            List<TopicPartition> topicPartitions = new ArrayList<>();
            for(PartitionInfo partitionInfo: partitionInfos) {
                TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
                topicPartitions.add(topicPartition);
            }

            consumer.assign(topicPartitions);

            for(TopicPartition topicPartition: topicPartitions) {
                consumer.seek(topicPartition, 20L);
            }
        }

        consume(consumer);
    }

    public static void main(String[] args) {
//        simpleConsume();

        seekPartitionConsume();
    }
}
