package Managers;

import Interface.Command;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    //Конструктор
    private Map<String, Command> commands;
    private ArrayDeque<Command> history_list = new ArrayDeque<>(13);
    public CommandManager(){
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new Commands.HelpCommand());
        commands.put("info", new Commands.InfoCommand());
        commands.put("show", new Commands.ShowCommand());
        commands.put("add", new Commands.AddCommand());
        commands.put("update_id", new Commands.UpdateCommand());
        commands.put("remove_by_id", new Commands.RemoveCommand());
        commands.put("clear", new Commands.ClearCommand());
        commands.put("save", new Commands.SaveCommand());
        commands.put("execute_script", new Commands.ExecuteCommand());
        commands.put("exit", new Commands.ExitCommand());
        commands.put("add_if_max", new Commands.AddIfMaxCommand());
        commands.put("add_if_min", new Commands.AddIfMinCommand());
        commands.put("history", new Commands.HistoryCommand());
        commands.put("min_by_semester_enum", new Commands.MinSemesterEnum());
        commands.put("count_greater_than_average_mark", new Commands.CountGreaterThanAverageMarkCommand());
        commands.put("filter_contains_name", new Commands.FilterContainsNameCommand());
        this.commands = commands;
    }


    public Map<String, Command> get_commands(){
        return commands;
    }

    public void add_command_in_history(Command command){
        history_list.addLast(command);
        if (history_list.size() > 13){
            history_list.removeFirst();
        }

    };
    public void get_history(){
        for (Command element : history_list) {
            System.out.println(element);
        }
    }
}
