package de.predic8.f_streams;

import de.predic8.f_kstreams_old.JsonPOJOSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;

public class VerkaeufeProducer {

    static String[] maerkte = { "Bonn", "Berlin", "Hamburg", "Köln", "Düsseldorf"};

    static String[] warengruppen = { "Food", "Zeitschriften", "Getränke"};

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");


        Producer<Long, Verkauf> producer = new KafkaProducer(props, new LongSerializer(), new JsonPOJOSerializer<Verkauf>() );


        int i = 0;
        while ( true) {
            i++;

            String markt = maerkte[random(4)];
            String gruppe = warengruppen[random(2)];
            Verkauf verkauf = new Verkauf( random(10),random(20), gruppe, markt );

            producer.send(new ProducerRecord<>("verkaeufe", verkauf.getWare(), verkauf));
            Thread.sleep(1000);
        }

    }

    public static int random(int max) {
        return ThreadLocalRandom.current().nextInt(1, max + 1);
    }
}
