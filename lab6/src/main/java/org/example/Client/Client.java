package org.example.Client;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.CommandManager;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

import static org.example.Managers.StartManager.setCollectionManager;
import static org.example.Managers.StartManager.setCommandManager;


public class Client  {
    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            // Устанавливаем адрес и порт сервера, к которому будем подключаться
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 1234);


            //Отправить команду на сервер
            System.out.println("Клиент запущена!");
            while (true) {
                System.out.println("Введите команду: ");
                Scanner sc = new Scanner(System.in);
                String line = " ";
                line = sc.nextLine();
                // Создаем буфер для отправки данных
                ByteBuffer buffer = ByteBuffer.allocate(line.getBytes().length);


                buffer.put(line.getBytes());
                buffer.flip();

                // Отправляем данные на сервер
                channel.send(buffer, serverAddress);
                System.out.println("Данные отправлены");

                // Получаем ответ от сервера
                buffer.clear();
                channel.receive(buffer);
                buffer.flip();

                // Читаем данные из буфера
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                System.out.println("Received from server: " + new String(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
