package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

public class ClearCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Очистить коллекцию");
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.clear_hashSet();
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
