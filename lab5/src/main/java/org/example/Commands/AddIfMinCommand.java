package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Interface.Command;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.getElement;

/**
 * Данный класс реализует команду add_if_min
 * Команда add_if_min добавляет элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
 * Сравнение происходит по полю studentsCount
 * Данный класс реализует интерфейс Command
 */
public class AddIfMinCommand implements Command {
    /**
     * Метод исполнение команды
     */
    @Override
    public void execute() {
        System.out.println("Добавить элемент в коллекцию, если количество студентов в введенной группе минимально");
        //Нахождение минимального количества студентов
        HashSet<StudyGroup> studyGroups = getStudyGroups();
        Integer minStudentsCount = Integer.MAX_VALUE;
        for (StudyGroup group : studyGroups) {
            if (group.getStudentsCount() < minStudentsCount) {
                minStudentsCount = group.getStudentsCount();
            }
        }
        StudyGroup group = getElement();
        if (group.getStudentsCount() < minStudentsCount) {
            studyGroups.add(group);
            setStudyGroups(studyGroups);
        }
        else {
            System.out.println("Не удалось добавить элемент в коллекцию. Группа не минимальная :(");
        }
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand(){
        return "add_if_min {element}";
    }
}
