package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CommandManager;

public class HistoryCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Последние 13 введенных команд: ");
        for (String command : CommandManager.history_list) {
            System.out.println(command);
        }


    }
    @Override
    public String description() {
        return "выводит последние 13 команд (без их аргументов)";
    }

    @Override
    public String get_name_command() {
        return "history";
    }


}
