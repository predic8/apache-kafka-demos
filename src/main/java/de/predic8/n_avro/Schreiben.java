package de.predic8.n_avro;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class Schreiben {

    public static void main(String[] args) throws IOException {
        Article a = Article.newBuilder()
                .setName("Beef")
                .setPrice(5)
                .setOrigin("Argentina")
                .build();

        DatumWriter<Article> userDatumWriter = new SpecificDatumWriter<>(Article.class);
        DataFileWriter<Article> dataFileWriter = new DataFileWriter<>(userDatumWriter);
        dataFileWriter.create(a.getSchema(), new File("article.avro"));
        dataFileWriter.append(a);
        dataFileWriter.close();
    }
}
