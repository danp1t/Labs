import org.example.Exceptions.NullFieldException;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;
import java.io.IOException;
import java.util.Scanner;

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
                commands.addCommandToHistory(command);
                command.execute(tokens);
            }
            catch (NullPointerException e) {
                System.out.println("Команда не найдена");
            }

        }
    }
}