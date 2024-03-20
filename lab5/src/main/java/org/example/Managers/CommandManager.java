package org.example.Managers;

import org.example.Collections.StudyGroup;
import org.example.Commands.*;
import org.example.Interface.Command;

import java.util.*;

import static org.example.Managers.CollectionManager.createStudyGroup;
import static org.example.Managers.CollectionManager.updateStudyGroup;

/**
 * Класс для работы с командами
 */
public class CommandManager {

    //Конструктор
    /**
     * Поле для хранения статуса команд
     * Если поле равно 0, то все нормально
     * Если поле равно -1, то произошло исключение и нужно прервать выполнения команды
     */
    private static int statusCommand = 0;
    /**
     * Поля для хранения команд
     */
    private static Map<String, Command> commands;
    /**
     * Поле для хранения element для команды update
     */
    private static StudyGroup groupElement;
    /**
     * Поле для хранения element
     */
    private static StudyGroup element;
    /**
     * Поле истории вызова команд
     */
    public static ArrayDeque<String> historyList = new ArrayDeque<>(13);

    /**
     * Конструктор класса
     */
    public CommandManager(){
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("add", new AddCommand());
        commands.put("update", new UpdateCommand());
        commands.put("remove_by_id", new RemoveCommand());
        commands.put("clear", new ClearCommand());
        commands.put("save", new SaveCommand());
        commands.put("execute_script", new ExecuteCommand());
        commands.put("exit", new ExitCommand());
        commands.put("add_if_max", new AddIfMaxCommand());
        commands.put("add_if_min", new AddIfMinCommand());
        commands.put("history", new HistoryCommand());
        commands.put("min_by_semester_enum", new MinSemesterEnum());
        commands.put("count_greater_than_average_mark", new CountGreaterThanAverageMarkCommand());
        commands.put("filter_contains_name", new FilterContainsNameCommand());
        commands.put("hello", new HelloWorldCommand());
        this.commands = commands;
    }

    /**
     * Getter для поля element
     * @return значение поля element
     */
    public static StudyGroup getElement(){
        return element;
    }
    /**
     * Setter для поля groupElement
     * @param group значение для groupElement
     */
    public static void setElement(StudyGroup group){
        element = group;
    }
    /**
     * Getter для поля groupElement
     * @return значение поля groupElement
     */
    public static StudyGroup gettingGroupElement(){
        return groupElement;
    }

    /**
     * Setter для поля groupElement
     * @param group значение для groupElement
     */
    public static void settingGroupElement(StudyGroup group){
        groupElement = group;
    }
    /**
     * Getter для поля statusCommand
     * @return значение поля statusCommand
     */
    public static int getStatusCommand() {
        return statusCommand;
    }

    /**
     * Setter для поля statusCommand
     * @param status устанавливаемый статус команды
     */
    public static void setStatusCommand(int status) {
        statusCommand = status;
    }

    /**
     * Getter для поля commands
     * @return список команд
     */
    public Map<String, Command> getCommands(){
        return commands;
    }

    /**
     * Вспомогательный метод для определения вида команд
     * @param command команда
     * @return это команда без аргументов?
     */
    public boolean isSimpleCommand(String command){
        String[] simpleCommand = {"hello", "help", "info", "show", "clear", "save", "exit", "history", "min_by_semester_enum"};
        boolean contains = Arrays.asList(simpleCommand).contains(command);
        if (contains) {return true;}
        else {return false;}
    }

    /**
     * Вспомогательный метод для определения вида команд
     * @param command команда
     * @return это команда с одним аргументом?
     */
    public boolean isCommandWithOneArg(String command){
        String[] commandWithOneArg = {"remove_by_id", "execute_script", "count_greater_than_average_mark", "filter_contains_name"};
        boolean contains = Arrays.asList(commandWithOneArg).contains(command);
        if (contains) {return true;}
        else {return false;}
    }

    /**
     * Вспомогательный метод для определения вида команд
     * @param command команда
     * @return это команда с element?
     */
    public boolean isCommandWithElement(String command){
        String[] commandWithElement = {"add", "add_if_max", "add_if_min"};
        boolean contains = Arrays.asList(commandWithElement).contains(command);
        if (contains) {return true;}
        else {return false;}
    }

    /**
     * Вспомогательный метод для определения вида команд
     * @param command команда
     * @return это команда с element и одним аргументом?
     */
    public boolean isCommandWithElementAndOneArg(String command){
        List<String> commandWithElement = List.of("update");
        boolean contains = commandWithElement.contains(command);
        return contains;

    }

    /**
     * Добавить команду в исторический список
     * @param command команда
     */
    public void addCommandToHistory(Command command) {
        String name = command.getNameCommand();
        historyList.addLast(name);
        if (historyList.size() > 13){
            historyList.removeFirst();
        }
    };

    /**
     * Добавить команду в исторический список и создать element
     * @param command команда
     * @param sc сканер
     * @param isUserInput пользовательский ввод?
     */
    public void processingElement(Command command, Scanner sc, boolean isUserInput){
        String name = command.getNameCommand();
        historyList.addLast(name);
        if (historyList.size() > 13){
            historyList.removeFirst();
        }
        element = createStudyGroup(sc, isUserInput);
    }

    /**
     * Добавить команду в исторический список
     * @param line команда с параметром
     */
    public void addCommandToHistory(String line) {
        historyList.addLast(line);
        if (historyList.size() > 13){
            historyList.removeFirst();
        }
    }

    /**
     * Получение element для команды update
     * @param sc сканер
     * @param isUserInput пользовательский ли ввод?
     */
    public void updateFunction(Scanner sc, boolean isUserInput) {
        element = updateStudyGroup(sc, isUserInput);

    }
}
