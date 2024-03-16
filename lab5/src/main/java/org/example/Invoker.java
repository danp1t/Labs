import org.example.Exceptions.CommandNotFound;
import org.example.Exceptions.CtrlDException;
import org.example.Exceptions.NullFieldException;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;
import java.io.IOException;
import java.util.Scanner;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;

/**
 * Класс запуска программы
 */
public class Invoker {
    public static void main(String[] argv) throws IOException {
        System.out.println("Программа запущена!");
        CommandManager commands = new CommandManager();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            try {
                Command command = commands.getCommands().get(tokens[0]);
                if (command == null){
                    throw new CommandNotFound();
                }
                if (commands.isSimpleCommand(tokens[0])) {
                    if (tokens.length > 1) {throw new CommandNotFound();}
                    commands.addCommandInHistory(commands.getCommands().get(tokens[0]));
                    command.execute();
                }
                else if (commands.isCommandWithOneArg(tokens[0])) {
                    if (tokens.length > 2) {throw new CommandNotFound();}
                    commands.addCommandInHistory(line);
                    command.execute();
                }
                else if (commands.isCommandWithElement(tokens[0])) {
                    if (tokens.length > 1) {throw new CommandNotFound();}
                    commands.processingElement(commands.getCommands().get(tokens[0]), sc, true);
                    command.execute();}

                else if (commands.isCommandWithElementAndOneArg(tokens[0])) {
                    if (tokens.length > 2) {throw new CommandNotFound();}
                    commands.addCommandInHistory(line);
                    getGroupElement();
                    if (gettingGroupElement() == null) {throw new NullFieldException();}
                    commands.updateFunction(sc, true);
                    command.execute();

                }
            }
            catch (CommandNotFound e){
                System.out.println(e.sendMessage());
            }
            catch (NullFieldException e) {
                System.out.print("");
            }
        }

        try {
            int input = System.in.read();
            if (input == -1){
                throw new CtrlDException();
            }
        }
        catch (CtrlDException e){
            System.out.println(e.sendMessage());
        }
    }
}
