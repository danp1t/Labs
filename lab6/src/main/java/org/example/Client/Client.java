package org.example.Client;

import org.example.Collections.StudyGroup;
import org.example.Managers.ElementManager;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Map;
import java.util.Scanner;

public class Client  {
    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            //Пнуть сервер
            // Устанавливаем адрес и порт сервера, к которому будем подключаться
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 1234);

            //Авторизация
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            buffer.put("Connection".getBytes());
            buffer.flip();
            channel.send(buffer, serverAddress);
            //Хочу получить commandManager с сервера.

            ClientListCommands clientListCommands = new ClientListCommands();
            Map<String, Integer> commands = clientListCommands.getCommands();
            //Установить на сервере id для пользователя
            //Выдать пользователю список команд (command manager)





            //Отправить команду на сервер
            System.out.println("Клиент запущена!");
            Scanner sc = new Scanner(System.in);
            String line;
            Commands command = null;
            while (true) {
                System.out.println("Введите команду: ");

                line = sc.nextLine();
                Integer type = commands.get(line.split(" ")[0]);

                if (type == null) {
                    System.out.println("Команда не найдена");
                    continue;
                }
                else if (type == 0) {
                    command = new Commands(line.strip().split(" ")[0]);
                }
                else if (type == 1) {
                    command = new Commands(line.strip().split(" ")[0], line.strip().split(" ")[1]);
                }
                else if (type == 2) {
                    //пнуть сервер и спросить следующий id для коллекции.
                    ElementManager elementManager = new ElementManager();
                    StudyGroup element = elementManager.createElement();
                    command = new Commands(line.strip().split(" ")[0], element);
                }
                else if (type == 3) {
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
                buffer = ByteBuffer.wrap(data);
                channel.send(buffer, serverAddress);

                System.out.println("Данные отправлены на сервер");
                System.out.println();
                // Получаем ответ от сервера
                buffer.clear();
                channel.receive(buffer);
                buffer.flip();

                // Читаем данные из буфера
//                byte[] data = new byte[buffer.remaining()];
//                buffer.get(data);
//                String receivedMessage = new String(data, StandardCharsets.UTF_8)
//                        .chars()
//                        .filter(c -> c != 0)
//                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                        .toString();
//                System.out.println(receivedMessage);
//                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
