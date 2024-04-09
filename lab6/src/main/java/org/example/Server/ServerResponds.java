package org.example.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ServerResponds {
    public static void sendResponds(DatagramSocket serverSocket, DatagramPacket receivePacket) throws IOException {

        InetAddress clientAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();
        byte[] sendData = "Response from server".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
        serverSocket.send(sendPacket);
    }

}
