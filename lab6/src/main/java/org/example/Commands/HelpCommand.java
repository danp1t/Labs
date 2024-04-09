package org.example.Commands;
import org.example.Exceptions.InputFromFIleException;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;

import java.nio.ByteBuffer;

import static org.example.Managers.StartManager.getCommandManager;
import static org.example.Server.ServerResponds.setByteBuffer;

/**
 * Данный класс реализует команду help
 * Команда help выводит справку по доступным командам
 * Данный класс реализует интерфейс Command
 */
public class HelpCommand implements Command {
    /**
     * Метод исполнения команды
     * 1. Получаем список доступных команд
     * 2. Выводим имя команды и её описание
     */
    @Override
    public void execute(String[] tokens) {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        try {
            CommandManager commandManager = getCommandManager();
            if (tokens.length != 1) throw new InputUserException();
            for (Command command : commandManager.getCommands().values()){
                buffer.put((command.getNameCommand() + " - " + command.description() + "\n").getBytes());
            }
        }
        catch (InputUserException e) {
            buffer.put("Команда help не должна содержать аргументов".getBytes());
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
        return "выводит справку по доступным командам";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "help";
    }
}
