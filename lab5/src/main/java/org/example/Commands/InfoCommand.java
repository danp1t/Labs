package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;

/**
 * Данный класс реализует команду info
 * Команда info выводит информацию о коллекции(тип коллекции, дата инициализации, количество элементов, является ли множество пустым)
 * Данный класс реализует интерфейс Command
 */
public class InfoCommand implements Command {
    /**
     * Метод исполнение команды
     */
    @Override
    public void execute(String[] tokens) {
        try {
            if (tokens.length != 1) throw new InputUserException();
            System.out.println("Информация о коллекции");
            System.out.println(printInfoHashSet());
        }
        catch (InputUserException e) {
            System.out.println("Команда info не должна содержать аргументов");
        }
    }


    /**
     * Вспомогательный метод для команды info
     * @return строку для команды info
     */
    private static String printInfoHashSet(){
        CollectionManager collectionManager = new CollectionManager();
        HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        return "Тип: " + studyGroups.getClass() + "\n" +
                "Дата инициализации: " + collectionManager.getCreateDateHashSet() + "\n" +
                "Количество элементов: " + studyGroups.size() + "\n" +
                "Множество пустое: " + studyGroups.isEmpty();
    }
    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "info";
    }


}
