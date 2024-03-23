package org.example.Commands;

import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import static org.example.Managers.StartManager.getCollectionManager;


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
    public void execute(String[] tokens) {
        try {
            CollectionManager collectionManager = getCollectionManager();
            if (tokens.length != 1) throw new InputUserException();
            System.out.println("Очистить коллекцию");
            collectionManager.getStudyGroups().clear();
            System.out.println("Коллекция очищена");
        }
        catch (InputUserException e) {
            System.out.println("Команда add_if_min не должна содержать аргументов");
        }
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
