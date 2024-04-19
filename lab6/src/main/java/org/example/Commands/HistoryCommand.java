package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;

import java.nio.ByteBuffer;

import static org.example.Managers.StartManager.getCommandManager;
import static org.example.Server.ServerResponds.setByteBuffer;


/**
 * Данный класс реализует команду history
 * Команда history выводит последние 13 команд на экран
 * Данный класс реализует интерфейс Command
 */
public class HistoryCommand implements Command {
    /**
     * Метод выполнение команды
     */
    @Override
    public void execute(String name, String arg, StudyGroup element) {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
            CommandManager commandManager = getCommandManager();
            System.out.println();
            buffer.put("Последние 13 введенных команд: \n".getBytes());
            for (String command : commandManager.getHistoryList()) {
                System.out.println(command);
                buffer.put((command + "\n").getBytes());
            }
        setByteBuffer(buffer);
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит последние 13 команд (без их аргументов)";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "history";
    }


}
