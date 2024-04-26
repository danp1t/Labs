package org.example.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Collections.*;
import org.example.Exceptions.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.sql.DriverManager.getConnection;
import static org.example.Managers.CommandManager.*;

/**
 * Класс для работы с коллекцией HashSet и другими данными
 */
public class CollectionManager {
    /**
     * Путь до json_file, где хранится сохраненная коллекция HashSet
     */
    private String fileName;
    /**
     * Дата создания коллекции
     */
    private String createDateHashSet;
    /**
     * JSONArray, в котором хранится коллекция HashSet
     */
    private JSONArray jsonFile;
    /**
     * Список учебных групп, которые хранятся в коллекции HashSet
     */
    private HashSet<StudyGroup> studyGroups;

    /**
     * Конструктор класса
     */
    public CollectionManager(){
        String pathJson = System.getenv("LAB6") + "Collection.json";
        this.fileName = pathJson;
    }

    public String getCreateDateHashSet(){
        return this.createDateHashSet;
    }

    /**
     * getter для поля studyGroups
     * @return значение поля studyGroups
     */
    public HashSet<StudyGroup> getStudyGroups() throws SQLException, IOException {
        if (Objects.isNull(this.studyGroups)) {
            getHashSet();
        };
        return this.studyGroups;
    }

    /**
     * setter для поля studyGroups
     * @param group
     */
    public void setStudyGroups(HashSet<StudyGroup> group){
        this.studyGroups = group;
    }

    public void getHashSet() throws IOException, SQLException {
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));

        Connection db = getConnection(url, info);
        String query = "SELECT * FROM studygroup__123";
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery(query);
        HashSet<StudyGroup> studyGroups = new HashSet<StudyGroup>();
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name_group");
            int idCoordinates = rs.getInt("id_coordinates");
            Timestamp creationDate = rs.getTimestamp("creation_date");
            int studentsCount = rs.getInt("students_count");
            double averageMark = rs.getDouble("average_mark");
            Object formOfEducation = rs.getObject("form_education");
            Object semesterEnum = rs.getObject("semester");
            int groupAdminId = rs.getInt("group_admin_id");

            String query123 = "SELECT * FROM coordinates__123 where coordinates__123.id = ?";
            PreparedStatement ps = db.prepareStatement(query123);
            ps.setInt(1, idCoordinates);
            rs = ps.executeQuery();
            rs.next();
            double x = rs.getDouble("x");
            double y = rs.getDouble("y");
            Coordinates newCoordinates = new Coordinates(x, y);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime newCreationDate = creationDate.toLocalDateTime();
            FormOfEducation newFormOfEducation = FormOfEducation.valueOf((String) formOfEducation);
            Semester semester = Semester.valueOf((String) semesterEnum);

            String query12 = "SELECT * FROM users__123 where users__123.id = ?";
            ps = db.prepareStatement(query12);
            ps.setInt(1, groupAdminId);
            rs = ps.executeQuery();
            rs.next();

            String personName = rs.getString("name");
            LocalDate newBirthday = rs.getDate("birthday").toLocalDate();
            Object eyeColor = rs.getObject("eye_color");
            Object hairColor = rs.getObject( "hair_color");
            EyeColor newEyeColor = EyeColor.valueOf(eyeColor.toString());
            HairColor newHairColor = HairColor.valueOf(hairColor.toString());

            Person newGroupAdmim = new Person(personName, newBirthday, newEyeColor, newHairColor);
            StudyGroup group = new StudyGroup(id, name, newCoordinates, newCreationDate, studentsCount, averageMark, newFormOfEducation, semester, newGroupAdmim);
            studyGroups.add(group);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        this.studyGroups = studyGroups;
        createDateHashSet = formattedDateTime;
    }

    /**
     * В данном методе мы читаем коллекцию из json файла
     * @return JSONArray
     */
    public JSONArray readJsonFile(){
        try {
            return (JSONArray) new JSONParser().parse(new FileReader(System.getenv("LAB6") + "Collection.json"));
        } catch (IOException | ParseException e) {
            System.out.println("Ошибка при чтении коллекции из файла");
        }
        return null;
    }

    /**
     * В этом методе мы преобразовываем коллекцию HashSet в JSONArray для последующего сохранения в файл
     * @return JSONArray
     */
    public JSONArray parseHashsetToJson() throws SQLException, IOException {
        Set<StudyGroup> studyGroups = printHashSet();
        JSONArray groupArray = new JSONArray();
        for (StudyGroup sg : studyGroups){
            groupArray.add(parseStudyGroupToJson(sg));
        }
        return groupArray;
    }
    /**
     * В этом методе мы учебную группу преобразуем в JSONObject
     * @param group учебная группа
     * @return JSONObject учебной группы
     */
    public JSONObject parseStudyGroupToJson(StudyGroup group){

        JSONObject groupObject = new JSONObject();
        //Разборка коллекции на составляющие
        int id = group.getID();
        String name = group.getName();
        Coordinates coordinates = group.getCoordinates();
        double x = coordinates.getX();
        Double y = coordinates.getY();
        String creationDate = group.getCreationDate().toString();
        Integer studentsCount = group.getStudentsCount();
        Double averageMark = group.getAverageMark();
        FormOfEducation formOfEducation = group.getFormOfEducation();
        Semester semesterEnum = group.getSemesterEnum();
        Person groupAdmin = group.getGroupAdmin();
        String namePerson = groupAdmin.getName();
        String birthday = groupAdmin.getBirthday().toString();
        EyeColor eyeColor = groupAdmin.getEyeColor();
        HairColor hairColor = groupAdmin.getHairColor();

        groupObject.put("id", id);
        groupObject.put("name", name);

        //Создание объекта для координат
        JSONObject coordinatesJson = new JSONObject();
        coordinatesJson.put("x", x);
        coordinatesJson.put("y", y);
        groupObject.put("coordinates", coordinatesJson);

        groupObject.put("creationDate", creationDate);
        groupObject.put("studentsCount", studentsCount);
        groupObject.put("averageMark", averageMark);
        groupObject.put("formOfEducation", formOfEducation);
        groupObject.put("semesterEnum", semesterEnum);

        //Создание объекта для старосты
        JSONObject admin = new JSONObject();
        admin.put("name", namePerson);
        admin.put("birthday", birthday);
        admin.put("eyeColor", eyeColor);
        admin.put("hairColor", hairColor);
        groupObject.put("groupAdmin", admin);

        return groupObject;
    }
    /**
     * Данная функция возвращает HashSet из JSONObject
     */
//    public void getHashSet(){
//        HashSet<StudyGroup> studyGroups = new HashSet<StudyGroup>();
//        if (jsonFile == null){
//            jsonFile = readJsonFile();
//        }
//        for (int i = 0; i < jsonFile.size(); i++){
//            JSONObject object = (JSONObject) jsonFile.get(i);
//
//            Long id = (Long) object.get("id");
//            String name = (String) object.get("name");
//            JSONObject coordinates = (JSONObject) object.get("coordinates");
//            String creationDate = (String) object.get("creationDate");
//            Long studentsCount = (Long) object.get("studentsCount");
//            Double averageMark = (Double) object.get("averageMark");
//            String formOfEducation = (String) object.get("formOfEducation");
//            String semesterEnum = (String) object.get("semesterEnum");
//            JSONObject groupAdmin = (JSONObject) object.get("groupAdmin");
//
//            //Преобразовать объекты в нужный тип для конструктора
//            int newId = id.intValue();
//
//            //Преобразовать coordinates
//            double x = (double) coordinates.get("x");
//            Double y = (Double) coordinates.get("y");
//            Coordinates newCoordinates = new Coordinates(x, y);
//
//            //Преобразовать creationDate в нужный тип
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//            LocalDateTime newCreationDate = LocalDateTime.parse(creationDate, formatter);
//
//            //Преобразовать integerCount
//            Integer newStudentsCount = studentsCount.intValue();
//
//            //Преобразовать formOfEducation и semesterEnum
//            FormOfEducation newFormOfEducation = FormOfEducation.valueOf(formOfEducation);
//            Semester semester = Semester.valueOf(semesterEnum);
//
//            //Преобразовать groupAdmin
//            //Распарсинг groupAdmin
//            String personName = (String) groupAdmin.get("name");
//            String birthday = (String) groupAdmin.get("birthday");
//            String eyeColor = (String) groupAdmin.get("eyeColor");
//            String hairColor = (String) groupAdmin.get("hairColor");
//
//            //Преобразование типов
//            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate newBirthday = LocalDate.parse(birthday, formatter);
//            EyeColor newEyeColor = EyeColor.valueOf(eyeColor);
//            HairColor newHairColor = HairColor.valueOf(hairColor);
//
//            //Создание админа из конструктора
//            Person newGroupAdmim = new Person(personName, newBirthday, newEyeColor, newHairColor);
//
//
//            //С помощью конструктора для StudyGroup создать новый объект
//            StudyGroup group = new StudyGroup(newId, name, newCoordinates, newCreationDate, newStudentsCount, averageMark, newFormOfEducation, semester, newGroupAdmim);
//            //Добавить этот объект в коллекцию
//            studyGroups.add(group);
//
//        }
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
//        String formattedDateTime = now.format(formatter);
//        this.studyGroups = studyGroups;
//        createDateHashSet = formattedDateTime;
//    }

    /**
     * Данный метод возвращает отсортированное множество
     * @return отсортированное множество
     */
    public Set printHashSet() throws SQLException, IOException {
        getStudyGroups();
        Set<StudyGroup> sortedGroups = new TreeSet<>(this.studyGroups);
        return sortedGroups;
    }
    /**
     * Метод возвращает красивый вывод JSONArray
     * @return красивый вывод JSONArray
     */
    public String beatifulOutputJson() throws SQLException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(parseHashsetToJson());
        return jsonString;
    }

    /**
     * Метод возвращает красивый вывод JSONObject
     * @param object JSONObject
     * @return красивый вывод jsonObject
     */
    public String beatifulOutputElementJson(JSONObject object){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(object);
        return jsonString;
    }
}
