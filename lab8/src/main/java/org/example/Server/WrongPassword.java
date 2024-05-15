package org.example.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import static org.example.Server.ServerResponds.byteBufferArrayList;

public class WrongPassword {
    public static void sendMessage(DatagramSocket serverSocket, DatagramPacket receivePacket, String line) throws IOException {
        InetAddress clientAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        buffer.put(line.getBytes());
        byteBufferArrayList.add(buffer);
        int size = byteBufferArrayList.size();
        byte[] sendData = ByteBuffer.allocate(Integer.BYTES).putInt(size).array();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
        serverSocket.send(sendPacket);
        for (ByteBuffer buffer123 : byteBufferArrayList) {
            sendData = buffer123.array();
            sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);

        }
        byteBufferArrayList.clear();
    }
}
