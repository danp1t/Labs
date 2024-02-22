package Commands;

import Interface.Command;

public class ClearCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Очистить коллекцию");
    }

    @Override
    public String description() {
        return "очищает коллекцию";
    }
}
