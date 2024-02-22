package Commands;

import Interface.Command;

public class UpdateCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Обновить значение ячейки из коллекции");
    }
}
