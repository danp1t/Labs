package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.nio.ByteBuffer;

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
    public void execute(String name, String arg, StudyGroup element) {
        ByteBuffer buffer = ByteBuffer.allocate(64000);

        CollectionManager collectionManager = getCollectionManager();
        buffer.put("Все элементы коллекции в строковом представлении\n".getBytes());
        buffer.put((collectionManager.beatifulOutputJson()).getBytes());
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
