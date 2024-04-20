package org.example.Client;

import org.example.Collections.StudyGroup;
import org.example.Managers.ElementManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static org.example.Client.ClientResponds.getRespond;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;

public class Client  {
    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.socket().setSoTimeout(5000);
            channel.configureBlocking(false);

            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 1234);

            //Авторизация
            ByteBuffer buffer = ByteBuffer.allocate(8192);

            //Отправить команду на сервер
            System.out.println("Клиент запущена!");
            Scanner sc = new Scanner(System.in);

            while (true) {
                buffer.clear();
                channel.configureBlocking(false);
                System.out.println("Введите команду: ");
                String line = sc.nextLine();
                Commands command = getCommand(line, channel, serverAddress, buffer);
                if (Objects.isNull(command)) {
                    continue;
                }
                buffer = sendCommand(command, channel, serverAddress, buffer);
                getRespond(buffer, channel);
                }

                // Читаем данные из буфера

            } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
