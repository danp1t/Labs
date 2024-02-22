package Commands;

import Interface.Command;

public class AddIfMinCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Что-то с минимумом");
    }

    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
