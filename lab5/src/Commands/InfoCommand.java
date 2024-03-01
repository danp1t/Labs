package Commands;

import Interface.Command;

public class InfoCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Информация о коллекции");
    }

    @Override
    public String description() {
        return "выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public String get_name_command() {
        return "info";
    }
}
