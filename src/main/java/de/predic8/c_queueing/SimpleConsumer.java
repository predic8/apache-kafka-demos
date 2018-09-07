package de.predic8.c_queueing;

import de.predic8.LogRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class SimpleConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "k");
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");


        try {
            KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(props);

            consumer.subscribe( singletonList("produktion"), new LogRebalanceListener());

            while (true) {

                ConsumerRecords<Long, String> records = consumer.poll( ofSeconds(1));
                if (records.count() == 0)
                    continue;

                System.out.print("Partitions: " + records.partitions());
                System.out.println(" Count: " + records.count());

                for (ConsumerRecord<Long, String> record : records)
                    System.out.printf("partition=%d, offset= %d, key= %s, value= %s\n", record.partition(), record.offset(), record.key(), record.value());

            }
        } catch (RuntimeException e) {
            System.out.println("e = " + e);
        } finally {
            System.out.println("Closing!");
        }
    }
}
