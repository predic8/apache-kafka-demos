package de.predic8.z_simple;

import kafka.common.OffsetAndMetadata;
import kafka.coordinator.group.BaseKey;
import kafka.coordinator.group.GroupMetadataManager;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class Consumer_A {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public static void main(String[] args) throws IOException {

        log.info("started up");

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "kafka-1.my.kafka:9092");
//        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");


        KafkaConsumer<Bytes, Bytes> consumer = new KafkaConsumer<>(props, new BytesDeserializer(), new BytesDeserializer());

        log.info("consumer created");

        consumer.subscribe(singletonList("__consumer_offsets"));

        log.info("Consumer A gestartet!");

        Runtime.getRuntime().addShutdownHook(new Thread(consumer::close));

        FileWriter fw = new FileWriter("Log-" + System.currentTimeMillis() + ".txt");
        fw.write("ConsumerOffsetsOffset;consumergroup;topic;partition;offset;date;expire\n");
        while (true) {

            ConsumerRecords<Bytes, Bytes> records = consumer.poll(ofSeconds(1));
            if (records.count() == 0)
                continue;

            for (ConsumerRecord<Bytes, Bytes> record : records) {

                BaseKey baseKey = GroupMetadataManager.readMessageKey(ByteBuffer.wrap(record.key().get()));
                try {
                    OffsetAndMetadata offsetAndMetadata = GroupMetadataManager.readOffsetMessageValue(ByteBuffer.wrap(record.value().get()));
                    String key = "Key: " + baseKey.key();

                    Date date = new Date(offsetAndMetadata.commitTimestamp());

                    String expire = "";

                    Option<Object> objectOption = offsetAndMetadata.expireTimestamp();
                    if (objectOption.nonEmpty())
                        expire = new Date((Long) objectOption.get()).toString();

                    String keyContent = baseKey.key().toString().substring(1, baseKey.key().toString().length() - 1);
                    String[] keyComponents = keyContent.split(",");


                    String formattedValue = "Offset: " + offsetAndMetadata.offset() + " leaderEpoch: " + offsetAndMetadata.leaderEpoch()
                            + " date: " + date + " expire: " + expire;

                    if (Arrays.stream(args).anyMatch(key::contains) || args.length == 0) {
                        fw.write(record.offset() + ";" + keyComponents[0] + ";" + keyComponents[1] + ";" + keyComponents[2] + ";" + offsetAndMetadata.offset() + ";" + date + ";" + expire + "\n");
                        log.info("offset= {}, key= {}, value= {}", record.offset(), keyContent, formattedValue);
                    }
                } catch (Exception e) {
                    log.error("Error occured", e);
                }


            }

        }
    }
}
