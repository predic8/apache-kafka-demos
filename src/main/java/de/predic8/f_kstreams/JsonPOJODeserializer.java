package de.predic8.f_kstreams;

import com.google.gson.Gson;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Map;

public class JsonPOJODeserializer<T> implements Deserializer<T> {

    private Gson gson = new Gson();
    private Class<T> tClass;

    public JsonPOJODeserializer() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        tClass = (Class<T>) props.get("JsonPOJOClass");
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {

        if (bytes == null)
            return null;

        try {
           return gson.fromJson(new String(bytes, "UTF-8"), tClass);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public void close() {

    }
}