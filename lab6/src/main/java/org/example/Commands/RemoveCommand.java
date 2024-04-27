package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Exceptions.NotPositiveField;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;
import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

/**
 * Данный класс реализует команду remove_by_id
 * Команда remove_by_id удаляет элемент из коллекции по его id
 * Данный класс реализует интерфейс Command
 */
public class RemoveCommand implements Command {
    /**
     * Метод исполнение команды
     * 1. Считываем id объекта
     * 2. Ищем объект по его id
     * 3. Удаляем объект
     */
    @Override
    public synchronized void execute(String name, String arg, StudyGroup element, String login) throws SQLException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ReadWriteLock lock = new ReentrantReadWriteLock();


        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));

        Connection db = getConnection(url, info);

        lock.readLock().lock();
        String query = "SELECT id FROM person__123 where person__123.login = ?";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int loginID = rs.getInt(1);
        lock.readLock().unlock();

        //Получить id
        try {
            CollectionManager collectionManager = getCollectionManager();
            HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
            lock.readLock().lock();
            int id = Integer.parseInt(arg);
            String query1234 = "SELECT * FROM studygroup__123 WHERE (studygroup__123.who_created_id = ? AND studygroup__123.id = ?);";
            PreparedStatement ps1 = db.prepareStatement(query1234);
            ps1.setInt(1, loginID);
            ps1.setInt(2, id);
            boolean flag = ps1.executeQuery().next();
            lock.readLock().unlock();
            if (!flag) throw new NotCollectionIDFound();

            lock.writeLock().lock();
            String query123 = "DELETE FROM studygroup__123 WHERE (studygroup__123.who_created_id = ? AND studygroup__123.id = ?);";
            ps = db.prepareStatement(query123);
            ps.setInt(1, loginID);
            ps.setInt(2, id);
            lock.writeLock().unlock();


            //Проверить, что он положительный и является целочисленным числом
            if (id <= 0) throw new NotPositiveField();

            //Получение текущей коллекции
            buffer.put(("Удалить элемент из коллекции по id: " + id + "\n").getBytes());
            HashSet<StudyGroup> newStudyGroups = new HashSet<>();
            lock.writeLock().lock();
            for (StudyGroup group : studyGroups) {
                if (id == group.getID()) {
                    buffer.put(("Коллекция с id: " + id + " удалена.").getBytes());
                } else {
                    newStudyGroups.add(group);
                    }
                }
            if (newStudyGroups.size() == studyGroups.size()) throw new NotCollectionIDFound();
            collectionManager.setStudyGroups(newStudyGroups);
            collectionManager.getHashSet();
            lock.writeLock().unlock();


        }
        catch (NotPositiveField | NumberFormatException e){
            buffer.put("Аргументом этой строки должно быть положительное целое число, которое больше 0".getBytes());
        }
        catch (NotCollectionIDFound e) {
            buffer.put(e.sendMessage().getBytes());
        }
        CollectionManager collectionManager = getCollectionManager();
        collectionManager.getHashSet();
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
        return "удаляет элемент из коллекции по его id";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "remove_by_id id";
    }
}
