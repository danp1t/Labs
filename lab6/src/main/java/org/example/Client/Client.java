package org.example.Client;

import org.example.Collections.StudyGroup;
import org.example.Managers.ElementManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class Client  {
    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.socket().setSoTimeout(5000);
            channel.configureBlocking(false);

            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 1234);

            //Авторизация
            ByteBuffer buffer = ByteBuffer.allocate(8192);

            ClientListCommands clientListCommands = new ClientListCommands();
            Map<String, Integer> commands = clientListCommands.getCommands();

            //Отправить команду на сервер
            System.out.println("Клиент запущена!");
            Scanner sc = new Scanner(System.in);
            String line;
            Commands command = null;
            while (true) {
                channel.configureBlocking(true);
                System.out.println("Введите команду: ");

                line = sc.nextLine();
                Integer type = commands.get(line.split(" ")[0]);
                channel.configureBlocking(false);
                if (type == null) {
                    System.out.println("Команда не найдена");
                    continue;
                }
                else if (type == -1) {
                    if (line.split(" ").length != 1) {
                        System.out.println("Команда не должна содержать аргументов");
                        continue;
                    };
                    command = new Commands("save");
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
                    objectOutputStream.writeObject(command);

                    // Отправка сериализованного объекта по DatagramChannel
                    byte[] data = byteStream.toByteArray();
                    channel.send(ByteBuffer.wrap(data), serverAddress);
                    System.exit(0);

                }
                else if (type == 0) {
                    if (line.split(" ").length != 1) {
                        System.out.println("Команда не должна содержать аргументов");
                        continue;
                    }
                    command = new Commands(line.strip().split(" ")[0]);
                }
                else if (type == 1) {
                    if (line.split(" ").length != 2) {
                        System.out.println("Команда должна содержать 1 аргумент");
                        continue;
                    };
                    command = new Commands(line.strip().split(" ")[0], line.strip().split(" ")[1]);
                }
                else if (type == 2) {
                    if (line.split(" ").length != 1) {
                        System.out.println("Команда не должна содержать аргументов");
                        continue;
                    };
                    //пнуть сервер и спросить следующий id для коллекции.
                    ElementManager elementManager = new ElementManager();
                    StudyGroup element = elementManager.createElement();
                    command = new Commands(line.strip().split(" ")[0], element);
                }
                else if (type == 3) {
                    if (line.split(" ").length != 2) continue;
                    ElementManager elementManager = new ElementManager();
                    StudyGroup element = elementManager.createElement();
                    command = new Commands(line.strip().split(" ")[0], line.strip().split(" ")[1], element);
                }

                // Создаем буфер для отправки данных
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
                objectOutputStream.writeObject(command);

                // Отправка сериализованного объекта по DatagramChannel
                byte[] data = byteStream.toByteArray();
                channel.send(ByteBuffer.wrap(data), serverAddress);
                System.out.println("Данные отправлены на сервер");
                System.out.println();
                // Получаем ответ от сервера
                buffer.clear();
                Thread.sleep(200);


                SocketAddress server = channel.receive(buffer);
                buffer.flip();
                if (server == null){
                    System.out.println("Сервер не отвечает. Попробуйте еще раз.");
                }
                else {
                    data = new byte[buffer.remaining()];
                    buffer.get(data);
                    int n = ByteBuffer.wrap(data).getInt();
                    for (int i = 0; i < n; i++) {
                        buffer.clear();
                        server = channel.receive(buffer);
                        buffer.flip();
                        data = new byte[buffer.remaining()];
                        buffer.get(data);
                        String receivedMessage = new String(data, StandardCharsets.UTF_8)
                                .chars()
                                .filter(c -> c != 0)
                                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                .toString();
                        System.out.println(receivedMessage);
                        System.out.println();
                    }
                    buffer.clear();

                }

                // Читаем данные из буфера

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
