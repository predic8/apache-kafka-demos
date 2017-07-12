package de.predic8.j_serialization;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ArtikelProducer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 16000);
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "");

    }
}
