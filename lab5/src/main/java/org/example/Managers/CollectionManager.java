package org.example.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Collections.*;
import org.example.Exceptions.EmptyCollectionException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
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
            group_array.add(parse_studyGroup_to_json(sg));
        }
        return group_array;
    }

    public JSONObject parse_studyGroup_to_json(StudyGroup group){

        JSONObject group_object = new JSONObject();
        //Разборка коллекции на составляющие
        int id = group.getID();
        String name = group.getName();
        Coordinates coordinates = group.getCoordinates();
        double x = coordinates.get_x();
        Double y = coordinates.get_y();
        String creationDate = group.getCreationDate().toString();
        Integer studentsCount = group.getStudentsCount();
        Double averageMark = group.getAverageMark();
        FormOfEducation formOfEducation = group.getFormOfEducation();
        Semester semesterEnum = group.getSemesterEnum();
        Person groupAdmin = group.getGroupAdmin();
        String name_person = groupAdmin.getName();
        String birthday = groupAdmin.getBirthday().toString();
        EyeColor eyeColor = groupAdmin.getEyeColor();
        HairColor hairColor = groupAdmin.getHairColor();

        group_object.put("id", id);
        group_object.put("name", name);

        //Создание объекта для координат
        JSONObject coordinates_json = new JSONObject();
        coordinates_json.put("x", x);
        coordinates_json.put("y", y);
        group_object.put("coordinates", coordinates_json);

        group_object.put("creationDate", creationDate);
        group_object.put("studentsCount", studentsCount);
        group_object.put("averageMark", averageMark);
        group_object.put("formOfEducation", formOfEducation);
        group_object.put("semesterEnum", semesterEnum);

        //Создание объекта для старосты
        JSONObject admin = new JSONObject();
        admin.put("name", name_person);
        admin.put("birthday", birthday);
        admin.put("eyeColor", eyeColor);
        admin.put("hairColor", hairColor);
        group_object.put("groupAdmin", admin);

        return group_object;
    }


    public void save_hashSet_to_file() {
        String json_string = beatiful_output_json();
        try(FileWriter fileWriter = new FileWriter("/home/danp1t/github/Labs/lab5/src/main/java/org/example/Files/Collection.json")) {
            fileWriter.write(json_string);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public String print_min_by_semester_enum() {
        HashSet hashSet = get_HashSet();
        Semester min_semester = Semester.SIXTH;
        StudyGroup min_group = null;

        try {
            if (hashSet.size() == 0) {
                throw new EmptyCollectionException();
            }
            for (Object element : hashSet) {
                StudyGroup group = (StudyGroup) element;
                Semester group_semester = group.getSemesterEnum();

                if (group_semester == Semester.SECOND) {
                    min_group = group;
                    break;
                } else if (min_semester == Semester.SIXTH && group_semester == Semester.FIFTH) {
                    min_semester = group_semester;
                    min_group = group;
                } else if (min_semester == Semester.SIXTH && group == null) {
                    min_group = group;
                }
            }
            return beatiful_output_element_json(parse_studyGroup_to_json(min_group));
        } catch (EmptyCollectionException e) {
            return e.send_message();
        }
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
        System.out.println("Коллекция очищена");
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

    public String beatiful_output_element_json(JSONObject object){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json_string = gson.toJson(object);
        return json_string;
    }
}
