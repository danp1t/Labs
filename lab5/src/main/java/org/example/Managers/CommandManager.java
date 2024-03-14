package org.example.Managers;

import org.example.Collections.StudyGroup;
import org.example.Commands.*;
import org.example.Interface.Command;

import java.util.*;

import static org.example.Managers.CollectionManager.create_study_group;
import static org.example.Managers.CollectionManager.update_study_group;

public class CommandManager {

    //Конструктор
    public static int status_command = 0;
    private static Map<String, Command> commands;
    public static StudyGroup element;
    public static ArrayDeque<String> history_list = new ArrayDeque<>(13);
    public CommandManager(){
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("add", new AddCommand());
        commands.put("update", new UpdateCommand());
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

    public boolean is_simple_command(String command){
        String[] simpleCommand = {"help", "info", "show", "clear", "save", "exit", "history", "min_by_semester_enum"};
        boolean contains = Arrays.asList(simpleCommand).contains(command);
        if (contains) {return true;}
        else {return false;}
    }

    public boolean is_command_with_one_arg(String command){
        String[] commandWithOneArg = {"remove_by_id", "execute_script", "count_greater_than_average_mark", "filter_contains_name"};
        boolean contains = Arrays.asList(commandWithOneArg).contains(command);
        if (contains) {return true;}
        else {return false;}
    }

    public boolean is_command_with_element(String command){
        String[] commandWithElement = {"add", "add_if_max", "add_if_min"};
        boolean contains = Arrays.asList(commandWithElement).contains(command);
        if (contains) {return true;}
        else {return false;}
    }

    public boolean is_command_with_element_and_one_arg(String command){
        String[] commandWithElement = {"update"};
        boolean contains = Arrays.asList(commandWithElement).contains(command);
        if (contains) {return true;}
        else {return false;}

    }

    public void add_command_in_history(Command command) {
        String name = command.get_name_command();
        history_list.addLast(name);
        if (history_list.size() > 13){
            history_list.removeFirst();
        }
    };
    public void processing_element(Command command, Scanner sc, boolean is_user_input){
        String name = command.get_name_command();
        history_list.addLast(name);
        if (history_list.size() > 13){
            history_list.removeFirst();
        }
        element = create_study_group(sc, is_user_input);
    }

    public void add_command_in_history(String line) {
        history_list.addLast(line);
        if (history_list.size() > 13){
            history_list.removeFirst();
        }
    }
    public void update_function(Scanner sc, boolean is_user_input) {
        element = update_study_group(sc, is_user_input);

    }
    }
