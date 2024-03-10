package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

public class InfoCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Информация о коллекции");
        CollectionManager collectionManager = new CollectionManager();
        System.out.println(collectionManager.print_info_HashSet());
    }

    @Override
    public String description() {
        return "выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public String get_name_command() {
        return "info";
    }


}
