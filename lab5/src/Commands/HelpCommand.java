package Commands;
import Interface.Command;

public class HelpCommand implements Command {
    //Написать программу для исполнения команды help
    @Override
    public void execute() {
        System.out.println("Спасите...");
    }
}
