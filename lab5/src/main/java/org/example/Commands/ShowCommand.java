package org.example.Commands;

import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.*;
/**
 * Данный класс реализует команду show
 * Команда show выводит все элементы коллекции на экран в строковом представлении
 * Данный класс реализует интерфейс Command
 */
public class ShowCommand implements Command {
    /**
     * Метод исполнение команды
     */
    @Override
    public void execute(String[] tokens) {
        try {
            if (tokens.length != 1) throw new InputUserException();
            System.out.println("Все элементы коллекции в строковом представлении");
            System.out.println(beatifulOutputJson());
        }
        catch (InputUserException e) {
            System.out.println("Команда info не должна содержать аргументов");
        }
    }
    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "show";
    }





}
