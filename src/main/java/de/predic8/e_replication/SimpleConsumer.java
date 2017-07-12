package de.predic8.e_replication;

import de.predic8.b_offset.OffsetBeginningRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class SimpleConsumer {

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

        consumer.subscribe(Arrays.asList("produktion"), new OffsetBeginningRebalanceListener(consumer, "produktion"));

        while(true) {

            ConsumerRecords<String, String> records = consumer.poll(1000);
            if (records.count() == 0)
                continue;

            System.out.println(" Count: " + records.count());

            for (ConsumerRecord<String, String> rec : records)
                System.out.printf("partition= %d, offset= %d, key= %d, value= %s\n", rec.partition(), rec.offset(), rec.key(), rec.value());

        }
    }
}
