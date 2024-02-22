package Commands;

import Interface.Command;

public class CountGreaterThanAverageMarkCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Имена что-то там");
    }

    @Override
    public String description() {
        return "выводит количество элементов, значение поля averageMark которых больше заданного";
    }
}
