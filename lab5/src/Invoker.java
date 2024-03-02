import Exceptions.CommandNotFound;
import Exceptions.CtrlDException;
import Interface.Command;
import Managers.CommandManager;

import java.io.IOException;
import java.util.Scanner;

public class Invoker {
    public static void main(String[] argv) throws IOException {


        CommandManager commands = new CommandManager();


        Scanner sc = new Scanner(System.in);



        while (sc.hasNext()) {
            String line = sc.next();
            String[] tokens = line.split(" ");
            try {
                Command command = commands.get_commands().get(tokens[0]);
                if (command == null){
                    throw new CommandNotFound();
                }
                commands.add_command_in_history(commands.get_commands().get(tokens[0]));
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
