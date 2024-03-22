package org.example.Commands;

import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import static org.example.Managers.CommandManager.getHistoryList;
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
    public void execute(String[] tokens) {
        try {
            if (tokens.length != 1) throw new InputUserException();
            System.out.println("Последние 13 введенных команд: ");
            for (String command : getHistoryList()) {
                System.out.println(command);
            }
        }
        catch (InputUserException e) {
            System.out.println("Команда history не должна содержать аргументов");
        }
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
