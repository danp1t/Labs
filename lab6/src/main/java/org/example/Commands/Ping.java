package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Interface.Command;

import java.nio.ByteBuffer;

import static org.example.Server.ServerResponds.byteBufferArrayList;

public class Ping implements Command {
    /**
     * Метод выполнения программы
     */
    @Override
    public void execute(String name, String arg, StudyGroup element) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Я жив".getBytes());

        byteBufferArrayList.add(buffer);
        buffer.clear();
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "Пинает сервер";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "ping";
    }
}