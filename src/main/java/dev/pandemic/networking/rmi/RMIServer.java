package dev.pandemic.networking.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {
    private static final int RANDOM_PORT_HINT = 0;
    public static final int RMI_PORT = 1099;
    public static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            ChatRemoteService chatRemoteService = new ChatRemoteServiceImpl();
            ChatRemoteService skeleton = (ChatRemoteService) UnicastRemoteObject.exportObject(chatRemoteService, RANDOM_PORT_HINT);
            registry.rebind(ChatRemoteService.REMOTE_OBJECT_NAME, skeleton);
            System.err.println("Object registered in RMI registry.");

            synchronized (RMIServer.class) {
                RMIServer.class.wait();
            }
        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
