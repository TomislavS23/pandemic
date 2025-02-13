package dev.pandemic.utilities.serialization;

import dev.pandemic.enumerations.FilePath;
import dev.pandemic.game.ReplayUtils;
import dev.pandemic.model.State;
import dev.pandemic.thread.ReadLastState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.*;

public class Serialization {
    public static Boolean FILE_ACCESS_IN_PROGRESS = false;

    public Serialization() {
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

    public synchronized void serializeObject(Object object, String filePath) {
        while (FILE_ACCESS_IN_PROGRESS){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        FILE_ACCESS_IN_PROGRESS = true;

        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FILE_ACCESS_IN_PROGRESS = false;
        notifyAll();
    }

    public synchronized Optional<Object> deserializeObject(String filePath) {
        while (FILE_ACCESS_IN_PROGRESS){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        FILE_ACCESS_IN_PROGRESS = true;

        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Object obj = in.readObject();
            return Optional.of(obj);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        FILE_ACCESS_IN_PROGRESS = false;
        notifyAll();

        return Optional.empty();
    }
}
