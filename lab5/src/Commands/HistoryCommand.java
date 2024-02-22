package Commands;

import Interface.Command;

public class HistoryCommand implements Command {
    @Override
    public void execute() {
        System.out.println("История...");
    }

    @Override
    public String description() {
        return "выводит последние 13 команд (без их аргументов)";
    }
}
