package org.example.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientListCommands {
    private Map<String, Integer> commands;

    //name, type
    public ClientListCommands(){
        Map<String, Integer> commands = new HashMap<>();
        commands.put("help", 0);
        commands.put("info", 0);
        commands.put("show", 0);
        commands.put("add", 2);
        commands.put("update", 3);
        commands.put("remove_by_id", 1);
        commands.put("clear", 0);
        commands.put("execute_script", 1);
        commands.put("exit", -1);
        commands.put("add_if_max", 2);
        commands.put("add_if_min", 2);
        commands.put("history", 0);
        commands.put("min_by_semester_enum", 0);
        commands.put("count_greater_than_average_mark", 1);
        commands.put("filter_contains_name", 1);
        commands.put("ping", 0);
        this.commands = commands;
    }

    public Map<String, Integer> getCommands(){
        return commands;
    }
}
