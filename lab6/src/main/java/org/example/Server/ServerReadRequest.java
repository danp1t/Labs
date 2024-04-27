package org.example.Server;

import org.example.Client.Commands;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerReadRequest implements Runnable{
    private Commands receivedMessage;
    private DatagramPacket receivePacket;

    public ServerReadRequest(DatagramPacket receivePacket) {
        this.receivePacket = receivePacket;
    }

    public Commands getReceivedMessage() {
        return receivedMessage;
    }

    public static DatagramPacket getDatagramPacket(DatagramSocket serverSocket) throws IOException {
        byte[] receiveData = new byte[8192];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        return receivePacket;
    }

    @Override
    public void run() {
        try {
             receivedMessage = readRequest(receivePacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Commands readRequest(DatagramPacket receivePacket) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(receivePacket.getData());


        ObjectInputStream objectStream = new ObjectInputStream(byteStream);

        Commands receivedCommand = (Commands) objectStream.readObject();
        return receivedCommand;
    }

}
