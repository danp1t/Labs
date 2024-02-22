package Commands;

import Interface.Command;

public class AddIfMaxCommand implements Command {
    @Override
    public void execute(){
        System.out.println("Что-то с максимальным элементом");
    }

    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
