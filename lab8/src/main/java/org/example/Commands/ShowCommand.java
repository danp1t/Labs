package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

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
    public synchronized void execute(String name, String arg, StudyGroup element, String login) throws SQLException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        CollectionManager collectionManager = getCollectionManager();
        buffer.put("Все элементы коллекции в строковом представлении\n".getBytes());
        byteBufferArrayList.add(buffer);
        buffer.clear();
        String message = collectionManager.beatifulOutputJson();
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
