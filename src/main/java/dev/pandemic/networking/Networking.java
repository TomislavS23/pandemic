package dev.pandemic.networking;

import dev.pandemic.controller.GameController;
import dev.pandemic.model.GameState;
import dev.pandemic.model.State;
import jakarta.xml.bind.JAXBException;
import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.Stack;

public class Networking {
    public static final String HOST = "localhost";
    public static int PORT = 0;

    private Networking() {
    }

    public static void acceptRequests() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf("Server listening on port: %d%n", serverSocket.getLocalPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.printf("Client connected from port %d%n", clientSocket.getPort());
                new Thread(() -> processSerializableClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processSerializableClient(Socket clientSocket) {
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());) {
            State state = (State) ois.readObject();
            GameState.getInstance().setState(state);
            System.out.println("Game state received.");
            oos.writeObject(GameState.getInstance().getState());
            oos.flush();
            Platform.runLater(() -> GameController.getInstance().initialize());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest() {
        try (Socket clientSocket = new Socket(HOST, PORT)) {
            System.out.printf("Client is connecting to %s:%d%n", clientSocket.getInetAddress(), clientSocket.getPort());
            sendSerializableRequest(clientSocket);
        } catch (IOException | JAXBException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void sendSerializableRequest(Socket client) throws IOException, JAXBException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        State state = GameState.getInstance().getState();
        oos.writeObject(state);
        oos.flush();

        State response = (State) ois.readObject();
        System.out.printf("Response received from server: ", response);
    }

    public static void synchronise(int port) {
        Networking.PORT = port;
        Platform.runLater(Networking::sendRequest);
    }
}
