package org.example.Client;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class ClientResponds {
    public static void getRespond(ByteBuffer buffer, DatagramChannel channel) throws IOException {
        byte[] data = new byte[buffer.remaining()];
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
            System.out.println(receivedMessage);
            System.out.println();
        }
        buffer.clear();

    }
}
