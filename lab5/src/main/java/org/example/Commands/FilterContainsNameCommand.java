package org.example.Commands;

import org.example.Collections.Person;
import org.example.Collections.StudyGroup;
import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.history_list;

public class FilterContainsNameCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Фильтр...");
        if (study_groups == null) {get_HashSet();}
        boolean flag = false;
        String filter_name = history_list.getLast().split(" ")[1];

        for (StudyGroup group : study_groups){
            String name = group.getName();
            Person admin = group.getGroupAdmin();
            String admin_name = admin.getName();

            if (filter_name.equals(name) || filter_name.equals(admin_name)) {
                System.out.println(beatiful_output_element_json(parse_studyGroup_to_json(group)));
                flag = true;
            }

        }
        if (!flag) {
            System.out.println("Ничего с таким именем не было найдено");
        }
    }

    @Override
    public String description() {
        return "выводит элементы, значение поля name которых содержит заданную подстроку";
    }

    @Override
    public String get_name_command() {
        return "filter_contains_name name";
    }
}

