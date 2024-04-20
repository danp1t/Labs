package org.example.Server;


import org.example.Client.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static org.example.Server.ServerCommandHandler.handlerCommand;
import static org.example.Server.ServerConnection.connection;
import static org.example.Server.ServerReadRequest.readRequest;
import static org.example.Server.ServerResponds.sendResponds;;

public class Server {
    private static DatagramSocket serverSocket;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws SocketException {
        serverSocket = connection();
        try {
            logger.info("Server started");
            while (true) {
                DatagramPacket receivePacket = ServerReadRequest.getDatagramPacket(serverSocket);
                Commands receivedMessage = readRequest(receivePacket);
                logger.info("Received from client: " + receivedMessage.getName());
                handlerCommand(receivedMessage);
                sendResponds(serverSocket, receivePacket);
                logger.info("Response sent to client");
            }


        } catch (Exception e) {
            logger.error("Error occurred: ", e);
        }
    }
    public static DatagramSocket getServerSocket() {
        return serverSocket;
    }
}