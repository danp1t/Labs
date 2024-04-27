package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;
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
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));

        Connection db = getConnection(url, info);


        String query = "SELECT id FROM person__123 where person__123.login = ?";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int loginID = rs.getInt(1);
        query = "DELETE FROM studygroup__123 WHERE studygroup__123.who_created_id = ?;";
        ps = db.prepareStatement(query);
        ps.setInt(1, loginID);
        ps.execute();

        collectionManager.getHashSet();
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
