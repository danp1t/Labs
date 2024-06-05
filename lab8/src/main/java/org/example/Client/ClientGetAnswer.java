package org.example.Client;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientGetAnswer {
    public static int getAnswer(ByteBuffer buffer, DatagramChannel channel) throws IOException {
        byte[] data = new byte[buffer.remaining()];
        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        buffer.get(data);
        int n = ByteBuffer.wrap(data).getInt();
        for (int i = 0; i < n; i++) {
            buffer.clear();
            SocketAddress server = channel.receive(buffer);
            buffer.flip();
            data = new byte[buffer.remaining()];
            buffer.get(data);
            String receivedMessage = new String(data, StandardCharsets.UTF_8)
                    .chars()
                    .filter(c -> c != 0)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            flag = receivedMessage.equals("Вы ввели неправильный пароль. Попробуйте еще раз: ");
            flag2 = receivedMessage.contains("Логин не обнаружен в базе данных");
            flag3 = receivedMessage.contains("Напрягите свою память, чтобы вспомнить пароль");

            System.out.println(receivedMessage);
            System.out.println();
            if (flag3) break;
            if (flag) break;
            if (flag2) break;

        }

        buffer.clear();
        if (flag3) {
            return 3;
        }
        if (flag2) {
            return 2;
        }
        if (!flag) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
