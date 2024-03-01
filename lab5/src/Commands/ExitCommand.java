package Commands;

import Interface.Command;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Выход...");
        System.exit(0);
    }

    @Override
    public String description() {
        return "завершает программу (без сохранения в файл)";
    }

    @Override
    public String get_name_command() {
        return "exit";
    }
}

