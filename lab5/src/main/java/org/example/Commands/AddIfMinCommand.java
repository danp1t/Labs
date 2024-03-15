package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.study_groups;
import static org.example.Managers.CommandManager.element;

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
        Integer min_students_count = 2099999999;
        for (StudyGroup group : study_groups) {
            if (group.getStudentsCount() < min_students_count) {
                min_students_count = group.getStudentsCount();
            }
        }
        StudyGroup group = element;
        if (group.getStudentsCount() < min_students_count) {
            study_groups.add(group);
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
