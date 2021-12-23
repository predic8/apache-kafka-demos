package de.predic8.l_offset_control;

import de.predic8.LogRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class SimpleConsumer {

    public static void main(String[] args) {

        TopicPartition partition = new TopicPartition("produktion",0);

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "k");
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false"); // Wichtig!

        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props, new IntegerDeserializer(), new StringDeserializer());

        consumer.subscribe(singletonList("produktion"));

        while (true) {

            ConsumerRecords<Integer, String> records = null;
            try {
                records = consumer.poll(ofSeconds(1));
            } catch (SerializationException e) {
                System.out.println("e = " + e);
                long pos = consumer.position(partition);
                System.out.println("Error at offset: " + pos + " moving to: " + (pos + 1));
                consumer.seek(partition, pos+1);
                continue;
            }

            if (records.count() == 0)
                continue;

            System.out.println("Count: " + records.count());

            for (ConsumerRecord<Integer, String> record : records) {
                System.out.printf("offset= %d, key= %s, value= %s\n", record.offset(), record.key(), record.value());
                consumer.commitSync();
            }

        }
    }
}
