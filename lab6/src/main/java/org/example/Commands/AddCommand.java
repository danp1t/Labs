package org.example.Commands;

import org.example.Collections.Coordinates;
import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.ElementManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;
import static org.example.Managers.ElementManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

/**
 * Данный класс реализует команду add
 * Команда add добавляет учебную группу в коллекцию HashSet
 * Данный класс реализует интерфейс Command
 */
public class AddCommand implements Command {
    /**
     * Метод исполнения команды
     */
    @Override
    public void execute(String name, String arg, StudyGroup element) throws IOException, SQLException {
        //Анализ команды
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StudyGroup group;
        CollectionManager collectionManager = getCollectionManager();
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));
        Connection db = getConnection(url, info);
        //id нужно запросить следующее
        String name_group = element.getName();
        Coordinates coordinates = element.getCoordinates();
        Double x = coordinates.getX();
        Double y = coordinates.getY();

        String insertCommand = "SELECT nextval('coordinates__123_id_seq');";
        Statement st = db.createStatement();
        System.out.println("Все было нормально");
        ResultSet rs = st.executeQuery(insertCommand);
        rs.next();
        int next_id = rs.getInt(1);


        String query = "INSERT INTO coordinates__123 VALUES(?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setInt(1, next_id);
        ps.setDouble(2, x);
        ps.setDouble(3, y);
        ps.execute();


        if (getIsUserInput()) {
            ElementManager elementManager = new ElementManager();
            int id = elementManager.nextID();
            group = elementManager.createStudyGroup(id, element);
        }
        else {
            ElementManager elementManager = new ElementManager();
            group = elementManager.createStudyGroup(getIsUserInput());
        }
        //Запрос на ввод данных для элемента группы

        //Запрос нашей коллекции для добавления нового элемента в группу
        HashSet<StudyGroup> studyGroup = collectionManager.getStudyGroups();
        if (Objects.isNull(group)) {
            buffer.put("Произошла ошибка при выполнении команды add".getBytes());
        }
        else {
            studyGroup.add(group);
            buffer.put("Группа добавлена!".getBytes());
            collectionManager.setStudyGroups(studyGroup);
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
        return "добавляет новый элемент в коллекцию";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "add {element}";
    }
}
