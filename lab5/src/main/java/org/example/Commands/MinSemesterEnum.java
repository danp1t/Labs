package org.example.Commands;

import org.example.Interface.Command;

public class MinSemesterEnum implements Command {
    @Override
    public void execute() {
        System.out.println("Вывод любого минимального элемента");
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