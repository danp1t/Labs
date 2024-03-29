package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.ElementManager;

import java.util.HashSet;
import java.util.Objects;

import static org.example.Managers.ElementManager.*;
import static org.example.Managers.StartManager.getCollectionManager;

/**
 * Данный класс реализует команду add
 * Команда add добавляет учебную группу в коллекцию HashSet
 * Данный класс реализует интерфейс Command
 */
public class AddCommand implements Command {
    /**
     * Метод исполнения команды
     */
    @Override
    public void execute(String[] tokens) {
        //Анализ команды
        try {
            CollectionManager collectionManager = getCollectionManager();
            if (tokens.length != 1) throw new InputUserException();
            //Запрос на ввод данных для элемента группы
            ElementManager elementManager = new ElementManager();
            StudyGroup group = elementManager.createStudyGroup(getIsUserInput());
            //Запрос нашей коллекции для добавления нового элемента в группу
            HashSet<StudyGroup> studyGroup = collectionManager.getStudyGroups();
            if (Objects.isNull(group)) {
                System.out.println("Произошла ошибка при выполнении команды add");
            }
            else {
                studyGroup.add(group);
                System.out.println("Группа добавлена!");
                collectionManager.setStudyGroups(studyGroup);
            }
        }
        catch (InputUserException e) {
            System.out.println("Команда add не должна содержать аргументов");
        }
    }
    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "add {element}";
    }
}
