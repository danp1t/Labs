package org.example.Commands;

import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.clearHashSet;

/**
 * Данный класс реализует команду clear
 * Команда clear очищает коллекцию HashSet
 * Данный класс реализует интерфейс Command
 */
public class ClearCommand implements Command {
    /**
     * Метод выполнение команды
     */
    @Override
    public void execute() {
        System.out.println("Очистить коллекцию");
        clearHashSet();
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "очищает коллекцию";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "clear";
    }
}
