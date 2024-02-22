import Commands.*;
import Interface.Command;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Invoker {
    public static void main(String[] argv){


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


        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.next();
            String[] tokens = line.split(" ");
            Command command = commands.get(tokens[0]);
            command.execute();
        }
    }
}
