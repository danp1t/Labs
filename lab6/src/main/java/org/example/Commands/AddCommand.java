package org.example.Commands;

import org.example.Collections.*;
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
    public synchronized void execute(String name, String arg, StudyGroup element, String login) throws IOException, SQLException {
        //Анализ команды
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        CollectionManager collectionManager = getCollectionManager();
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));

        Connection db = getConnection(url, info);
        db.setAutoCommit(false);
        String getNextIDStudyGroup = "SELECT nextval('studygroup__123_id_seq');";
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery(getNextIDStudyGroup);
        rs.next();
        int StudyGroupNextId = rs.getInt(1);

        if (getIsUserInput()) {
            ElementManager elementManager = new ElementManager();
            element = elementManager.createStudyGroup(StudyGroupNextId, element);
        }
        else {
            ElementManager elementManager = new ElementManager();
            element = elementManager.createStudyGroup(getIsUserInput());
        }

        //id нужно запросить следующее

        String query = "SELECT id FROM person__123 where person__123.login = ?";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, login);
        rs = ps.executeQuery();
        rs.next();
        int loginID = rs.getInt(1);


        String name_group = element.getName();
        Coordinates coordinates = element.getCoordinates();
        Double x = coordinates.getX();
        Double y = coordinates.getY();
        java.time.LocalDateTime creationDate = element.getCreationDate();
        Integer studentsCount = element.getStudentsCount();
        Double averageMark = element.getAverageMark();
        FormOfEducation formOfEducation = element.getFormOfEducation();
        Semester semesterEnum = element.getSemesterEnum();


        Person person = element.getGroupAdmin();
        String person_name = person.getName();
        java.time.LocalDate birthday = person.getBirthday();
        HairColor hairColor = person.getHairColor();
        EyeColor eyeColor = person.getEyeColor();

        String insertCommand = "SELECT nextval('coordinates__123_id_seq');";
        st = db.createStatement();
        rs = st.executeQuery(insertCommand);
        rs.next();
        int coordinationNextId = rs.getInt(1);


        String query123 = "INSERT INTO coordinates__123 VALUES(?, ?, ?);";
        ps = db.prepareStatement(query123);
        ps.setInt(1, coordinationNextId);
        ps.setDouble(2, x);
        ps.setDouble(3, y);
        ps.execute();

        String getNextIDPerson = "SELECT nextval('users__123_id_seq');";
        st = db.createStatement();
        rs = st.executeQuery(getNextIDPerson);
        rs.next();
        int personNextId = rs.getInt(1);


        String query1 = "INSERT INTO users__123 VALUES(?, ?, ?, ?, ?);";
        ps = db.prepareStatement(query1);
        ps.setInt(1, personNextId);
        ps.setString(2, person_name);
        ps.setDate(3, Date.valueOf(birthday));
        ps.setObject(4, hairColor, Types.OTHER);
        ps.setObject(5, eyeColor, Types.OTHER);
        ps.execute();



        String query2 = "INSERT INTO studygroup__123 VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        ps = db.prepareStatement(query2);
        ps.setInt(1, StudyGroupNextId);
        ps.setString(2, name_group);
        ps.setInt(3, coordinationNextId);
        ps.setTimestamp(4, Timestamp.valueOf(creationDate));
        ps.setInt(5, studentsCount);
        ps.setDouble(6, averageMark);
        ps.setObject(7, formOfEducation, Types.OTHER);
        ps.setObject(8, semesterEnum, Types.OTHER);
        ps.setInt(9, personNextId);
        ps.setInt(10, loginID);
        ps.execute();

        db.commit();
        ps.close();
        db.close();


        //Запрос нашей коллекции для добавления нового элемента в группу
        HashSet<StudyGroup> studyGroup = collectionManager.getStudyGroups();
        if (Objects.isNull(element)) {
            buffer.put("Произошла ошибка при выполнении команды add".getBytes());
        }
        else {
            studyGroup.add(element);
            buffer.put("Группа добавлена!".getBytes());
            collectionManager.setStudyGroups(studyGroup);
        }
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
