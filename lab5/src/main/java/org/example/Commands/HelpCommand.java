package org.example.Commands;
import org.example.Exceptions.InputFromFIleException;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;

import static org.example.Managers.StartManager.getCommandManager;

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
        try {
            CommandManager commandManager = getCommandManager();
            if (tokens.length != 1) throw new InputUserException();
            for (Command command : commandManager.getCommands().values()){
                System.out.println(command.getNameCommand() + " - " + command.description());
            }
        }
        catch (InputUserException e) {
            System.out.println("Команда help не должна содержать аргументов");
        }
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
