package de.predic8.z_simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class CustomDeserializer implements Deserializer<OffsetAndMetadata> {
    public CustomDeserializer() {
    }

    public void configure(Map<String, ?> map, boolean b) {
    }

    public OffsetAndMetadata deserialize(String s, byte[] MessageBytes) {
        OffsetAndMetadata eEventMessage = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            eEventMessage = (OffsetAndMetadata)objectMapper.readValue(MessageBytes, OffsetAndMetadata.class);
        } catch (IOException ex) {
  //               your stuffs
        }

        return eEventMessage;
    }

    public void close() {
    }
}