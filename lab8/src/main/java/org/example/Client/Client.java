package org.example.Client;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import static org.example.Client.ClientGetAnswer.getAnswer;
import static org.example.Client.ClientResponds.getRespond;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;

public class Client  {
    public static void main(String[] args) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            int port = 8932;

            channel.socket().setSoTimeout(5000);
            channel.configureBlocking(false);
            Scanner sc = new Scanner(System.in);
            boolean flag = false;
            boolean readFlag = true;
            String login = null;
            String password = null;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("helios.cs.ifmo.ru", port);
            //Авторизация
            System.out.println("Клиент запущен!");
            while (!flag) {
                //Отправить команду на сервер
                System.out.print("Введите ваш логин: ");
                login = sc.nextLine();
                System.out.print("Введите ваш пароль: ");
                password = sc.nextLine();
                String line = "authorizations";
                Commands command = getCommand(line, channel, serverAddress, buffer, login, password);
                buffer = sendCommand(command, channel, serverAddress, buffer);
                flag = getAnswer(buffer, channel);
                buffer.clear();
            }
            while (true) {
                buffer.clear();
                System.out.println("Введите команду: ");
                String line = sc.nextLine();
                Commands command = getCommand(line, channel, serverAddress, buffer, login, password);
                if (Objects.isNull(command)) {
                    continue;
                }
                buffer = sendCommand(command, channel, serverAddress, buffer);
                buffer.clear();
                getRespond(buffer, channel);
                }


            } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
