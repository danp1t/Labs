package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;

import java.nio.ByteBuffer;

import static org.example.Server.ServerResponds.byteBufferArrayList;

/**
 * Данный класс реализует команду exit
 * Команда exit завершает программу без сохранения в файл
 * Данный класс реализует интерфейс Command
 */
public class ExitCommand implements Command {
    /**
     * Метод выполнения программы
     */
    @Override
    public synchronized void execute(String name, String arg, StudyGroup element, String login) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Выход...".getBytes());
        System.exit(0);

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
        return "завершает программу (без сохранения в файл)";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "exit";
    }
}

