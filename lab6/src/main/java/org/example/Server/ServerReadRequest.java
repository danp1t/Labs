package org.example.Server;

import org.example.Client.Commands;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerReadRequest {
    public static DatagramPacket getDatagramPacket(DatagramSocket serverSocket) throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        return receivePacket;
    }

    public static Commands readRequest(DatagramPacket receivePacket) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(receivePacket.getData());
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        Commands receivedCommand = (Commands) objectStream.readObject();
        return receivedCommand;
    }

}
