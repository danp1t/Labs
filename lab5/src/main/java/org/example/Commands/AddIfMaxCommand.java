package org.example.Commands;

import org.example.Interface.Command;

public class AddIfMaxCommand implements Command {
    @Override
    public void execute(){

        System.out.println("Что-то с максимальным элементом");
    }

    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String get_name_command(){
        return "add_if_max {element}";
    }
}
