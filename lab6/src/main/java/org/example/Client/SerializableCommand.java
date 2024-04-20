package org.example.Client;

import org.example.Collections.StudyGroup;
import org.example.Managers.ElementManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SerializableCommand {
    public static Commands getCommand(String line, DatagramChannel channel, InetSocketAddress serverAddress, ByteBuffer buffer) throws IOException, InterruptedException {

        ClientListCommands clientListCommands = new ClientListCommands();
        Map<String, Integer> commands = clientListCommands.getCommands();
        Integer type = commands.get(line.split(" ")[0]);
        channel.configureBlocking(false);
        Commands command = null;
        if (type == null) {
            System.out.println("Команда не найдена");
        }
        else if (type == -1) {
            if (line.split(" ").length != 1) {
                System.out.println("Команда не должна содержать аргументов");
            };
            command = new Commands("save");
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(command);

            // Отправка сериализованного объекта по DatagramChannel
            byte[] data = byteStream.toByteArray();
            channel.send(ByteBuffer.wrap(data), serverAddress);

            Thread.sleep(450);

            SocketAddress server = channel.receive(buffer);
            buffer.flip();
            while (server == null){
                Thread.sleep(5000);
                channel.send(ByteBuffer.wrap(data), serverAddress);
                System.out.println("Сервер недоступен. Сделаем попытку отправки пакета через 5 секунд");
                System.out.println("Данные отправлены на сервер");
                System.out.println();
                buffer.clear();
                server = channel.receive(buffer);
                buffer.flip();
            }
            data = new byte[buffer.remaining()];
            buffer.get(data);
            int n = ByteBuffer.wrap(data).getInt();
            for (int i = 0; i < n; i++) {
                buffer.clear();
                server = channel.receive(buffer);
                buffer.flip();
                data = new byte[buffer.remaining()];
                buffer.get(data);String receivedMessage = new String(data, StandardCharsets.UTF_8)
                        .chars()
                        .filter(c -> c != 0)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                System.out.println(receivedMessage);
                System.out.println();
            }
            buffer.clear();


            System.exit(0);

        }
        else if (type == 0) {
            if (line.split(" ").length != 1) {
                System.out.println("Команда не должна содержать аргументов");

            }
            else {
                command = new Commands(line.strip().split(" ")[0]);
            }
        }
        else if (type == 1) {
            if (line.split(" ").length != 2) {
                System.out.println("Команда должна содержать 1 аргумент");

            }
            else {
                command = new Commands(line.strip().split(" ")[0], line.strip().split(" ")[1]);
            }
        }
        else if (type == 2) {
            if (line.split(" ").length != 1) {
                System.out.println("Команда не должна содержать аргументов");
            }
            else {
            //пнуть сервер и спросить следующий id для коллекции.
                ElementManager elementManager = new ElementManager();
                StudyGroup element = elementManager.createElement();
                command = new Commands(line.strip().split(" ")[0], element);
            }
        }
        else if (type == 3) {
            if (line.split(" ").length != 2) {
                System.out.println("Команда должна содержать 1 аргумент");
            }
            else {
                ElementManager elementManager = new ElementManager();
                StudyGroup element = elementManager.createElement();
                command = new Commands(line.strip().split(" ")[0], line.strip().split(" ")[1], element);
            }
        }
        return command;
    }
}
