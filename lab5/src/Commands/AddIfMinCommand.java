package Commands;

import Interface.Command;

public class AddIfMinCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Что-то с минимумом");
    }
}
