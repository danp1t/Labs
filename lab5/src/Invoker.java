import Interface.Command;
import Managers.CommandManager;

import java.util.Scanner;

public class Invoker {
    public static void main(String[] argv){


        CommandManager commands = new CommandManager();


        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.next();
            String[] tokens = line.split(" ");
            Command command = commands.get_commands().get(tokens[0]);
            commands.add_command_in_history(commands.get_commands().get(tokens[0]));
            command.execute();
        }
    }
}
