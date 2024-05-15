package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.HashSet;

import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

/**
 * Данный класс реализует команду info
 * Команда info выводит информацию о коллекции(тип коллекции, дата инициализации, количество элементов, является ли множество пустым)
 * Данный класс реализует интерфейс Command
 */
public class InfoCommand implements Command {
    /**
     * Метод исполнение команды
     */
    @Override
    public synchronized void execute(String name, String arg, StudyGroup element, String login) throws SQLException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        ReadWriteLock lock = new ReentrantReadWriteLock();
        buffer.put("Информация о коллекции\n".getBytes());
        lock.readLock().lock();
        buffer.put(printInfoHashSet().getBytes());
        lock.readLock().unlock();

        byteBufferArrayList.add(buffer);
        buffer.clear();
    }


    /**
     * Вспомогательный метод для команды info
     * @return строку для команды info
     */
    private synchronized String printInfoHashSet() throws SQLException, IOException {

        CollectionManager collectionManager = getCollectionManager();
        collectionManager.getHashSet();
        HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        return "Тип: " + studyGroups.getClass() + "\n" +
                "Дата инициализации: " + collectionManager.getCreateDateHashSet() + "\n" +
                "Количество элементов: " + studyGroups.size() + "\n" +
                "Множество пустое: " + studyGroups.isEmpty();
    }
    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "info";
    }


}
