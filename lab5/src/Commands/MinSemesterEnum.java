package Commands;

import Interface.Command;

public class MinSemesterEnum implements Command {
    @Override
    public void execute() {
        System.out.println("Вывод любого минимального элемента");
    }
}
