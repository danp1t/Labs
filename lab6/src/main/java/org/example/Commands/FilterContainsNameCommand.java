package org.example.Commands;

import org.example.Collections.Person;
import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

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
    public void execute(String command_name, String arg, StudyGroup element, String login) throws SQLException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //Считать аргумент

        CollectionManager collectionManager = getCollectionManager();
        String filterName = arg;
        HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        buffer.put(("Поиск элементов, поля name которых содержат подстроку: " + filterName + "\n").getBytes());
        byteBufferArrayList.add(buffer);
        buffer.clear();

        String message = studyGroups.stream()
                .filter(group -> group.getName().contains(filterName) || group.getGroupAdmin().getName().contains(filterName))
                .sorted(Comparator.comparing(StudyGroup::getName))
                .map(group -> collectionManager.beatifulOutputElementJson(collectionManager.parseStudyGroupToJson(group)))
                .collect(Collectors.joining("\n"));

        int messageLength = message.getBytes().length;
        int bufferSize = 1024;
        int numBuffers = (int) Math.ceil((double) messageLength / bufferSize);

        for (int i = 0; i < numBuffers; i++) {
            int start = i * bufferSize;
            int end = Math.min(start + bufferSize, messageLength);
            String subMessage = message.substring(start, end);

            ByteBuffer subBuffer = ByteBuffer.allocate(1024);
            subBuffer.put(subMessage.getBytes());
            byteBufferArrayList.add(subBuffer);
        }


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

