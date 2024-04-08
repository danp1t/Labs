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
import java.nio.charset.StandardCharsets;

import static org.example.Managers.StartManager.setCollectionManager;
import static org.example.Managers.StartManager.setCommandManager;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Сервер запущен!");
            DatagramSocket serverSocket = new DatagramSocket(1234);
            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData(), StandardCharsets.UTF_8)
                        .chars()
                        .filter(c -> c >= 32 && c < 127) // Фильтруем только печатные ASCII символы
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();


                System.out.println("Received from client: " + receivedMessage);
                String[] tokens = receivedMessage.split(" ");
                CommandManager commands = new CommandManager();
                CollectionManager collectionManager = new CollectionManager();
                setCollectionManager(collectionManager);
                setCommandManager(commands);
                try {
                    Command command = commands.getCommands().get(tokens[0]);
                    commands.addCommandToHistory(command);
                    command.execute(tokens);
                }
                catch (NullPointerException e) {
                    System.out.println("Команда не найдена");
                }
                // Обработка данных и отправка ответа
                // ...

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                byte[] sendData = "Response from server".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}