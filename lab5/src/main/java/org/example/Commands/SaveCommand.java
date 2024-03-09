package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

public class SaveCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Сохранить коллекцию в файл");
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.save_hashSet_to_file();
    }

    @Override
    public String description() {
        return "сохраняет коллекцию в файл";
    }

    @Override
    public String get_name_command() {
        return "save";
    }
}
