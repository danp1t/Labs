package org.example.Server;
import org.example.Client.Commands;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static org.example.Server.ServerCommandHandler.handlerCommand;
import static org.example.Server.ServerConnection.connection;
import static org.example.Server.ServerReadRequest.readRequest;
import static org.example.Server.ServerResponds.sendResponds;;

public class Server {
    private static DatagramSocket serverSocket;

    public static void main(String[] args) throws SocketException {
        serverSocket = connection();
        try {
            while (true) {
                DatagramPacket receivePacket = ServerReadRequest.getDatagramPacket(serverSocket);
                Commands receivedMessage = readRequest(receivePacket);
                System.out.println("Received from client: " + receivedMessage.getName());
                handlerCommand(receivedMessage);
                sendResponds(serverSocket, receivePacket);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DatagramSocket getServerSocket() {
        return serverSocket;
    }
}