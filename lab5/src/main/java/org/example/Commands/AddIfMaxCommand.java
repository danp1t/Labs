package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.ElementManager;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.ElementManager.*;
import static org.example.Managers.StartManager.getCollectionManager;

/**
 * Данный класс реализует команду add_if_max
 * Команда add_if_max добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
 * Сравнение происходит по полю studentsCount
 * Данный класс реализует интерфейс Command
 */
public class AddIfMaxCommand implements Command {
    /**
     * Метод исполнения команды
     */
    @Override
    public void execute(String[] tokens){
        try {
            CollectionManager collectionManager = getCollectionManager();
            if (tokens.length != 1) throw new InputUserException();
            System.out.println("Добавить элемент в коллекцию, если количество студентов в новой группе превышает количество людей в любой группе");
            HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
            Integer maxStudentsCount = 0;
            for (StudyGroup group : studyGroups) {
                if (maxStudentsCount < group.getStudentsCount()){
                    maxStudentsCount = group.getStudentsCount();
                }
            }

            //Прочитать элемент
            ElementManager elementManager = new ElementManager();
            StudyGroup group = elementManager.createStudyGroup(getIsUserInput());
            if (group.getStudentsCount() > maxStudentsCount) {
                studyGroups.add(group);
                collectionManager.setStudyGroups(studyGroups);
                System.out.println("Учебная группа добавлена в коллекцию");
            }
            else {
                System.out.println("Не удалось добавить элемент в коллекцию. Группа не максимальная :(");
            }

            }
        catch (InputUserException e) {
            System.out.println("Команда add_if_max не должна содержать аргументов");
        }
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand(){
        return "add_if_max {element}";
    }
}
