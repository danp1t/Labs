package org.example.Server;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.CommandManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import static org.example.Managers.StartManager.setCommandManager;
import static org.example.Server.ServerCommandHandler.handlerCommand;
import static org.example.Server.ServerConnection.connection;
import static org.example.Server.ServerReadRequest.readRequest;
import static org.example.Server.ServerResponds.sendResponds;;

public class Server {
    public static void main(String[] args) throws SocketException {
        DatagramSocket serverSocket = connection();
        try {
                //Получение запроса от Клиента
            DatagramPacket receivePacket = ServerReadRequest.getDatagramPacket(serverSocket);
            String receivedMessage = readRequest(receivePacket);
            System.out.println("Received from client: " + receivedMessage);
            String[] tokens = receivedMessage.split(" ");
            handlerCommand(tokens);
            sendResponds(serverSocket, receivePacket);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}