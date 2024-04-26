package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;

import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;


/**
 * Данный класс реализует команду clear
 * Команда clear очищает коллекцию HashSet
 * Данный класс реализует интерфейс Command
 */
public class ClearCommand implements Command {
    /**
     * Метод выполнение команды
     */
    @Override
    public void execute(String name, String arg, StudyGroup element, String login) throws SQLException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
            //Удалить группы только принадлежащие пользователю


        CollectionManager collectionManager = getCollectionManager();
        buffer.put("Очистить коллекцию\n".getBytes());
        collectionManager.getStudyGroups().clear();
        buffer.put("Коллекция очищена".getBytes());

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
        return "очищает коллекцию";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "clear";
    }
}
