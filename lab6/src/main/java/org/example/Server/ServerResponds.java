package org.example.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;


public class ServerResponds {
    public static ByteBuffer byteBuffer;


    public static void sendResponds(DatagramSocket serverSocket, DatagramPacket receivePacket) throws IOException {
         InetAddress clientAddress = receivePacket.getAddress();
         int clientPort = receivePacket.getPort();

         byte[] sendData = byteBuffer.array();
         DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
         serverSocket.send(sendPacket);
    }

    public static  void setByteBuffer(ByteBuffer buffer) {
        byteBuffer = buffer;
    }

    public static ByteBuffer getByteBuffer() {
        return byteBuffer;
    }
}
