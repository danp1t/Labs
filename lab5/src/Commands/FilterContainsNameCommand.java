package Commands;

import Interface.Command;
import org.w3c.dom.ls.LSOutput;

public class FilterContainsNameCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Фильтр...");
    }
}
