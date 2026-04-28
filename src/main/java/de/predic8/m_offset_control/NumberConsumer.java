package de.predic8.m_offset_control;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import java.util.Properties;
import java.util.Random;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class NumberConsumer {

    private static final Random RAND = new Random();

    public static void main(String[] args) throws Exception {

        var partition = new TopicPartition("numbers", 0);

        var props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(GROUP_ID_CONFIG, "numbers");
        props.put(SESSION_TIMEOUT_MS_CONFIG, 6_000);
        props.put(HEARTBEAT_INTERVAL_MS_CONFIG, 2_000);
        props.put(MAX_POLL_INTERVAL_MS_CONFIG, 300_000);

        try (var consumer = new KafkaConsumer<>(props, new IntegerDeserializer(), new IntegerDeserializer())) {

            consumer.subscribe(singletonList("numbers"));

            while (true) {

                var recs = consumer.poll(ofSeconds(1));
                System.out.println(recs.count() + " gelesen.");

                for (var rec : recs) {

                    System.out.println("Offset: " + rec.offset() + " Value: " + rec.value());

                    Thread.sleep(1000);
                }
            }
        }
    }

}
