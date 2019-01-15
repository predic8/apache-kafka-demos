package de.predic8.f_streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.predic8.f_kstreams.JsonPOJODeserializer;
import de.predic8.f_kstreams.JsonPOJOSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

public class VerkaufSerde implements Serde<Verkauf> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }

    @Override
    public Serializer<Verkauf> serializer() {
        return new JsonPOJOSerializer<Verkauf>();
    }

    @Override
    public Deserializer<Verkauf> deserializer() {
        Map<String, Object> serdeProps = new HashMap<>();
        serdeProps.put("JsonPOJOClass", Verkauf.class);
        JsonPOJODeserializer<Verkauf>  deserializer = new JsonPOJODeserializer<Verkauf>();
        deserializer.configure(serdeProps, false);

        return deserializer;
    }
}
