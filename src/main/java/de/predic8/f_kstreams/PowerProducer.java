package de.predic8.f_kstreams;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class PowerProducer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 16000);
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonPOJOSerializer.class.getName());


        Producer<String, Messung> producer = new KafkaProducer<>(props);

        long t1 = System.currentTimeMillis();

        String[] types = {"Windrad","Biogas","Biogas","Solar","Windrad","Windrad","Solar","Biogas"};

        JsonPOJOSerializer<Messung> serializer = new JsonPOJOSerializer<>();

        int i = 0;
        for(; i < 3000; i++) {

            int key = (int)round(random() * 7);
            double value = new Double(round(random()*10000000L)).intValue()/1000.0;

            Messung messung = new Messung();
            messung.anlage=""+key;
            messung.type=types[key];
            messung.kw=value;

            producer.send(new ProducerRecord<>("produktion", "" + key, messung));

            Thread.sleep(1000);
        }
        System.out.println("fertig " + i + " Nachrichten in " + (System.currentTimeMillis() - t1 + " ms"));

        producer.close();
    }
}
