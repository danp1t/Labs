import org.example.Exceptions.CommandNotFound;
import org.example.Exceptions.CtrlDException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.CommandManager;
import java.io.IOException;
import java.util.Scanner;

import static org.example.Managers.CollectionManager.*;

public class Invoker {
    public static void main(String[] argv) throws IOException {

        CommandManager commands = new CommandManager();
        CollectionManager collection = new CollectionManager("/home/danp1t/github/Labs/lab5/src/main/java/org/example/Files/Collection.json");

        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            try {
                Command command = commands.get_commands().get(tokens[0]);
                if (command == null){
                    throw new CommandNotFound();
                }
                if (commands.is_simple_command(tokens[0])) {
                    commands.add_command_in_history(commands.get_commands().get(tokens[0]));
                }
                else if (commands.is_command_with_one_arg(tokens[0])) {
                    commands.add_command_in_history(line);
                }
                else if (commands.is_command_with_element(tokens[0])) {
                    commands.processing_element(commands.get_commands().get(tokens[0]));}

                else if (commands.is_command_with_element_and_one_arg(tokens[0])) {
                    commands.add_command_in_history(line);
                }


                command.execute();



            }
            catch (CommandNotFound e){
                System.out.println(e.send_message());
            }
        }

        try {
            int input = System.in.read();
            if (input == -1){
                throw new CtrlDException();}
        }
        catch (CtrlDException e){
            System.out.println(e.send_message());
        }
    }
}
