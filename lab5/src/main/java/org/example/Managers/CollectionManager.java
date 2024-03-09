package org.example.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Collections.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionManager {

    public String fileName;
    private static JSONArray json_file;
    //Конструктор
    public CollectionManager(String fileName){
        this.fileName = fileName;
    }
    public CollectionManager(){
        this.fileName = "/home/danp1t/github/Labs/lab5/src/main/java/org/example/Files/Collection.json";
    }
    public JSONArray read_json_file(){
        try {
            JSONArray json_file = (JSONArray) new JSONParser().parse(new FileReader(fileName));
            return json_file;
        } catch (IOException | ParseException e) {
            System.out.println("Ошибка в команде show");}
        return null;
    }

    public JSONArray parse_hashset_to_json(){
        Set<StudyGroup> studyGroups = print_HashSet();
        JSONArray group_array = new JSONArray();
        for (StudyGroup sg : studyGroups){
            JSONObject group = new JSONObject();

            //Разборка коллекции на составляющие
            int id = sg.getID();
            String name = sg.getName();
            Coordinates coordinates = sg.getCoordinates();
            double x = coordinates.get_x();
            Double y = coordinates.get_y();
            String creationDate = sg.getCreationDate().toString();
            Integer studentsCount = sg.getStudentsCount();
            Double averageMark = sg.getAverageMark();
            FormOfEducation formOfEducation = sg.getFormOfEducation();
            Semester semesterEnum = sg.getSemesterEnum();
            Person groupAdmin = sg.getGroupAdmin();
            String name_person = groupAdmin.getName();
            String birthday = groupAdmin.getBirthday().toString();
            EyeColor eyeColor = groupAdmin.getEyeColor();
            HairColor hairColor = groupAdmin.getHairColor();

            group.put("id", id);
            group.put("name", name);

            //Создание объекта для координат
            JSONObject coordinates_json = new JSONObject();
            coordinates_json.put("x", x);
            coordinates_json.put("y", y);
            group.put("coordinates", coordinates_json);

            group.put("creationDate", creationDate);
            group.put("studentsCount", studentsCount);
            group.put("averageMark", averageMark);
            group.put("formOfEducation", formOfEducation);
            group.put("semesterEnum", semesterEnum);

            //Создание объекта для старосты
            JSONObject admin = new JSONObject();
            admin.put("name", name_person);
            admin.put("birthday", birthday);
            admin.put("eyeColor", eyeColor);
            admin.put("hairColor", hairColor);
            group.put("groupAdmin", admin);

            group_array.add(group);
        }
        return group_array;
    }




    public HashSet get_HashSet(){
        HashSet<StudyGroup> studyGroups = new HashSet<StudyGroup>();
        if (json_file == null){
            json_file = read_json_file();
        }
        for (int i = 0; i < json_file.size(); i++){
            JSONObject object = (JSONObject) json_file.get(i);

            //Прочитать Json-объект. Распарсить его на элементы
            Long id = (Long) object.get("id");
            String name = (String) object.get("name");
            JSONObject coordinates = (JSONObject) object.get("coordinates");
            String creationDate = (String) object.get("creationDate");
            Long studentsCount = (Long) object.get("studentsCount");
            Double averageMark = (Double) object.get("averageMark");
            String formOfEducation = (String) object.get("formOfEducation");
            String semesterEnum = (String) object.get("semesterEnum");
            JSONObject groupAdmin = (JSONObject) object.get("groupAdmin");

            //Преобразовать объекты в нужный тип для конструктора
            //
            int new_id = id.intValue();

            //Преобразовать coordinates
            double x = (double) coordinates.get("x");
            Double y = (Double) coordinates.get("y");
            Coordinates new_coordinates = new Coordinates(x, y);

            //Преобразовать creationDate в нужный тип
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime new_creationDate = LocalDate.parse(creationDate, formatter).atStartOfDay();

            //Преобразовать integerCount
            Integer new_studentsCount = studentsCount.intValue();

            //Преобразовать formOfEducation и semesterEnum
            FormOfEducation new_formOfEducation = FormOfEducation.valueOf(formOfEducation);
            Semester semester = Semester.valueOf(semesterEnum);

            //Преобразовать groupAdmin
            //Распарсинг groupAdmin
            String person_name = (String) groupAdmin.get("name");
            String birthday = (String) groupAdmin.get("birthday");
            String eyeColor = (String) groupAdmin.get("eyeColor");
            String hairColor = (String) groupAdmin.get("hairColor");

            //Преобразование типов
            LocalDate new_birthday = LocalDate.parse(birthday, formatter);
            EyeColor new_eyeColor = EyeColor.valueOf(eyeColor);
            HairColor new_hairColor = HairColor.valueOf(hairColor);

            //Создание админа из конструктора
            Person new_groupAdmim = new Person(person_name, new_birthday, new_eyeColor, new_hairColor);


            //С помощью конструктора для StudyGroup создать новый объект
            StudyGroup group = new StudyGroup(new_id, name, new_coordinates, new_creationDate, new_studentsCount, averageMark, new_formOfEducation, semester, new_groupAdmim);
            //Добавить этот объект в коллекцию
            studyGroups.add(group);

        }

        return studyGroups;
    }

    public void clear_hashSet(){
        json_file = new JSONArray();
        System.out.println(json_file);
    }
    public Set print_HashSet(){
        HashSet<StudyGroup> studyGroups = get_HashSet();
        Set<StudyGroup> sortedGroups = new TreeSet<StudyGroup>(studyGroups);
        return sortedGroups;
    }

    public String beatiful_output_json(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json_string = gson.toJson(parse_hashset_to_json());
        return json_string;
    }
}
