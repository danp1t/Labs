package org.example.Managers;

import org.example.Commands.*;
import org.example.Interface.Command;
import java.util.*;

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
     * Поле истории вызова команд
     */
    private static ArrayDeque<String> historyList = new ArrayDeque<>(13);

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
        this.commands = commands;
    }

    public static ArrayDeque<String> getHistoryList(){
        return historyList;
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
}
