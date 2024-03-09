package org.example.Commands;

import org.example.Interface.Command;
import org.w3c.dom.ls.LSOutput;

public class FilterContainsNameCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Фильтр...");
    }

    @Override
    public String description() {
        return "выводит элементы, значение поля name которых содержит заданную подстроку";
    }

    @Override
    public String get_name_command() {
        return "filter_contains_name name";
    }
}
