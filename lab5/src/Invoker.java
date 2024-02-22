import Interface.Command;
import Commands.HelpCommand;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Invoker {
    public static void main(String[] argv){


        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new HelpCommand());

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.next();
            String[] tokens = line.split(" ");
            Command command = commands.get(tokens[0]);
            command.execute(tokens);
        }
    }
}
