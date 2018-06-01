package de.predic8.j_serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ArtikelSerializer implements Serializer<Artikel>, Deserializer<Artikel>
{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        System.out.println("map = " + map);
        System.out.println(b);
    }

    @Override
    public Artikel deserialize(String s, byte[] bytes) {
        return null;
    }

    @Override
    public byte[] serialize(String s, Artikel artikel) {
        return null;
    }

    @Override
    public void close() {
        mapper = null;
    }
}
