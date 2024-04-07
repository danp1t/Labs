package org.example.Client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;



public class Client  {
    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            // Устанавливаем адрес и порт сервера, к которому будем подключаться
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 1234);
            // Создаем буфер для отправки данных
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("Hello, UDP Server!".getBytes());
            buffer.flip();

            // Отправляем данные на сервер
            System.out.println("Данные отправлены");
            channel.send(buffer, serverAddress);

            // Получаем ответ от сервера
            buffer.clear();
            channel.receive(buffer);
            buffer.flip();

            // Читаем данные из буфера
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            System.out.println("Received from server: " + new String(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
