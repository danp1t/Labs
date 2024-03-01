package Commands;

import Interface.Command;
import Managers.CommandManager;

import java.lang.reflect.Field;

public class HistoryCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Последние 13 введенных команд: ");
        for (Command command : CommandManager.history_list) {
            System.out.println(command.get_name_command());
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
