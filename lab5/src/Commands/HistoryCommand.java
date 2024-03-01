package Commands;

import Interface.Command;
import Managers.CommandManager;
import java.lang.reflect.Field;
import java.util.ArrayDeque;

public class HistoryCommand implements Command {

    @Override
    public void execute() {
        System.out.println("История...");

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
