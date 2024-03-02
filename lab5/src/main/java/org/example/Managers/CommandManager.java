package org.example.Managers;

import org.example.Commands.*;
import org.example.Interface.Command;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    //Конструктор
    private Map<String, Command> commands;
    public static ArrayDeque<Command> history_list = new ArrayDeque<>(13);
    public CommandManager(){
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("add", new AddCommand());
        commands.put("update_id", new UpdateCommand());
        commands.put("remove_by_id", new RemoveCommand());
        commands.put("clear", new ClearCommand());
        commands.put("save", new SaveCommand());
        commands.put("execute_script", new ExecuteCommand());
        commands.put("exit", new ExitCommand());
        commands.put("add_if_max", new AddIfMaxCommand());
        commands.put("add_if_min", new AddIfMinCommand());
        commands.put("history", new HistoryCommand());
        commands.put("min_by_semester_enum", new MinSemesterEnum());
        commands.put("count_greater_than_average_mark", new CountGreaterThanAverageMarkCommand());
        commands.put("filter_contains_name", new FilterContainsNameCommand());
        this.commands = commands;
    }


    public Map<String, Command> get_commands(){
        return commands;
    }

    public void add_command_in_history(Command command) {
        history_list.addLast(command);
        if (history_list.size() > 13){
            history_list.removeFirst();
        }

    };

}
