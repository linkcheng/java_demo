package cn.xyf.flowcount.kafka.consumer;

import common.common.user_menu.Envelope;
import common.common.user_menu.Key;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

;

/**
 * ConfluentConsumer.java
 * 使用Confluent实现的Schema Registry服务来消费Avro序列化后的对象
 */
@Slf4j
public class KafkaAvroConsumer {
    private static String GROUP_ID = "avro_demo";
    private static String BOOTSTRAP_SERVERS = "192.168.0.44:9092";
    private static String SCHEMA_REGISTRY_URL = "http://192.168.0.44:8081";
    private static String topic = "common.common.user_menu";

    public static Properties getProps() {
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // earliest, none
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
        return props;
    }

    public static void genericConsume() {
        Properties props = getProps();

        KafkaConsumer<GenericRecord, GenericRecord> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        try {
            for (;;) {
                ConsumerRecords<GenericRecord, GenericRecord> records = consumer.poll(Duration.ofSeconds(2L));
                for (ConsumerRecord<GenericRecord, GenericRecord> record : records) {
                    GenericRecord item = record.value();
                    log.info("value = [before=" + item.get("before")
                            + ", after=" + item.get("after")
                            + ", source=" + item.get("source")
                            + ", op=" + item.get("op")
                            + ", ts_ms=" + item.get("ts_ms")
                            + "], schema = "+ item.getSchema()
                            + ", class = "+ item.getClass().getName()
                            + ", before-class = "+ item.get("before").getClass().getName()
                            + ", after-class = "+ item.get("after").getClass().getName()
                            + ", key = "+ record.key()
                            + ", partition = " + record.partition()
                            + ", offset = " + record.offset()
                    );
                }
            }
        } finally {
            consumer.close();
        }

    }

    public static void specificConsume() {
        Properties props = getProps();
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

        KafkaConsumer<Key, Envelope> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        try {
            for (;;) {
                ConsumerRecords<Key, Envelope> records = consumer.poll(Duration.ofSeconds(2L));
                for (ConsumerRecord<Key, Envelope> record : records) {

                    Envelope envelope = record.value();
                    log.info("value = [before=" + envelope.getBefore()
                            + ", after=" + envelope.getAfter()
                            + ", source=" + envelope.getSource()
                            + ", op=" + envelope.getOp()
                            + ", ts_ms=" + envelope.getTsMs()
                            + "], schema = "+ envelope.getSchema()
                            + ", class = "+ envelope.getClass().getName()
                            + ", before-class = "+ envelope.getBefore().getClass().getName()
                            + ", after-class = "+ envelope.getAfter().getClass().getName()
                    );
                }
            }
        } finally {
            consumer.close();
        }

    }

    public static void main(String[] args) {
        specificConsume();
    }
}