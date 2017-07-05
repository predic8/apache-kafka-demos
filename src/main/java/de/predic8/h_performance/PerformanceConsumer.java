package de.predic8.h_performance;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class PerformanceConsumer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "a");
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongDeserializer");

        KafkaConsumer<Long, Long> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("produktion"), new AssignmentDisplayRebalanceListener());

        System.out.println("SimpleConsumer gestartet!");

        long t1 = 0, i = 0;

        outer:
        while(true) {

            ConsumerRecords<Long, Long> records = consumer.poll(1000);
            if (records.count() == 0)
                continue;

            if(t1 == 0) {
                System.out.println("Started Timer!");
                t1 = System.currentTimeMillis();
            }

            for(ConsumerRecord<Long,Long> record : records) {
                if(record.key() == -1) {
                    break outer;
                }
                i++;
            }

        }

        System.out.println("Fertig! " + i + " Nachrichten in " + (System.currentTimeMillis() - t1 + " ms"));

        consumer.close();
    }
}
