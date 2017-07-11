package de.predic8.i_headers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;

import java.util.Arrays;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class HeaderConsumer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "a");
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("produktion"));

        System.out.println("HeaderConsumer gestartet!");

        while(true) {

            ConsumerRecords<String, String> records = consumer.poll(1000);
            if (records.count() == 0)
                continue;

            for (ConsumerRecord<String, String> rec : records) {

                System.out.printf("offset= %d, key= %s, value= %s\n", rec.offset(), rec.key(), rec.value());
                for (Header h : rec.headers()) {
                    System.out.println(h.key() + ": " + new String(h.value()));

                }
            }


        }
    }
}
