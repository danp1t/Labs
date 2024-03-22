package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.getElement;

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
            if (tokens.length != 1) throw new InputUserException();
            System.out.println("Добавить элемент в коллекцию, если количество студентов в новой группе превышает количество людей в любой группе");
            HashSet<StudyGroup> studyGroups = getStudyGroups();
            Integer maxStudentsCount = 0;
            for (StudyGroup group : studyGroups) {
                if (maxStudentsCount < group.getStudentsCount()){
                    maxStudentsCount = group.getStudentsCount();
                }
            }

            //Прочитать элемент
            StudyGroup group = createStudyGroup(true);
            if (group.getStudentsCount() > maxStudentsCount) {
                studyGroups.add(group);
                setStudyGroups(studyGroups);
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
