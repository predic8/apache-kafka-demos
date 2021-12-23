package de.predic8.e_replication;

import de.predic8.b_offset.OffsetBeginningRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

public class WindenergieConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(GROUP_ID_CONFIG, "windenergie");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer())) {

            consumer.subscribe(singletonList("produktion"), new OffsetBeginningRebalanceListener(consumer, "produktion"));

            while (true) {
                for (ConsumerRecord<String, String> rec : consumer.poll(ofSeconds(1)))
                    System.out.printf("partition= %d, offset= %d, key= %s, value= %s\n", rec.partition(), rec.offset(), rec.key(), rec.value());
            }
        }
    }
}
