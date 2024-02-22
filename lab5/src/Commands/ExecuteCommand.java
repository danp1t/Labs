package Commands;

import Interface.Command;

public class ExecuteCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Считать и исполнить скрипт из файлв");
    }
}
