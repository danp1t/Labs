package org.example.Managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Collections.*;
import org.example.Exceptions.*;
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
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.example.Commands.ExecuteCommand.counter_line;
import static org.example.Managers.CommandManager.*;

public class CollectionManager {

    public static String fileName;
    private static String createDateHashSet;
    private static JSONArray json_file;
    public static HashSet<StudyGroup> study_groups;
    public static int counter_input;
    //Конструктор
    public CollectionManager(String fileName){
        this.fileName = fileName;
    }
    public CollectionManager(){
        this.fileName = "/home/danp1t/github/Labs/lab5/src/main/java/org/example/Files/Collection.json";
    }
    public static JSONArray read_json_file(){
        try {
            JSONArray json_file = (JSONArray) new JSONParser().parse(new FileReader(fileName));
            return json_file;
        } catch (IOException | ParseException e) {
            System.out.println("Ошибка в команде show");}
        return null;
    }

    public static JSONArray parse_hashset_to_json(){
        Set<StudyGroup> studyGroups = print_HashSet();
        JSONArray group_array = new JSONArray();
        for (StudyGroup sg : studyGroups){
            group_array.add(parse_studyGroup_to_json(sg));
        }
        return group_array;
    }

    public static JSONObject parse_studyGroup_to_json(StudyGroup group){

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


    public static void save_hashSet_to_file() {
        String json_string = beatiful_output_json();
        try(FileWriter fileWriter = new FileWriter("/home/danp1t/github/Labs/lab5/src/main/java/org/example/Files/Collection.json")) {
            fileWriter.write(json_string);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String print_min_by_semester_enum() {
        if (study_groups == null){get_HashSet();}
        HashSet hashSet = study_groups;
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
                } else if (min_semester == Semester.SIXTH && min_group == null) {
                    min_group = group;
                }
            }

            return beatiful_output_element_json(parse_studyGroup_to_json(min_group));
        } catch (EmptyCollectionException e) {
            return e.send_message();
        }
    }

    public static void get_HashSet(){
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime new_creationDate = LocalDateTime.parse(creationDate, formatter);

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
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        study_groups = studyGroups;
        createDateHashSet = formattedDateTime;
    }

    public static void clear_hashSet(){
        //Очистка HashSet
        if (study_groups == null) {get_HashSet();}
        study_groups.clear();
        System.out.println("Коллекция очищена");
    }
    public static Set print_HashSet(){
        if (study_groups == null){
            get_HashSet();
        }
        HashSet<StudyGroup> studyGroups = study_groups;
        Set<StudyGroup> sortedGroups = new TreeSet<>(study_groups);
        return sortedGroups;
    }

    public static String print_info_HashSet(){
        if (study_groups == null) {get_HashSet();}
        return "Тип: " + study_groups.getClass() + "\n" +
                "Дата инициализации: " + createDateHashSet + "\n" +
                "Количество элементов: " + study_groups.size() + "\n" +
                "Множество пустое: " + study_groups.isEmpty();
    }

    public static String beatiful_output_json(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json_string = gson.toJson(parse_hashset_to_json());
        return json_string;
    }

    public static String beatiful_output_element_json(JSONObject object){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json_string = gson.toJson(object);
        return json_string;
    }

    public static String input_name(Scanner sc, boolean is_user_input){
        boolean flag = false;
        counter_input = 0;
        String name = null;
            while (!flag){
                try {
                    if (is_user_input){
                        counter_input = 0;
                        System.out.print("Введите имя: ");}

                    name = sc.nextLine().split(" ")[0];
                    counter_input += 1;
                    if (name != null && name != ""){
                        flag = true;
                    }
                    else if (name == null) {
                        throw new NullFieldException();
                    }
                    else if (name == "") {
                        throw new EmptyStringFieldException();
                    }
                    if (counter_input > 1) {throw new InputFromFIleException();}
                }
                catch (NullFieldException e){
                    System.out.println(e.send_message());
                }
                catch (EmptyStringFieldException e){
                    System.out.print(e.send_message());
                }
                catch (InputFromFIleException e){
                    break;
                }
            }
        return name;
    }

    public static Coordinates input_coordinates(Scanner sc, boolean is_user_input){
        counter_input = 0;
        if (is_user_input) {System.out.println("Введите координаты");}

        double new_x = 0;
        Double y = null;
        boolean flag = true;
        while(flag) {
            try{
                if (is_user_input) {
                    counter_input = 0;
                    System.out.print("x = ");}

                String x = sc.nextLine().split(" ")[0];
                counter_input += 1;
                new_x = Double.parseDouble(x);
                flag = false;
                if (counter_input > 2) {throw new InputFromFIleException();}
            }
            catch (NumberFormatException e){
                System.out.println("Ошибка при вводе поля x. Оно должно быть числом с плавующей точкой. Повторите попытку");
            }
            catch (InputFromFIleException e){
                break;
            }
        }
        flag = true;
        while(flag) {
            try{
                if (is_user_input) {
                    counter_input = 0;
                    System.out.print("y = ");}
                String x = sc.nextLine().split(" ")[0];
                counter_input += 1;
                y = Double.parseDouble(x);
                if (y != null) {flag = false;}
                else {
                    throw new NullFieldException();
                }
                if (counter_input > 2) {throw new InputFromFIleException();}
            }
            catch (NumberFormatException e){
                System.out.println("Ошибка при вводе поля x. Оно должно быть числом с плавующей точкой. Повторите попытку");
            }
            catch (NullFieldException e){
                System.out.println(e.send_message());
            }
            catch (InputFromFIleException e) {
                break;
            }
        }
        //Перевод в double
        Coordinates coordinates = new Coordinates(new_x, y);
        return coordinates;
    }

    public static Integer input_students_count(Scanner sc, boolean is_user_input){
        counter_input = 0;
        boolean flag = true;
        Integer students_count = 0;
        while (flag) {
            try {
                if (is_user_input) {
                    counter_input = 0;
                    System.out.print("Введите количество студентов в группе: ");}
                String str_students_count = sc.nextLine().split(" ")[0];
                counter_input += 1;
                if (counter_input > 1) {throw new InputFromFIleException();}
                students_count = Integer.parseInt(str_students_count);
                if (students_count <= 0) {
                    throw new NotPositiveField();
                }
                else if (students_count == null) {
                    throw new NullFieldException();
                }
                else {flag = false;}
            }
            catch (NotPositiveField e) {
                System.out.println(e.send_message());
            }
            catch (NullFieldException e){
                System.out.println(e.send_message());
            }
            catch (NumberFormatException e) {
                System.out.println("Введенное значение не соответствует целочисленному типу. Повторите ввод еще раз");
            }
            catch (InputFromFIleException e){
                break;
            }
        }
        return students_count;
    }

    public static Double input_average_mark(Scanner sc, boolean is_user_input){
        boolean flag = true;
        counter_input = 0;
        Double averageMark = 0.0;
        while (flag){
            try{
                if (is_user_input) {
                    counter_input = 0;
                    System.out.print("Введите среднюю оценку группы: ");}
                String str_students_count = sc.nextLine().split(" ")[0];
                counter_input += 1;
                if (counter_input > 1) {throw new InputFromFIleException();}
                averageMark = Double.parseDouble(str_students_count);
                if (averageMark <= 0.0) {
                    throw new NotPositiveField();
                }
                else if (averageMark == null) {
                    throw new NullFieldException();
                }
                else {flag = false;}
            }
            catch (NotPositiveField e){
                System.out.println(e.send_message());
            }
            catch (NullFieldException e){
                System.out.println(e.send_message());
            }
            catch (NumberFormatException e){
                System.out.println("Введено число не с плавающей точкой. Попробуйте еще раз.");
            }
            catch (InputFromFIleException e){
                break;
            }

        }
        return averageMark;
    }

    public static FormOfEducation input_form_of_education(Scanner sc, boolean is_user_input){
        counter_input = 0;
        boolean flag = true;
        FormOfEducation formOfEducation = null;
        while (flag) {
            try {
                if (is_user_input) {
                    counter_input = 0;
                    System.out.println("Доступные форматы: DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES");
                    System.out.print("Введите формат обучения: ");}

                String str_form_of_education = sc.nextLine().split(" ")[0];
                counter_input += 1;
                if (counter_input > 1) {throw new InputFromFIleException();}
                if (str_form_of_education.equals("DISTANCE_EDUCATION")  ||
                        str_form_of_education.equals("FULL_TIME_EDUCATION")  ||
                        str_form_of_education.equals("EVENING_CLASSES")){
                    flag = false;
                }
                else {
                    throw new NotFoundEnum();
                }
                formOfEducation = FormOfEducation.valueOf(str_form_of_education);
            }
            catch (NotFoundEnum e){
                System.out.println(e.send_message());
            }
            catch (InputFromFIleException e){
                break;
            }
        }
        return formOfEducation;
    }

    public static Semester input_semester_enum(Scanner sc, boolean is_user_input){
        counter_input = 0;
        boolean flag = true;
        Semester semester = null;
        while (flag) {
            try {
                if (is_user_input) {
                    counter_input = 0;
                    System.out.println("Доступные семестры: SECOND, FIFTH, SIXTH");
                    System.out.print("Введите семестр обучения: ");}
                String str_semester = sc.nextLine().split(" ")[0];
                counter_input += 1;
                if (counter_input > 1) {throw new InputFromFIleException();}
                if (str_semester.equals("SECOND")  ||
                        str_semester.equals("FIFTH")  ||
                        str_semester.equals("SIXTH")){
                    flag = false;
                }
                else {
                    throw new NotFoundEnum();
                }
                semester = Semester.valueOf(str_semester);
            }
            catch (NotFoundEnum e){
                System.out.println(e.send_message());
            }
            catch (InputFromFIleException e){
                break;
            }
        }
        return semester;
    }

    public static Person input_admin_group(Scanner sc, boolean is_user_input){
        counter_input = 0;
        if (is_user_input) {System.out.println("Заполните значение старосты группы");}
        boolean flag = false;
        String admin_name = null;
        LocalDate birthday = null;
        EyeColor eyeColor = null;
        HairColor hairColor = null;
        while (!flag){
            try {
                if (is_user_input) {
                    counter_input = 0;
                    System.out.print("Введите имя старосты группы: ");}
                admin_name = sc.nextLine().split(" ")[0];
                counter_input += 1;
                if (counter_input > 4) {throw new InputFromFIleException();}
                if (admin_name != null && admin_name != ""){
                    flag = true;
                }
                else if (admin_name == null) {
                    throw new NullFieldException();
                }
                else if (admin_name == "") {
                    throw new EmptyStringFieldException();
                }
            }
            catch (NullFieldException e){
                System.out.print(e.send_message());
            }
            catch (EmptyStringFieldException e){
                System.out.print(e.send_message());
            }
            catch (InputFromFIleException e){
                break;
            }
        }
        flag = false;
        while (!flag){
            try {
                if (is_user_input) {
                    counter_input = 0;
                    System.out.print("Введите дату рождения старосты группы в формате dd.MM.yyyy: ");}
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String str_birthday = sc.nextLine().split(" ")[0];
                counter_input += 1;
                if (counter_input > 4) {throw new InputFromFIleException();}
                birthday = LocalDate.parse(str_birthday, formatter);

                if (birthday != null){
                    flag = true;
                }
                else if (birthday == null) {
                    throw new NullFieldException();
                }
            }
            catch (NullFieldException e){
                System.out.print(e.send_message());
            }
            catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты! Попробуйте еще раз");
            }
            catch (InputFromFIleException e){
                break;
            }
        }
        flag = false;
        while (!flag) {
            try {
                if (is_user_input) {
                    counter_input = 0;
                    System.out.println("Доступные цвета глаз: BLUE, ORANGE, WHITE");
                    System.out.print("Введите цвет глаз старосты: ");}
                String str_eye_color = sc.nextLine().split(" ")[0];
                counter_input += 1;
                if (counter_input > 4) {throw new InputFromFIleException();}
                if (str_eye_color.equals("BLUE")  ||
                        str_eye_color.equals("ORANGE")  ||
                        str_eye_color.equals("WHITE")){
                    flag = true;
                }
                else {
                    throw new NotFoundEnum();
                }
                eyeColor = EyeColor.valueOf(str_eye_color);
            }
            catch (NotFoundEnum e){
                System.out.println(e.send_message());
            }
            catch (InputFromFIleException e){
                break;
            }
        }
        flag = false;
        while (!flag) {
            try {
                if (is_user_input) {
                    counter_input = 0;
                    System.out.println("Доступные цвета волос: BLACK, ORANGE, WHITE, BROWN");
                    System.out.print("Введите цвет волос старосты: ");}

                String str_hair_color = sc.nextLine().split(" ")[0];
                counter_input += 1;
                if (counter_input > 4){
                    throw new InputFromFIleException();
                }
                if (str_hair_color.equals("BLACK")  ||
                        str_hair_color.equals("ORANGE")  ||
                        str_hair_color.equals("WHITE") ||
                        str_hair_color.equals("BROWN")){
                    flag = true;
                }
                else {
                    throw new NotFoundEnum();
                }
                hairColor = HairColor.valueOf(str_hair_color);
            }
            catch (NotFoundEnum e){
                System.out.println(e.send_message());
            }
            catch (InputFromFIleException e) {
                break;
            }
        }
        Person admin_group = new Person(admin_name, birthday, eyeColor, hairColor);
        return admin_group;
    }

    public static int nextID(){
        if (study_groups == null) {get_HashSet();}
        ArrayList groups = new ArrayList<>();
        for (StudyGroup group : study_groups){
            groups.add(group.getID());
        }
        Collections.sort(groups);
        int nextID = 1;
        for (Object number : groups) {
            int new_number = (int) number;
            if (new_number == nextID) {
                nextID = nextID + 1;
            }
        }
        return nextID;
    }

    public static java.time.LocalDateTime create_date_creating(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        LocalDateTime date_creating = LocalDateTime.parse(formattedDateTime, formatter);
        return date_creating;
    }

    public static StudyGroup create_study_group(Scanner sc, boolean is_user_input){
        StudyGroup group = null;
        try{
            int id = nextID();
            String name = input_name(sc, is_user_input);
            if (counter_input > 1) {throw new InputFromFIleException();}
            Coordinates coordinates = input_coordinates(sc, is_user_input);
            if (counter_input > 2) {throw new InputFromFIleException();}
            Integer studentsCount = input_students_count(sc, is_user_input);
            if (counter_input > 1) {throw new InputFromFIleException();}
            Double averageMark = input_average_mark(sc, is_user_input);
            if (counter_input > 1) {throw new InputFromFIleException();}
            FormOfEducation formOfEducation = input_form_of_education(sc, is_user_input);
            if (counter_input > 1) {throw new InputFromFIleException();}
            Semester semester = input_semester_enum(sc, is_user_input);
            if (counter_input > 1) {throw new InputFromFIleException();}
            Person adminGroup = input_admin_group(sc, is_user_input);
            if (counter_input > 4) {throw new InputFromFIleException();}
            LocalDateTime creating_data = create_date_creating();
            group = new StudyGroup(id, name, coordinates, creating_data, studentsCount, averageMark, formOfEducation, semester, adminGroup);
            }
        catch (InputFromFIleException e){
            status_command =-1;
            System.out.println();
            System.out.println(e.send_message());
            return null;
        }
        catch (NoSuchElementException e){
            System.out.println("НЕ ПОВЕЗЛО");
        }
        return group;
    }

    public static StudyGroup update_study_group(Scanner sc, boolean is_user_input){
        StudyGroup group = null;
        try {int id = group_element.getID();
            if (is_user_input) {System.out.print("(" + group_element.getName() + ") ");}
            String name = input_name(sc, is_user_input);
            if (is_user_input) {System.out.print("(" + group_element.getCoordinates() + ") ");}
            Coordinates coordinates = input_coordinates(sc, is_user_input);
            if (is_user_input) {System.out.print("(" + group_element.getStudentsCount() + ") ");}
            Integer studentsCount = input_students_count(sc, is_user_input);
            if (is_user_input) {System.out.print("(" + group_element.getAverageMark() + ") ");}
            Double averageMark = input_average_mark(sc, is_user_input);
            if (is_user_input) {System.out.print("(" + group_element.getFormOfEducation() + ") ");}
            FormOfEducation formOfEducation = input_form_of_education(sc, is_user_input);
            if (is_user_input) {System.out.print("(" + group_element.getSemesterEnum() + ") ");}
            Semester semester = input_semester_enum(sc, is_user_input);
            if (is_user_input) { System.out.print("(" + group_element.getGroupAdmin() + ") ");}
            Person adminGroup = input_admin_group(sc, is_user_input);
            LocalDateTime creating_data = group_element.getCreationDate();
            group = new StudyGroup(id, name, coordinates, creating_data, studentsCount, averageMark, formOfEducation, semester, adminGroup);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return group;
    }
    public static void get_group_element(){
        try {
            if (study_groups == null) {get_HashSet();}
            String arg = history_list.getLast().split(" ")[1];
            //Перевод из численного в строковый тип
            int number = 1;
            number = Integer.parseInt(arg);
            HashSet<StudyGroup> new_study_groups = new HashSet<>();
            for (StudyGroup group : study_groups) {
                if (number == group.getID()) {
                    System.out.println("Коллекция с id: " + number + " найдена.");
                    System.out.println("Текучие значения указаны в скобках.");
                    group_element = group;
                }
                else {new_study_groups.add(group);}
            }
            if (new_study_groups.size() == study_groups.size()) {
                throw new NotCollectionIDFound();
            }
        }
        catch (NumberFormatException e){
            System.out.println("Для коллекции введен не целочисленный id. Введите команду еще раз");
            status_command = -1;
        }
        catch (NotCollectionIDFound e){
            System.out.println(e.send_message());
            status_command = -1;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Введите ID элемента");
            status_command = -1;
        }
    }
}
