package Commands;

import Interface.Command;

public class ExecuteCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Считать и исполнить скрипт из файлв");
    }

    @Override
    public String description() {
        return "считывает и исполняет скрипт из указанного файла";
    }
}
