package org.example.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientSendCommand {
    public static ByteBuffer sendCommand(Commands command, DatagramChannel channel, InetSocketAddress serverAddress, ByteBuffer buffer) throws IOException, InterruptedException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            objectOutputStream.writeObject(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Отправка сериализованного объекта по DatagramChannel
        byte[] data = byteStream.toByteArray();
        channel.send(ByteBuffer.wrap(data), serverAddress);
        System.out.println("Данные отправлены на сервер");
        System.out.println();
        // Получаем ответ от сервера
        buffer.clear();

        Thread.sleep(450);
        while (channel.receive(buffer) == null) {
            Thread.sleep(5000);
            channel.send(ByteBuffer.wrap(data), serverAddress);
            System.out.println("Сервер недоступен. Сделаем попытку отправки пакета через 5 секунд");
            System.out.println("Данные отправлены на сервер");
            System.out.println();
            buffer.clear();

        }
        buffer.flip();
        return buffer;

    }
}
