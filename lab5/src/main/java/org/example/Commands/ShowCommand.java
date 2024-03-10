package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import static org.example.Managers.CollectionManager.beatiful_output_json;
import static org.example.Managers.CollectionManager.parse_hashset_to_json;

public class ShowCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Все элементы коллекции в строковом представлении");
        System.out.println(beatiful_output_json());
        parse_hashset_to_json();
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
