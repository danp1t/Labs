package Commands;

import Interface.Command;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Выход...");
    }
}
