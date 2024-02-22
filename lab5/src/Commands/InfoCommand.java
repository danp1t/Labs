package Commands;

import Interface.Command;

public class InfoCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Информация о коллекции");
    }
}
