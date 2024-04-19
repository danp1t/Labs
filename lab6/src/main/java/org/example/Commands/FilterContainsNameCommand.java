package org.example.Commands;

import org.example.Collections.Person;
import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.nio.ByteBuffer;
import java.util.HashSet;

import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

/**
 * Данный класс реализует команду filter_contains_name
 * Команда filter_contains_name выводит элементы, значение поля name которых содержит заданную подстроку
 * Данный класс реализует интерфейс Command
 */
public class FilterContainsNameCommand implements Command {
    /**
     * Метод выполнение команды
     * 1. Считываем подстроку
     * 2. Смотрим поля name у каждого элемента
     * 3. Выводим результат
     */
    @Override
    public void execute(String command_name, String arg, StudyGroup element) {
        ByteBuffer buffer = ByteBuffer.allocate(18000);
        //Считать аргумент

        CollectionManager collectionManager = getCollectionManager();
        String filterName = arg;
        HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        buffer.put(("Поиск элементов, поля name которых содержат подстроку: " + filterName + "\n").getBytes());

        studyGroups.stream()
                .filter(group -> group.getName().contains(filterName) || group.getGroupAdmin().getName().contains(filterName))
                .map(group -> collectionManager.beatifulOutputElementJson(collectionManager.parseStudyGroupToJson(group)))
                .forEach(element123 -> buffer.put((element123 + "\n").getBytes()));

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
        return "выводит элементы, значение поля name которых содержит заданную подстроку";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "filter_contains_name name";
    }
}

