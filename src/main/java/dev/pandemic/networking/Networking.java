package dev.pandemic.networking;

import dev.pandemic.model.GameState;
import dev.pandemic.model.State;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.Stack;

public class Networking {
    public static final String HOST = "localhost";
    public static final int PORT = 1989;

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
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());) {
            State state = (State) ois.readObject();
            System.out.println("Game state received from Player 01");
            oos.writeObject(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest() {
        try (Socket clientSocket = new Socket(HOST, PORT)) {
            System.out.printf("Client is connecting to %s:%d%n", clientSocket.getInetAddress(), clientSocket.getPort());

            sendSerializableRequest(clientSocket);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void sendSerializableRequest(Socket client) throws IOException, JAXBException {
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        oos.writeObject(GameState.getInstance().getState());
        System.out.println("Game state sent to the Player 02");
    }
}
