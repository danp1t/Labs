package org.example.Commands;

import org.example.Collections.*;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.ElementManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;
import static org.example.Managers.ElementManager.*;
import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

/**
 * Данный класс реализует команду update
 * Команда update обновляет значение элемента коллекции по id
 * Данный класс реализует интерфейс Command
 */
public class UpdateCommand implements Command {
    /**
     * Метод исполнение команды
     * 1. Считываем id
     * 2. Ищем по id элемент коллекции
     * 3. Обновляем коллекцию
     */
    @Override
    public void execute(String name, String arg, StudyGroup element123, String login) throws SQLException, IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));

        Connection db = getConnection(url, info);
        db.setAutoCommit(false);


        String query = "SELECT id FROM person__123 where person__123.login = ?";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int loginID = rs.getInt(1);
        try {
            CollectionManager collectionManager = getCollectionManager();
            HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
            int number = Integer.parseInt(arg);
            String query1234 = "SELECT * FROM studygroup__123 WHERE (studygroup__123.who_created_id = ? AND studygroup__123.id = ?);";
            PreparedStatement ps1 = db.prepareStatement(query1234);
            ps1.setInt(1, loginID);
            ps1.setInt(2, number);
            boolean flag = ps1.executeQuery().next();
            if (!flag) throw new NotCollectionIDFound();

            String query123 = "DELETE FROM studygroup__123 WHERE (studygroup__123.who_created_id = ? AND studygroup__123.id = ?);";
            ps = db.prepareStatement(query123);
            ps.setInt(1, loginID);
            ps.setInt(2, number);

            HashSet<StudyGroup> newStudyGroups = new HashSet<>();
            for (StudyGroup group : studyGroups) {
                if (number != group.getID()) {
                    newStudyGroups.add(group);
                }
            }
            if (newStudyGroups.size() == studyGroups.size()) {
                throw new NotCollectionIDFound();
            }
            collectionManager.setStudyGroups(newStudyGroups);
            ElementManager elementManager = new ElementManager();
            HashSet<StudyGroup> studyGroups1 = collectionManager.getStudyGroups();
            StudyGroup element;
            //Прочитать элемент
            if (getIsUserInput()) {
                element = elementManager.createStudyGroup(number, element123);
            }
            else {
                element = elementManager.createStudyGroup(getIsUserInput());
            }



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

            String query5 = "SELECT id_coordinates FROM studygroup__123 where studygroup__123.id = ?;";
            ps = db.prepareStatement(query5);
            ps.setInt(1, number);

            rs = ps.executeQuery();
            rs.next();
            int idCoordinates = rs.getInt(1);

            String updateCoordinateX = "UPDATE coordinates__123 SET x = ? where coordinates__123.id = ?";
            ps = db.prepareStatement(updateCoordinateX);
            ps.setDouble(1, x);
            ps.setInt(2, idCoordinates);
            ps.execute();

            String updateCoordinateY = "UPDATE coordinates__123 SET y = ? where coordinates__123.id = ?";
            ps = db.prepareStatement(updateCoordinateY);
            ps.setDouble(1, y);
            ps.setInt(2, idCoordinates);
            ps.execute();

            String updateName = "UPDATE studygroup__123 SET name_group = ? where studygroup__123.id = ?;";
            ps = db.prepareStatement(updateName);
            ps.setString(1, name_group);
            ps.setInt(2, number);
            ps.execute();

            String updateStudentsCount = "UPDATE studygroup__123 SET students_count = ? where studygroup__123.id = ?;";
            ps = db.prepareStatement(updateStudentsCount);
            ps.setInt(1, studentsCount);
            ps.setInt(2, number);
            ps.execute();

            String updateAverageMark = "UPDATE studygroup__123 SET average_mark = ? where studygroup__123.id = ?;";
            ps = db.prepareStatement(updateAverageMark);
            ps.setDouble(1, averageMark);
            ps.setInt(2, number);
            ps.execute();

            String updateFormEducation = "UPDATE studygroup__123 SET form_education = ? where studygroup__123.id = ?;";
            ps = db.prepareStatement(updateFormEducation);
            ps.setObject(1, formOfEducation, Types.OTHER);
            ps.setInt(2, number);
            ps.execute();

            String updateSemester = "UPDATE studygroup__123 SET semester = ? where studygroup__123.id = ?;";
            ps = db.prepareStatement(updateSemester);
            ps.setObject(1, semesterEnum, Types.OTHER);
            ps.setInt(2, number);
            ps.execute();

            String query125 = "SELECT group_admin_id FROM studygroup__123 where studygroup__123.id = ?;";
            ps = db.prepareStatement(query125);
            ps.setInt(1, number);
            rs = ps.executeQuery();
            rs.next();
            int GroupAdminId = rs.getInt(1);


            String updateAdminName = "UPDATE users__123 SET name = ? where users__123.id = ?";
            ps = db.prepareStatement(updateAdminName);
            ps.setString(1, person_name);
            ps.setInt(2, GroupAdminId);
            ps.execute();

            String updateBirthday = "UPDATE users__123 SET birthday = ? where users__123.id = ?";
            ps = db.prepareStatement(updateBirthday);
            ps.setDate(1, Date.valueOf(birthday));
            ps.setInt(2, GroupAdminId);
            ps.execute();

            String updateHairColor = "UPDATE users__123 SET hair_color = ? where users__123.id = ?";
            ps = db.prepareStatement(updateHairColor);
            ps.setObject(1, hairColor, Types.OTHER);
            ps.setInt(2, GroupAdminId);
            ps.execute();

            String updateEyeColor = "UPDATE users__123 SET eye_color = ? where users__123.id = ?";
            ps = db.prepareStatement(updateEyeColor);
            ps.setObject(1, eyeColor, Types.OTHER);
            ps.setInt(2, GroupAdminId);
            ps.execute();

            db.commit();
            ps.close();
            db.close();

            if (element != null) {studyGroups1.add(element);}

            if (studyGroups.size() != studyGroups1.size()) {
                collectionManager.setStudyGroups(studyGroups);
            }
            else {collectionManager.setStudyGroups(studyGroups1);}
            collectionManager.getHashSet();
            buffer.put("Коллекция обновлена\n".getBytes());
        }
        catch (NumberFormatException e){
            buffer.put("Аргумент должен быть положительным целым числом большим 0".getBytes());
        }
        catch (NotCollectionIDFound e){
            buffer.put("ID коллекции не найден".getBytes());
        }
        catch (ArrayIndexOutOfBoundsException e){
            buffer.put("Введите ID элемента\n".getBytes());
        }
        catch (NullPointerException e) {
            buffer.put("Ошибочка вышла".getBytes());
        }
        CollectionManager collectionManager = new CollectionManager();
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
        return "обновляет значение элемента коллекции, id которого равен заданному";
    }
    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "update id {element}";
    }
}

