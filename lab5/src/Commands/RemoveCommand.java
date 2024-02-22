package Commands;

import Interface.Command;

public class RemoveCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Удалить элемент из коллекции");
    }
}
