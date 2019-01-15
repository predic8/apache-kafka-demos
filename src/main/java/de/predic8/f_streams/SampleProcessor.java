package de.predic8.f_streams;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

public class SampleProcessor implements Processor<Integer,Double> {
    @Override
    public void init(ProcessorContext ctx) {
        System.out.println("ctx = " + ctx);
        System.out.println("Init");

    }

    @Override
    public void process(Integer integer, Double aDouble) {
        System.out.println("Processung integer = " + integer);
    }

    @Override
    public void close() {
        System.out.println("Close");
    }
}
