package Commands;

import Interface.Command;

public class ShowCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Все элементы коллекции в строковом представлении");
    }
}
