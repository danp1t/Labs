package org.example.Commands;

import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.printInfoHashSet;
/**
 * Данный класс реализует команду info
 * Команда info выводит информацию о коллекции(тип коллекции, дата инициализации, количество элементов, является ли множество пустым)
 * Данный класс реализует интерфейс Command
 */
public class InfoCommand implements Command {
    /**
     * Метод исполнение команды
     */
    @Override
    public void execute() {
        System.out.println("Информация о коллекции");
        System.out.println(printInfoHashSet());
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "info";
    }


}
