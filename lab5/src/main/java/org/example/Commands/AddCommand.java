package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.getElement;

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
            if (tokens.length != 1) throw new InputUserException();
            //Запрос на ввод данных для элемента группы


            //Запрос нашей коллекции для добавления нового элемента в группу
            HashSet studyGroup = getStudyGroups();
            System.out.println("Группа добавлена!");
            StudyGroup group = getElement();
            studyGroup.add(group);
            setStudyGroups(studyGroup);
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
