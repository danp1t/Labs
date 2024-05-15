package org.example.Server;


import org.example.Managers.CollectionManager;
import org.example.Managers.CommandManager;

import java.io.IOException;
import java.net.DatagramSocket;
import java.sql.SQLException;

import static org.example.Managers.StartManager.setCollectionManager;
import static org.example.Managers.StartManager.setCommandManager;
import static org.example.Server.ServerCreateDB.createDB;

public class ServerConnection {
    public static DatagramSocket connection(Integer port) throws IOException, SQLException {
        System.out.println("Сервер запущен!");
        createDB();

        DatagramSocket serverSocket = new DatagramSocket(port);
        CommandManager commands = new CommandManager();
        CollectionManager collectionManager = new CollectionManager();
        setCollectionManager(collectionManager);
        setCommandManager(commands);
        return serverSocket;
    }
}
