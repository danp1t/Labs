package Commands;

import Interface.Command;

public class HelpCommand implements Command {
    //Написать программу для исполнения команды help
    public String descr() {return "help - помощь";}
    public void execute(String[] tokens) {
        System.out.println("FFFF");
    }
}
