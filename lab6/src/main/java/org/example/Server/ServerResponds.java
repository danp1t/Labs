package org.example.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class ServerResponds {
    public static ArrayList<ByteBuffer> byteBufferArrayList = new ArrayList<ByteBuffer>();


    public static void sendResponds(DatagramSocket serverSocket, DatagramPacket receivePacket) throws IOException {
         InetAddress clientAddress = receivePacket.getAddress();
         int clientPort = receivePacket.getPort();
         int size = byteBufferArrayList.size();
         byte[] sendData = ByteBuffer.allocate(Integer.BYTES).putInt(size).array();

         DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
         serverSocket.send(sendPacket);
         for (ByteBuffer buffer : byteBufferArrayList) {
            sendData = buffer.array();
            sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);

         }
        byteBufferArrayList.clear();
    }

}
