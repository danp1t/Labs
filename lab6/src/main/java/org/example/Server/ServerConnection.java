package org.example.Server;

import org.example.Managers.CollectionManager;
import org.example.Managers.CommandManager;

import java.net.DatagramSocket;
import java.net.SocketException;

import static org.example.Managers.StartManager.setCollectionManager;
import static org.example.Managers.StartManager.setCommandManager;

public class ServerConnection {
    public static DatagramSocket connection(Integer port) throws SocketException {
        System.out.println("Сервер запущен!");
        DatagramSocket serverSocket = new DatagramSocket(port);
        CommandManager commands = new CommandManager();
        CollectionManager collectionManager = new CollectionManager();
        setCollectionManager(collectionManager);
        setCommandManager(commands);
        return serverSocket;
    }
}
