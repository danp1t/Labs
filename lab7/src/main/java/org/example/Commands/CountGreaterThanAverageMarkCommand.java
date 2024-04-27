package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotPositiveField;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.nio.ByteBuffer;
import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

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
    public void execute(String name, String arg, StudyGroup element) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //Чтение аргумента из tokens
        try {
            CollectionManager collectionManager = getCollectionManager();
            //Проверка на то, что у нас один элемент
            //Проверка на то, что у нас это дробное число
            Double averageMark = Double.parseDouble(arg);
            //Проверка на то, что у нас это отрицательное число
            if (averageMark <= 0) throw new NotPositiveField();
            //Получить текучую коллекцию StudyGroup
            HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();

            if (studyGroups == null) {
                collectionManager.getHashSet();
                studyGroups = collectionManager.getStudyGroups();
            }

            long counter = studyGroups.stream()
                    .filter(group -> group.getAverageMark() > averageMark)
                    .count();

            buffer.put(("Количество элементов, значение поля averageMark которых больше заданного: " + counter).getBytes());


        } catch (NotPositiveField e) {
            buffer.put("Значение аргумента не может быть отрицательным числом и нулем".getBytes());
        } catch (NumberFormatException e) {
            buffer.put("Введено не число с плавающей точкой".getBytes());
        }
        byteBufferArrayList.add(buffer);
        buffer.clear();
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