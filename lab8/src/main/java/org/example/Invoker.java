//import org.example.Exceptions.NullFieldException;
//import org.example.Interface.Command;
//import org.example.Managers.CollectionManager;
//import org.example.Managers.CommandManager;
//import org.example.Managers.ElementManager;
//import org.example.Managers.StartManager;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//import static org.example.Managers.ElementManager.setIsUserInput;
//import static org.example.Managers.StartManager.setCollectionManager;
//import static org.example.Managers.StartManager.setCommandManager;
//
///**
// * Класс запуска программы
// */
//public class Invoker {
//    public static void main(String[] argv) throws IOException {
//        System.out.println("Программа запущена!");
//        CommandManager commands = new CommandManager();
//        CollectionManager collectionManager = new CollectionManager();
//        setCollectionManager(collectionManager);
//        setCommandManager(commands);
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNext()) {
//            String line = sc.nextLine();
//            String[] tokens = line.split(" ");
//            try {
//                Command command = commands.getCommands().get(tokens[0]);
//                commands.addCommandToHistory(command);
//                command.execute(tokens);
//            }
//            catch (NullPointerException e) {
//                System.out.println("Команда не найдена");
//            }
//
//        }
//    }
//}