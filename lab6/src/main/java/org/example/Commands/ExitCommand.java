package org.example.Commands;

import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;

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
    public void execute(String[] tokens) {
        try {
            if (tokens.length != 1) throw new InputUserException();
            System.out.println("Выход...");
            System.exit(0);
        }
        catch (InputUserException e) {
            System.out.println("Команда exit не должна содержать аргументов");
        }
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

