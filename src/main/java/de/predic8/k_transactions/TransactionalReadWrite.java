package de.predic8.k_transactions;

import de.predic8.b_offset.OffsetBeginningRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Properties;

import static java.lang.Math.random;
import static java.lang.Math.round;
import static java.util.Collections.singleton;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

public class TransactionalReadWrite {

    public static void main(String[] args) throws InterruptedException {

        Producer<String, String> producer = new KafkaProducer<>(producerProperties());

        producer.initTransactions();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties());

        consumer.subscribe(singleton("produktion"),new OffsetBeginningRebalanceListener(consumer,"produktion"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);

            System.out.println("Received something!");

            producer.beginTransaction();
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("record = " + record);
                producer.send(new ProducerRecord<String, String>("produktion-tmp", record.key(), record.value()));
            }

//            producer.sendOffsetsToTransaction(currentOffsets(consumer), group);
            producer.commitTransaction();
//            producer.abortTransaction();
        }
    }

    private static Properties consumerProperties() {
        Properties props = commonProperties();
        props.put(CLIENT_ID_CONFIG, "consumer-b");
        props.put(GROUP_ID_CONFIG, "foo");
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        // Nur nicht-transaktionale oder committed Nachrichten lesen
        props.put("isolation.level", "read_committed");


        return props;
    }

    private static Properties producerProperties() {
        Properties props = commonProperties();
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(BATCH_SIZE_CONFIG, 16000);
        props.put(ACKS_CONFIG, "all");
        // Verhindert Zombie Instanzen dieses Producers
        props.put("transactional.id", "my-transactional-id");

        return props;
    }

    private static Properties commonProperties() {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");



        return props;
    }
}