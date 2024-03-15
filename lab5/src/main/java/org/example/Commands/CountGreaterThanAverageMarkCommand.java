package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Interface.Command;

import static org.example.Managers.CollectionManager.get_HashSet;
import static org.example.Managers.CollectionManager.study_groups;
import static org.example.Managers.CommandManager.history_list;
import static org.example.Managers.CommandManager.status_command;

/**
 * Данный класс реализует команду count_greater_than_average_mark
 * Команда count_greater_than_average_mark выводит на экран количество элементов, значение поля averageMark которых больше заданного
 * Данный класс реализует интерфейс Command
 */
public class CountGreaterThanAverageMarkCommand implements Command {
    /**
     * Метод выполнения команды
     * 1. Считываем аргумент averageMark
     * 2. Считаем количество групп, где значение больше заданного
     * 3. Выводим значение на экран
     */
    @Override
    public void execute() {
        System.out.println("Количество элементов, значение поля averageMark которых больше заданного");
        if (study_groups == null) {get_HashSet();}
        String str_average_mark = history_list.getLast().split(" ")[1];
        Double average_mark;
        try{
            average_mark = Double.parseDouble(str_average_mark);
            int counter = 0;
            for (StudyGroup group : study_groups){
                Double group_average_mark = group.getAverageMark();
                if (group_average_mark > average_mark) {
                    counter = counter + 1;
                }
            }
            System.out.println(counter);
        }
        catch (NumberFormatException e){
            System.out.println("Введено некорректное число. Попробуйте еще раз!");
            status_command = -1;
        }

    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит количество элементов, значение поля averageMark которых больше заданного";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String get_name_command() {
        return "count_greater_than_average_mark averageMark";
    }
}
