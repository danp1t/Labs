package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CommandManager;
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
    public void execute() {
        System.out.println("Последние 13 введенных команд: ");
        for (String command : CommandManager.history_list) {
            System.out.println(command);
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
    public String get_name_command() {
        return "history";
    }


}
