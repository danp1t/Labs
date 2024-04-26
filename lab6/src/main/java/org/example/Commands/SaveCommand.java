package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;

import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

/**
 * Данный класс реализует команду save
 * Команда save сохраняет коллекцию в файл
 * Данный класс реализует интерфейс Command
 */
public class SaveCommand implements Command {
    /**
     * Метод исполнение команды
     */
    @Override
    public void execute(String name, String arg, StudyGroup element, String login) throws SQLException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Сохранить коллекцию в файл".getBytes());
        //saveHashSetToFile();

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
        return "сохраняет коллекцию в файл";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "save";
    }
}
