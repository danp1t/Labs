package org.example.Commands;

import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.print_info_HashSet;

public class InfoCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Информация о коллекции");
        System.out.println(print_info_HashSet());
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
