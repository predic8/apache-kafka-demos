package de.predic8.c_queueing;

import de.predic8.LogRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

public class SimpleConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(GROUP_ID_CONFIG, "simple");

        try(KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(props, new LongDeserializer(), new StringDeserializer())) {
            consumer.subscribe(singletonList("produktion"), new LogRebalanceListener());

            while(true) {
                ConsumerRecords<Long, String> recs = consumer.poll(ofSeconds(1));
                if (recs.count() == 0)
                    continue;

                System.out.print("Partitions: " + recs.partitions());
                System.out.println(" Count: " + recs.count());

                for (ConsumerRecord<Long, String> rec : recs)
                    System.out.printf("partition=%d, offset= %d, key= %s, value= %s\n", rec.partition(), rec.offset(), rec.key(), rec.value());
            }
        }
    }
}
