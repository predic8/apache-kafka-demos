package de.predic8.g_timestamps;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class TimestampConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(GROUP_ID_CONFIG, "a");

        try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer())) {
            consumer.subscribe(singletonList("produktion"));

            while (true) {

                for (ConsumerRecord<String, String> rec : consumer.poll(ofSeconds(1)))
                    System.out.printf("offset= %d, key= %s, timestamp=%d, timestampType=%s, value= %s\n", rec.offset(), rec.key(), rec.timestamp(), rec.timestampType().toString(), rec.value());

            }
        }
    }
}
