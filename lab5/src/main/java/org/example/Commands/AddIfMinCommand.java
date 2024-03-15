package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Interface.Command;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.get_element;

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
        HashSet<StudyGroup> studyGroups = get_study_groups();
        Integer min_students_count = 2099999999;
        for (StudyGroup group : studyGroups) {
            if (group.getStudentsCount() < min_students_count) {
                min_students_count = group.getStudentsCount();
            }
        }
        StudyGroup group = get_element();
        if (group.getStudentsCount() < min_students_count) {
            studyGroups.add(group);
            set_study_groups(studyGroups);
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
    public String get_name_command(){
        return "add_if_min {element}";
    }
}
