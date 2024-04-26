package org.example.Client;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientGetAnswer {
    public static boolean getAnswer(ByteBuffer buffer, DatagramChannel channel) throws IOException {
        byte[] data = new byte[buffer.remaining()];
        boolean flag = false;
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
            System.out.println(receivedMessage);
            System.out.println();
            if (flag) break;

        }
        buffer.clear();
    return !flag;
    }
}
