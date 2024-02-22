package Commands;

import Interface.Command;

public class HistoryCommand implements Command {
    @Override
    public void execute() {
        System.out.println("История...");
    }
}
