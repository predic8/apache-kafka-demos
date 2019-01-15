package de.predic8.f_streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.util.Properties;

public class VerkaeufeApp {

    public static void main(String[] args) throws InterruptedException {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "verkaeufe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);

        // Wichtig für store Topic
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<Long, Verkauf> src = builder.stream("verkaeufe",
                Consumed.with(Serdes.Long() ,new VerkaufSerde()));

        src.print(Printed.toSysOut());



        //.to("koeln", Produced.with(Serdes.Long(),Serdes.Integer()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);


        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));



        streams.start();

        Thread.sleep(50000);

        ReadOnlyKeyValueStore<String, Long> store =
                streams.store("by-markt", QueryableStoreTypes.keyValueStore());




        while (true ) {
            System.out.println("Köln: " + store.get("Köln"));
        }



    }
}



/*

           src.print( Printed.toSysOut());

            src
                .filter((k,v) -> v.getMarkt().equals("Köln"))
                .print( Printed.toSysOut());


                   src
                .filter((k,v) -> v.getMarkt().equals("Köln"))
                .to("koeln", Produced.with(Serdes.Long(),new VerkaufSerde()));


                    src
                .filter((k,v) -> v.getMarkt().equals("Köln"))
                .mapValues((k,v) -> v.getMenge())
                .to("koeln", Produced.with(Serdes.Long(),Serdes.Integer()));


                       src
                .filter((k,v) -> v.getMarkt().equals("Köln"))
                .mapValues((k,v) -> v.getMenge())
                .print(Printed.toSysOut());

           src.foreach((k,v) -> {
            System.out.println(v);
        });





        src.map((k,v) -> new KeyValue<>(k,v)).
                peek((k,v) ->
                System.out.println("k  = " + k + " v= " + v)
        ).print(Printed.toSysOut());



           src
                .groupBy((k,v) -> 1)
                .aggregate(
                        () -> 0,
                        (aggKey, neu, agg) -> agg + neu,
                                Materialized.as("store2"))
                                .toStream()
                                .print(Printed.toSysOut());





        src
                .groupBy((k,v) -> 1)
                .windowedBy(SessionWindows.with( ofSeconds(30)))
                .reduce(

                        (agg, neu) -> agg + neu)
                .toStream()
                .print(Printed.toSysOut());



                src
                .through("temp-topic")
                .groupBy((k,v) -> 1)
                .windowedBy(SessionWindows.with( ofSeconds(30)))
                .reduce(

                        (agg, neu) -> agg + neu)
                .toStream()
                .print(Printed.toSysOut());






                KTable byMarkt = src
                .groupBy((k,v) -> v.getMarkt(), Grouped.with(Serdes.String(), new VerkaufSerde()))
                .aggregate( () -> 0,
                        (aggKey, neu, agg) -> agg + neu.getMenge(),
                        Materialized.<String, Integer, KeyValueStore<Bytes, byte[]>>as("by-markt").withValueSerde(Serdes.Integer())
                );

        byMarkt.toStream().print(Printed.toSysOut());



        //.to("koeln", Produced.with(Serdes.Long(),Serdes.Integer()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);


        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));



        streams.start();

        Thread.sleep(50000);

        ReadOnlyKeyValueStore<String, Long> store =
                streams.store("by-markt", QueryableStoreTypes.keyValueStore());




        while (true ) {
            System.out.println("Köln: " + store.get("Köln"));
        }


--------


 KTable byMarkt = src
                .groupBy((k,v) -> v.getMarkt(), Grouped.with(Serdes.String(), new VerkaufSerde()))
                .aggregate( () -> 0,
                        (aggKey, neu, agg) -> agg + neu.getMenge(),
                        Materialized.<String, Integer, KeyValueStore<Bytes, byte[]>>as("by-markt").withValueSerde(Serdes.Integer())
                );

        byMarkt.toStream().print(Printed.toSysOut());
 */