package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import static org.example.Managers.CollectionManager.print_min_by_semester_enum;
/**
 * Данный класс реализует команду min_by_semester_enum
 * Команда min_by_semester_enum выводит любой объект из коллекции, значение поля semesterEnum которого является минимальным
 * Данный класс реализует интерфейс Command
 */
public class MinSemesterEnum implements Command {
    /**
     * Метод выполнения команды
     */
    @Override
    public void execute() {
        System.out.println("Вывод любого объекта из коллекции, значение поля semesterEnum которого является минимальным");
        System.out.println(print_min_by_semester_enum());
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит любой объект из коллекции, значение поля semesterEnum которого является минимальным";
    }
    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String get_name_command() {
        return "min_by_semester_enum";
    }
}
