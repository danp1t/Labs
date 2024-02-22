package Commands;

import Interface.Command;

public class SaveCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Сохранить коллекцию в файл");
    }
}
