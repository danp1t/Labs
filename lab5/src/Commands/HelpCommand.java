package Commands;
import Interface.Command;
import Managers.CommandManager;

import java.util.Map;

public class HelpCommand implements Command {
    //Написать программу для исполнения команды help
    @Override
    public void execute() {
        CommandManager commands = new CommandManager();
        for (Command command : commands.get_commands().values()){
            System.out.println(command.get_name_command() + " - " + command.description());
        }
    }

    @Override
    public String description() {
        return "выводит справку по доступным командам";
    }

    @Override
    public String get_name_command() {
        return "help";
    }
}
