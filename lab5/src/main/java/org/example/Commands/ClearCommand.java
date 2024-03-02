package org.example.Commands;

import org.example.Interface.Command;

public class ClearCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Очистить коллекцию");
    }

    @Override
    public String description() {
        return "очищает коллекцию";
    }

    @Override
    public String get_name_command() {
        return "clear";
    }
}
