package Commands;

import Interface.Command;

public class AddIfMaxCommand implements Command {
    @Override
    public void execute(){
        System.out.println("Что-то с максимальным элементом");
    }
}
