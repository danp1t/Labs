package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.study_groups;
import static org.example.Managers.CommandManager.element;

public class AddIfMinCommand implements Command {
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

    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String get_name_command(){
        return "add_if_min {element}";
    }
}
