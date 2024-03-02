package org.example.Commands;

import org.example.Interface.Command;

public class RemoveCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Удалить элемент из коллекции");
    }

    @Override
    public String description() {
        return "удаляет элемент из коллекции по его id";
    }

    @Override
    public String get_name_command() {
        return "remove_by_id id";
    }
}
