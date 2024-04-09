package org.example.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

import static org.example.Server.ServerConnection.connection;

public class ServerReadRequest {
    public static DatagramPacket getDatagramPacket(DatagramSocket serverSocket) throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        return receivePacket;
    }

    public static String readRequest(DatagramPacket receivePacket) {
        String receivedMessage = new String(receivePacket.getData(), StandardCharsets.UTF_8)
                .chars()
                .filter(c -> c != 0)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return receivedMessage;
    }

}
