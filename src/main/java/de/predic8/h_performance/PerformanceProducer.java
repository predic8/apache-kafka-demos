package de.predic8.h_performance;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class PerformanceProducer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 32000);
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongSerializer");

        Producer<Long, Long> producer = new KafkaProducer<>(props);

        long t1 = System.currentTimeMillis();

        long i = 0;
        for(; i < 1000000; i++) {

            producer.send(new ProducerRecord<>("produktion", i, i));
        }
        producer.send(new ProducerRecord<>("produktion", (long) -1, (long) -1));
        System.out.println("fertig " + i  + " Nachrichten in " + (System.currentTimeMillis() - t1 + " ms"));

        producer.close();
    }
}
