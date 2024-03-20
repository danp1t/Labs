package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotPositiveField;
import org.example.Interface.Command;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;

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
    public void execute(String[] tokens) {
        //Чтение аргумента из tokens
        try {
            //Проверка на то, что у нас один элемент
            if (tokens.length != 2) throw new InputUserException();
            //Проверка на то, что у нас это дробное число
            Double averageMark = Double.parseDouble(tokens[1]);
            //Проверка на то, что у нас это отрицательное число
            if (averageMark <= 0) throw new NotPositiveField();
            //Получить текучую коллекцию StudyGroup
            HashSet<StudyGroup> studyGroups = getStudyGroups();

            if (studyGroups == null) {
                getHashSet();
                studyGroups = getStudyGroups();
            }

            int counter = 0;
            for (StudyGroup group : studyGroups) {
                Double groupAverageMark = group.getAverageMark();
                if (groupAverageMark > averageMark) {
                    counter = counter + 1;
                }
            }
            System.out.println("Количество элементов, значение поля averageMark которых больше заданного: " + counter);


        } catch (InputUserException e) {
            System.out.println("Неверно введены аргументы для команды count_greater_than_average_mark");
        } catch (NotPositiveField e) {
            System.out.println("Значение аргумента не может быть отрицательным числом и нулем");
        } catch (NumberFormatException e) {
            System.out.println("Введено не число с плавающей точкой");
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
    public String getNameCommand() {
        return "count_greater_than_average_mark averageMark";
    }
}
