package Commands;

import Interface.Command;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Выход...");
    }

    @Override
    public String description() {
        return "завершает программу (без сохранения в файл)";
    }
}
