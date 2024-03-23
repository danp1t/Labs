package org.example.Commands;

import org.example.Collections.Person;
import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.util.HashSet;

import static org.example.Managers.StartManager.getCollectionManager;

/**
 * Данный класс реализует команду filter_contains_name
 * Команда filter_contains_name выводит элементы, значение поля name которых содержит заданную подстроку
 * Данный класс реализует интерфейс Command
 */
public class FilterContainsNameCommand implements Command {
    /**
     * Метод выполнение команды
     * 1. Считываем подстроку
     * 2. Смотрим поля name у каждого элемента
     * 3. Выводим результат
     */
    @Override
    public void execute(String[] tokens) {
        //Считать аргумент
        try {
            CollectionManager collectionManager = getCollectionManager();
            if (tokens.length != 2) throw new InputUserException();
            String filterName = tokens[1];
            HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
            System.out.println("Поиск элементов, поля name которых содержат подстроку: " + filterName);

            boolean flag = false;
            for (StudyGroup group : studyGroups){
                String name = group.getName();
                Person admin = group.getGroupAdmin();
                String adminName = admin.getName();
                if (name.contains(filterName) || adminName.contains(filterName)) {
                    System.out.println(collectionManager.beatifulOutputElementJson(collectionManager.parseStudyGroupToJson(group)));
                    flag = true;
                }
            }
            if (!flag) {
                System.out.println("Не найдено ни одного поля с заданной подстрокой");
            }
        }
        catch (InputUserException e) {
            System.out.println("Неверно введены аргументы для команды filter_contains_name");
        }
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит элементы, значение поля name которых содержит заданную подстроку";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "filter_contains_name name";
    }
}

