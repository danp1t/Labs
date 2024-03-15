import org.example.Exceptions.CommandNotFound;
import org.example.Exceptions.CtrlDException;
import org.example.Exceptions.NullFieldException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.CommandManager;
import java.io.IOException;
import java.util.Scanner;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.element;
import static org.example.Managers.CommandManager.group_element;

public class Invoker {
    public static void main(String[] argv) throws IOException {

        CommandManager commands = new CommandManager();
        CollectionManager collection = new CollectionManager();

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
                    if (tokens.length > 1) {throw new CommandNotFound();}
                    commands.add_command_in_history(commands.get_commands().get(tokens[0]));
                    command.execute();
                }
                else if (commands.is_command_with_one_arg(tokens[0])) {
                    if (tokens.length > 2) {throw new CommandNotFound();}
                    commands.add_command_in_history(line);
                    command.execute();
                }
                else if (commands.is_command_with_element(tokens[0])) {
                    if (tokens.length > 1) {throw new CommandNotFound();}
                    commands.processing_element(commands.get_commands().get(tokens[0]), sc, true);
                    command.execute();}

                else if (commands.is_command_with_element_and_one_arg(tokens[0])) {
                    if (tokens.length > 2) {throw new CommandNotFound();}
                    commands.add_command_in_history(line);
                    get_group_element();
                    if (group_element == null) {throw new NullFieldException();}
                    commands.update_function(sc, true);
                    command.execute();

                }






            }
            catch (CommandNotFound e){
                System.out.println(e.send_message());
            }
            catch (NullFieldException e) {
                System.out.print("");
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
