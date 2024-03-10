package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import static org.example.Managers.CollectionManager.print_min_by_semester_enum;

public class MinSemesterEnum implements Command {
    @Override
    public void execute() {
        System.out.println("Вывод любого объекта из коллекции, значение поля semesterEnum которого является минимальным");
        System.out.println(print_min_by_semester_enum());
    }

    @Override
    public String description() {
        return "выводит любой объект из коллекции, значение поля semesterEnum которого является минимальным";
    }

    @Override
    public String get_name_command() {
        return "min_by_semester_enum";
    }
}
