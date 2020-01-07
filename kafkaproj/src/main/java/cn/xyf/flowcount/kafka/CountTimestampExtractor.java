package cn.xyf.flowcount.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 指定事件时间
 */
@Slf4j
public class CountTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long timeMill) {
        GenericRecord value = (GenericRecord)record.value();
        GenericRecord after = (GenericRecord)value.get("after");
        Utf8 eventTimeStr = (Utf8)after.get("created_time");
        log.info(record.timestamp() + "");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = df.parse(eventTimeStr.toString());
            return date.toInstant().toEpochMilli();
        } catch (ParseException e) {
            log.error(eventTimeStr.toString()+" parse error", e);
        }

        return record.timestamp();

    }

}