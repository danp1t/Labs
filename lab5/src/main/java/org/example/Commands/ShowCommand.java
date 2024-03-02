package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

public class ShowCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Все элементы коллекции в строковом представлении");
        CollectionManager collectionManager = new CollectionManager();
        System.out.println(collectionManager.beatiful_output_json());
    }

    @Override
    public String description() {
        return "выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String get_name_command() {
        return "show";
    }





}
