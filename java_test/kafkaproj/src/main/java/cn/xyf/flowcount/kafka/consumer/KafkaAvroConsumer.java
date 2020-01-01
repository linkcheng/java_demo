package cn.xyf.flowcount.kafka.consumer;

import common.common.user_menu.Envelope;
import common.common.user_menu.Key;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;


/**
 * ConfluentConsumer.java
 * 使用Confluent实现的Schema Registry服务来消费Avro序列化后的对象
 */
@Slf4j
public class KafkaAvroConsumer extends AbstractConsumer{
    private static String GROUP_ID = "avro_demo";
    private static String topic = "common.common.user_menu";


    public void genericConsume() {
        KafkaConsumer<GenericRecord, GenericRecord> consumer = new KafkaConsumer<>(getAvroProps(GROUP_ID));
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

    public void specificConsume() {
        Properties props = getAvroProps(GROUP_ID);
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
        new KafkaAvroConsumer().specificConsume();
    }
}