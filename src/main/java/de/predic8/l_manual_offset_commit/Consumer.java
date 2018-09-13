package de.predic8.l_manual_offset_commit;

import de.predic8.LogRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;
import java.util.Random;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class Consumer {

    public static void main(String[] args) throws Exception {

       // TopicPartition partition = new TopicPartition("orders",0);

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "k");
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false");

        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props, new IntegerDeserializer(), new StringDeserializer());


        consumer.subscribe(singletonList("orders"), new LogRebalanceListener());

        while (true) {

            ConsumerRecords<Integer, String> records = consumer.poll(ofSeconds(1));

            if (records.count() == 0)
                continue;

            System.out.println("Count: " + records.count());

            for (ConsumerRecord<Integer, String> rec : records) {

                System.out.printf("offset= %d\n", rec.offset());

                int r = new Random().nextInt(18);
                if (r == 13)
                    throw new RuntimeException("Pech!"); // Wichtig: Kein close() ausführen!

            }

            consumer.commitSync();


        }
    }
}
