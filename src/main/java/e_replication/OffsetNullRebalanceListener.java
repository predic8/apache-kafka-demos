package e_replication;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

public class OffsetNullRebalanceListener implements ConsumerRebalanceListener {


    private final KafkaConsumer<String, String> consumer;
    private boolean resetted;

    public OffsetNullRebalanceListener(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition>partitions) {
        System.out.println("Revoked from");

        for (TopicPartition partition : partitions) {
            System.out.println("collection = [" + partition + "]");
        }
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.println("Assigned to :");

        for (TopicPartition partition : partitions) {
            System.out.print(partition );
        }

        System.out.println();

        if (!resetted) {
            consumer.seek(new TopicPartition("bar",0),0);
            resetted = true;
        }
    }

}
