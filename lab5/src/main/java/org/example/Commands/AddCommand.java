package org.example.Commands;

import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.study_groups;
import static org.example.Managers.CommandManager.element;

public class AddCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Группа добавлена!");
        study_groups.add(element);
    }

    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию";
    }

    @Override
    public String get_name_command() {
        return "add {element}";
    }
}
