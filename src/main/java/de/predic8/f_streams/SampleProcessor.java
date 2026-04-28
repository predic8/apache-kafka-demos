package de.predic8.f_streams;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;

public class SampleProcessor implements Processor<Integer, Double, Void, Void> {

    private ProcessorContext<Void, Void> context;

    @Override
    public void init(ProcessorContext<Void, Void> context) {
        this.context = context;
        System.out.println("Init ctx = " + context);
    }

    @Override
    public void process(Record<Integer, Double> record) {
        System.out.println("Processing key = " + record.key() +
                ", value = " + record.value() +
                ", timestamp = " + record.timestamp());
    }

    @Override
    public void close() {
        System.out.println("Close");
    }
}
