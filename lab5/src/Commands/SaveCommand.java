package Commands;

import Interface.Command;

public class SaveCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Сохранить коллекцию в файл");
    }

    @Override
    public String description() {
        return "сохраняет коллекцию в файл";
    }

    @Override
    public String get_name_command() {
        return "save";
    }
}
