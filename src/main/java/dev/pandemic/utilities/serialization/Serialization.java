package dev.pandemic.utilities.serialization;

import dev.pandemic.enumerations.FilePath;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Serialization {
    private Serialization() {
    }

    public static void serialize(String filename, String outputFilename) throws IOException {
        String xmlContent = new String(Files.readAllBytes(Paths.get(filename)));
        String base64Encoded = Base64.getEncoder().encodeToString(xmlContent.getBytes());
        try (FileOutputStream fos = new FileOutputStream(outputFilename);
             PrintWriter writer = new PrintWriter(fos)) {
            writer.print(base64Encoded);
        }
    }

    public static void deserialize(String inputFilename, String filename) throws IOException {
        String base64Encoded = new String(Files.readAllBytes(Paths.get(inputFilename)));
        byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
        String xmlContent = new String(decodedBytes);
        Files.write(Paths.get(filename), xmlContent.getBytes());
    }
}
