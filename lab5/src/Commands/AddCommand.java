package Commands;

import Interface.Command;

public class AddCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Добавить команду...");
    }

    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию";
    }

}
