package de.predic8.l_offset_control;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

public class NumberConsumer {

    public static void main(String[] args) throws Exception {

        TopicPartition partition = new TopicPartition("numbers", 0);

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(GROUP_ID_CONFIG, "numbers");

        try (KafkaConsumer<Integer, Integer> consumer = new KafkaConsumer<>(props, new IntegerDeserializer(), new IntegerDeserializer())) {

            consumer.subscribe(singletonList("numbers"));

            while (true) {

                ConsumerRecords<Integer, Integer> recs = consumer.poll(ofSeconds(1));
                System.out.println(recs.count() + " gelesen.");

                for (ConsumerRecord<Integer, Integer> rec : recs) {

                    System.out.println("Offset: " + rec.offset() + " Value: " + rec.value());

                    Thread.sleep(1000);
                }
            }
        }
    }

}
