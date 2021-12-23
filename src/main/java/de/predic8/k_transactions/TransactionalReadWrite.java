package de.predic8.k_transactions;

import de.predic8.b_offset.OffsetBeginningRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static java.time.Duration.ofMillis;
import static java.util.Collections.singleton;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

public class TransactionalReadWrite {

    public static void main(String[] args) {

        try(Producer<String, String> producer = new KafkaProducer<>(producer(), new StringSerializer(), new StringSerializer())) {
            producer.initTransactions();

            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumer(), new StringDeserializer(), new StringDeserializer())) {
                consumer.subscribe(singleton("produktion"), new OffsetBeginningRebalanceListener(consumer, "produktion"));

                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(ofMillis(10));

                    System.out.println("Received something!");

                    producer.beginTransaction();
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("record = " + record);
                        producer.send(new ProducerRecord<>("produktion-tmp", record.key(), record.value()));
                    }

//            producer.sendOffsetsToTransaction(currentOffsets(consumer), group);
                    producer.commitTransaction();
//            producer.abortTransaction();
                }
            }
        }
    }

    private static Properties consumer() {
        Properties props = common();
        props.put(CLIENT_ID_CONFIG, "consumer-b");
        props.put(GROUP_ID_CONFIG, "kopierer");
        //props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");

        // Nur nicht-transaktionale oder committed Nachrichten lesen
        props.put("isolation.level", "read_committed");
        return props;
    }

    private static Properties producer() {
        Properties props = common();
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BATCH_SIZE_CONFIG, 16000);
        // Verhindert Zombie Instanzen dieses Producers
        props.put("transactional.id", "my-transactional-id");

        return props;
    }

    private static Properties common() {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        return props;
    }
}