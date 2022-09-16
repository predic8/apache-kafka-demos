package de.predic8.n_avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.File;
import java.io.IOException;

public class Lesen {

    public static void main(String[] args) throws IOException {
        DatumReader<Article> userDatumReader = new SpecificDatumReader<>(Article.class);
        DataFileReader<Article> dataFileReader = new DataFileReader<>(new File("article.avro"), userDatumReader);
        Article user = null;
        while (dataFileReader.hasNext()) {

            user = dataFileReader.next(user);
            System.out.println("User: " + user);
        }
    }
}
