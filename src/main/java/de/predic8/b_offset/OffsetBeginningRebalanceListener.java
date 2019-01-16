package de.predic8.b_offset;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

public class OffsetBeginningRebalanceListener implements ConsumerRebalanceListener {


    private final KafkaConsumer<String, String> consumer;
    private boolean resetted;
    private String topic;

    public OffsetBeginningRebalanceListener(KafkaConsumer<String, String> consumer, String topic) {
        this.consumer = consumer;
        this.topic = topic;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition>partitions) {
        System.out.printf("Revoked from: %s\n", partitions );
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.printf("Assigned to: %s\n\n", partitions );

        if (!resetted) {
            consumer.seekToBeginning(partitions);
            resetted = true;
        }
    }

}
