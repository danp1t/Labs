package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.study_groups;
import static org.example.Managers.CommandManager.element;

public class AddIfMaxCommand implements Command {
    @Override
    public void execute(){
        //Найти максимальный элемент в коллекции
        System.out.println("Добавить элемент в коллекцию, если количество студентов в новой группе превышает количество людей в любой группе");
        Integer max_students_count = 0;
        for (StudyGroup group : study_groups) {
            if (max_students_count < group.getStudentsCount()){
                max_students_count = group.getStudentsCount();
            }
        }

        //Прочитать элемент
        StudyGroup group = element;
        if (group.getStudentsCount() > max_students_count) {
            study_groups.add(group);
        }
        else {
            System.out.println("Не удалось добавить элемент в коллекцию. Группа не максимальная :(");
        }

    }

    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String get_name_command(){
        return "add_if_max {element}";
    }
}
