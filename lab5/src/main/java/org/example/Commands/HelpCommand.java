package org.example.Commands;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;

/**
 * Данный класс реализует команду help
 * Команда help выводит справку по доступным командам
 * Данный класс реализует интерфейс Command
 */
public class HelpCommand implements Command {
    /**
     * Метод исполнения команды
     * 1. Получаем список доступных команд
     * 2. Выводим имя команды и её описание
     */
    @Override
    public void execute() {
        CommandManager commands = new CommandManager();
        for (Command command : commands.get_commands().values()){
            System.out.println(command.get_name_command() + " - " + command.description());
        }
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит справку по доступным командам";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String get_name_command() {
        return "help";
    }
}
