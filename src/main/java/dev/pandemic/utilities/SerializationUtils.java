package dev.pandemic.utilities;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SerializationUtils {
    private SerializationUtils() {
    }

    public static void serialize(String filename) throws IOException {
        String xmlContent = new String(Files.readAllBytes(Paths.get(filename)));

        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(xmlContent);
        }
    }

    public static void deserialize(String inputFile, String outputXmlFile) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(inputFile);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            String xmlContent = (String) in.readObject();

            try (FileWriter fileWriter = new FileWriter(outputXmlFile)) {
                fileWriter.write(xmlContent);
            }
        }
    }
}
