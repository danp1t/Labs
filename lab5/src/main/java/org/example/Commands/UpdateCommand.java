package org.example.Commands;

import org.example.Interface.Command;

public class UpdateCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Обновить значение ячейки из коллекции");
    }

    @Override
    public String description() {
        return "обновляет значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String get_name_command() {
        return "update id {element}";
    }
}

