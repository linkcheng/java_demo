package cn.xyf.flowcount.kafka.consumer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.GenericAvroDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.kstream.TimeWindowedDeserializer;
import org.apache.kafka.streams.kstream.Windowed;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;


/**
 * ConfluentConsumer.java
 * 使用Confluent实现的Schema Registry服务来消费Avro序列化后的对象
 */
@Slf4j
public class KafkaWindowConsumer extends AbstractConsumer {
    private static String GROUP_ID = "avro_win_demo11";
    private static String topic = "streams.count2222";

    public void winConsume() {
        Properties props = getProps(GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, TimeWindowedDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GenericAvroDeserializer.class.getName());
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);

        TimeWindowedDeserializer<String> keyDeserializer = new TimeWindowedDeserializer<>(new StringDeserializer());

        Map<String, String> valueConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
        GenericAvroDeserializer valueDeserializer = new GenericAvroDeserializer();
        valueDeserializer.configure(valueConfig, false);

        KafkaConsumer<Windowed<String>, GenericRecord> consumer = new KafkaConsumer<>(props, keyDeserializer, valueDeserializer);
        consumer.subscribe(Collections.singletonList(topic));

        try {
            for (;;) {
                ConsumerRecords<Windowed<String>, GenericRecord> records = consumer.poll(Duration.ofSeconds(3L));
                for (ConsumerRecord<Windowed<String>, GenericRecord> record : records) {
                    log.info("value = " + record.value()
                            + ", key = "+ record.key()
                    );
                }
            }
        } finally {
            consumer.close();
        }

    }

    public static void main(String[] args) {
        new KafkaWindowConsumer().winConsume();
    }
}