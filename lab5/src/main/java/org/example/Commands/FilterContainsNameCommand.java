package org.example.Commands;

import org.example.Collections.Person;
import org.example.Collections.StudyGroup;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.history_list;

public class FilterContainsNameCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Фильтр...");
        String filter = history_list.getLast().split(" ")[1];

        for (StudyGroup group : study_groups){
            String name = group.getName();
            Person admin = group.getGroupAdmin();
            String admin_name = admin.getName();

            if (filter == name || filter == admin_name) {
                System.out.println(beatiful_output_element_json(parse_studyGroup_to_json(group)));
            }
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

