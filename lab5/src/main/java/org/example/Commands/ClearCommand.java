package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import static org.example.Managers.CollectionManager.clear_hashSet;

public class ClearCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Очистить коллекцию");
        clear_hashSet();
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
