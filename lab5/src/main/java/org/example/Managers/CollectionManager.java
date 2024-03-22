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

import static org.example.Managers.CommandManager.*;

/**
 * Класс для работы с коллекцией HashSet и другими данными
 */
public class CollectionManager {
    /**
     * Путь до json_file, где хранится сохраненная коллекция HashSet
     */
    private static String fileName = System.getenv("JSON_FILE_LAB5");
    /**
     * Дата создания коллекции
     */
    private static String createDateHashSet;
    /**
     * JSONArray, в котором хранится коллекция HashSet
     */
    private static JSONArray jsonFile;
    /**
     * Список учебных групп, которые хранятся в коллекции HashSet
     */
    private static HashSet<StudyGroup> studyGroups;
    /**
     * Счетчик количества вводов данных для анализа скрипта на ошибки
     */
    private static int counterInput;

    /**
     * Конструктор класса
     */
    public CollectionManager(){
        String pathJson = System.getenv("JSON_FILE_LAB5");
        this.fileName = pathJson;
    }

    public static String getCreateDateHashSet(){
        return createDateHashSet;
    }



    /**
     * getter для поля studyGroups
     * @return значение поля studyGroups
     */
    public static HashSet<StudyGroup> getStudyGroups() {
        if (studyGroups == null) {
            getHashSet();
        };
        return studyGroups;
    }

    /**
     * setter для поля studyGroups
     * @param group
     */
    public static void setStudyGroups(HashSet<StudyGroup> group){
        studyGroups = group;
    }

    /**
     * В данном методе мы читаем коллекцию из json файла
     * @return JSONArray
     */
    public static JSONArray readJsonFile(){
        try {
            JSONArray jsonFile = (JSONArray) new JSONParser().parse(new FileReader(fileName));
            return jsonFile;
        } catch (IOException | ParseException e) {
            System.out.println("Ошибка при чтении коллекции из файла");
        }
        return null;
    }

    /**
     * В этом методе мы преобразовываем коллекцию HashSet в JSONArray для последующего сохранения в файл
     * @return JSONArray
     */
    public static JSONArray parseHashsetToJson(){
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
    public static JSONObject parseStudyGroupToJson(StudyGroup group){

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
    public static void getHashSet(){
        HashSet<StudyGroup> studyGroups = new HashSet<StudyGroup>();
        if (jsonFile == null){
            jsonFile = readJsonFile();
        }
        for (int i = 0; i < jsonFile.size(); i++){
            JSONObject object = (JSONObject) jsonFile.get(i);

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
            int newId = id.intValue();

            //Преобразовать coordinates
            double x = (double) coordinates.get("x");
            Double y = (Double) coordinates.get("y");
            Coordinates newCoordinates = new Coordinates(x, y);

            //Преобразовать creationDate в нужный тип
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime newCreationDate = LocalDateTime.parse(creationDate, formatter);

            //Преобразовать integerCount
            Integer newStudentsCount = studentsCount.intValue();

            //Преобразовать formOfEducation и semesterEnum
            FormOfEducation newFormOfEducation = FormOfEducation.valueOf(formOfEducation);
            Semester semester = Semester.valueOf(semesterEnum);

            //Преобразовать groupAdmin
            //Распарсинг groupAdmin
            String personName = (String) groupAdmin.get("name");
            String birthday = (String) groupAdmin.get("birthday");
            String eyeColor = (String) groupAdmin.get("eyeColor");
            String hairColor = (String) groupAdmin.get("hairColor");

            //Преобразование типов
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newBirthday = LocalDate.parse(birthday, formatter);
            EyeColor newEyeColor = EyeColor.valueOf(eyeColor);
            HairColor newHairColor = HairColor.valueOf(hairColor);

            //Создание админа из конструктора
            Person newGroupAdmim = new Person(personName, newBirthday, newEyeColor, newHairColor);


            //С помощью конструктора для StudyGroup создать новый объект
            StudyGroup group = new StudyGroup(newId, name, newCoordinates, newCreationDate, newStudentsCount, averageMark, newFormOfEducation, semester, newGroupAdmim);
            //Добавить этот объект в коллекцию
            studyGroups.add(group);

        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        CollectionManager.studyGroups = studyGroups;
        createDateHashSet = formattedDateTime;
    }

    /**
     * Данный метод возвращает отсортированное множество
     * @return отсортированное множество
     */
    public static Set printHashSet(){
        if (studyGroups == null){
            getHashSet();
        }
        HashSet<StudyGroup> studyGroups = CollectionManager.studyGroups;
        Set<StudyGroup> sortedGroups = new TreeSet<>(CollectionManager.studyGroups);
        return sortedGroups;
    }
    /**
     * Метод возвращает красивый вывод JSONArray
     * @return красивый вывод JSONArray
     */
    public static String beatifulOutputJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(parseHashsetToJson());
        return jsonString;
    }

    /**
     * Метод возвращает красивый вывод JSONObject
     * @param object JSONObject
     * @return красивый вывод jsonObject
     */
    public static String beatifulOutputElementJson(JSONObject object){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(object);
        return jsonString;
    }

    /**
     * Метод для ввода поля name для element
     * @param sc сканнер
     * @param isUserInput тип ввода
     * @return значение для поля name
     */

    public static String inputName(Scanner sc, boolean isUserInput){
        boolean flag = false;
        counterInput = 0;
        String name = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getName();
            while (!flag){
                try {
                    if (isUserInput){
                        counterInput = 0;
                        System.out.print("Введите имя: ");
                    }
                    String line = sc.nextLine().strip();
                    if (line.split(" ").length > 1) {
                        throw new InputUserException();
                    }
                    name = line.split(" ")[0];
                    counterInput += 1;
                    if (name != null && name != ""){
                        flag = true;
                    }
                    else if (name == null) {
                        throw new NullFieldException();
                    }
                    else if (name == "") {
                        throw new EmptyStringFieldException();
                    }
                    if (counterInput > 1) throw new InputFromFIleException();
                }
                catch (NullFieldException e){
                    System.out.println(e.sendMessage());
                }
                catch (EmptyStringFieldException e){
                    System.out.println(e.sendMessage());
                }
                catch (InputFromFIleException e){
                    break;
                }
                catch (InputUserException e){
                    System.out.println(e.sendMessage());
                }
            }
        return name;
    }

    /**
     * Метод для ввода поля coordinates для element
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return значение поля coordinates
     */
    public static Coordinates inputCoordinates(Scanner sc, boolean isUserInput){
        counterInput = 0;
        if (isUserInput) {System.out.println("Введите координаты");}

        double newX = (Objects.isNull(gettingGroupElement())) ? 1 : gettingGroupElement().getCoordinates().getX();
        Double y = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getCoordinates().getY();
        boolean flag = true;
        while(flag) {
            try{
                if (isUserInput) {
                    counterInput = 0;
                    System.out.print("x = ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String x = line.split(" ")[0];
                counterInput += 1;
                newX = Double.parseDouble(x);
                flag = false;
                if (counterInput > 2) {throw new InputFromFIleException();}
            }
            catch (NumberFormatException e){
                System.out.println("Ошибка при вводе поля x. Оно должно быть числом с плавующей точкой. Повторите попытку");
            }
            catch (InputFromFIleException e){
                break;
            }
            catch (InputUserException e){
                System.out.println(e.sendMessage());
            }
        }
        flag = true;
        while(flag) {
            try{
                if (isUserInput) {
                    counterInput = 0;
                    System.out.print("y = ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String x = line.split(" ")[0];
                counterInput += 1;
                y = Double.parseDouble(x);
                if (y != null) {flag = false;}
                else {
                    throw new NullFieldException();
                }
                if (counterInput > 2) {throw new InputFromFIleException();}
            }
            catch (NumberFormatException e){
                System.out.println("Ошибка при вводе поля x. Оно должно быть числом с плавующей точкой. Повторите попытку");
            }
            catch (NullFieldException e){
                System.out.println(e.sendMessage());
            }
            catch (InputFromFIleException e) {
                break;
            }
            catch (InputUserException e) {
                System.out.println(e.sendMessage());
            }
        }
        //Перевод в double
        Coordinates coordinates = new Coordinates(newX, y);
        return coordinates;
    }

    /**
     * Метод для ввода поля studentsCount для element
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return значение для поля studentsCount
     */
    public static Integer inputStudentsCount(Scanner sc, boolean isUserInput){
        counterInput = 0;
        boolean flag = true;
        Integer studentsCount = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getStudentsCount();
        while (flag) {
            try {
                if (isUserInput) {
                    counterInput = 0;
                    System.out.print("Введите количество студентов в группе: ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String strStudentsCount = line.split(" ")[0];
                counterInput += 1;
                if (counterInput > 1) {throw new InputFromFIleException();}
                studentsCount = Integer.parseInt(strStudentsCount);
                if (studentsCount <= 0) {
                    throw new NotPositiveField();
                }
                else if (studentsCount == null) {
                    throw new NullFieldException();
                }
                else {flag = false;}
            }
            catch (NotPositiveField e) {
                System.out.println(e.sendMessage());
            }
            catch (NullFieldException e){
                System.out.println(e.sendMessage());
            }
            catch (NumberFormatException e) {
                System.out.println("Введенное значение не соответствует целочисленному типу. Повторите ввод еще раз");
            }
            catch (InputFromFIleException e){
                break;
            }
            catch (InputUserException e){
                System.out.println(e.sendMessage());
            }
        }
        return studentsCount;
    }

    /**
     * Метод для ввода поля averageMark для element
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return значение для поля averageMark
     */
    public static Double inputAverageMark(Scanner sc, boolean isUserInput){
        boolean flag = true;
        counterInput = 0;
        Double averageMark = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getAverageMark();
        while (flag){
            try{
                if (isUserInput) {
                    counterInput = 0;
                    System.out.print("Введите среднюю оценку группы: ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String strStudentsCount = line.split(" ")[0];
                counterInput += 1;
                if (counterInput > 1) {throw new InputFromFIleException();}
                averageMark = Double.parseDouble(strStudentsCount);
                if (averageMark <= 0.0) {
                    throw new NotPositiveField();
                }
                else if (averageMark == null) {
                    throw new NullFieldException();
                }
                else {flag = false;}
            }
            catch (NotPositiveField e){
                System.out.println(e.sendMessage());
            }
            catch (NullFieldException e){
                System.out.println(e.sendMessage());
            }
            catch (NumberFormatException e){
                System.out.println("Введено число не с плавающей точкой. Попробуйте еще раз.");
            }
            catch (InputFromFIleException e){
                break;
            }
            catch (InputUserException e) {
                System.out.println(e.sendMessage());
            }

        }
        return averageMark;
    }

    /**
     * Метод для ввода поля formOfEducation для element
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return значение поля formOfEducation
     */
    public static FormOfEducation inputFormOfEducation(Scanner sc, boolean isUserInput){
        counterInput = 0;
        boolean flag = true;
        FormOfEducation formOfEducation = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getFormOfEducation();
        while (flag) {
            try {
                if (isUserInput) {
                    counterInput = 0;
                    System.out.println("Доступные форматы: DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES");
                    System.out.print("Введите формат обучения: ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String strFormOfEducation = line.split(" ")[0];
                counterInput += 1;
                if (counterInput > 1) {throw new InputFromFIleException();}
                if (strFormOfEducation.equals("DISTANCE_EDUCATION")  ||
                        strFormOfEducation.equals("FULL_TIME_EDUCATION")  ||
                        strFormOfEducation.equals("EVENING_CLASSES")){
                    flag = false;
                }
                else {
                    throw new NotFoundEnum();
                }
                formOfEducation = FormOfEducation.valueOf(strFormOfEducation);
            }
            catch (NotFoundEnum e){
                System.out.println(e.sendMessage());
            }
            catch (InputFromFIleException e){
                break;
            }
            catch (InputUserException e) {
                System.out.println(e.sendMessage());
            }
        }
        return formOfEducation;
    }

    /**
     * Метод для ввода значения поля semesterEnum для element
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return значение поля semesterEnum
     */
    public static Semester inputSemesterEnum(Scanner sc, boolean isUserInput){
        counterInput = 0;
        boolean flag = true;
        Semester semester = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getSemesterEnum();
        while (flag) {
            try {
                if (isUserInput) {
                    counterInput = 0;
                    System.out.println("Доступные семестры: SECOND, FIFTH, SIXTH");
                    System.out.print("Введите семестр обучения: ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String strSemester = line.split(" ")[0];
                counterInput += 1;
                if (counterInput > 1) {throw new InputFromFIleException();}
                if (strSemester.equals("SECOND")  ||
                        strSemester.equals("FIFTH")  ||
                        strSemester.equals("SIXTH")){
                    flag = false;
                }
                else {
                    throw new NotFoundEnum();
                }
                semester = Semester.valueOf(strSemester);
            }
            catch (NotFoundEnum e){
                System.out.println(e.sendMessage());
            }
            catch (InputFromFIleException e){
                break;
            }
            catch (InputUserException e){
                System.out.println(e.sendMessage());
            }
        }
        return semester;
    }

    /**
     * Метод для ввода поля adminGroup для element
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return значение поля adminGroup
     */
    public static Person inputAdminGroup(Scanner sc, boolean isUserInput){
        counterInput = 0;
        if (isUserInput) {System.out.println("Заполните значение старосты группы");}
        boolean flag = false;
        String adminName = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getGroupAdmin().getName();
        LocalDate birthday = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getGroupAdmin().getBirthday();
        EyeColor eyeColor = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getGroupAdmin().getEyeColor();
        HairColor hairColor = (Objects.isNull(gettingGroupElement())) ? null : gettingGroupElement().getGroupAdmin().getHairColor();
        while (!flag){
            try {
                if (isUserInput) {
                    counterInput = 0;
                    System.out.print("Введите имя старосты группы: ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                adminName = line.split(" ")[0];
                counterInput += 1;
                if (counterInput > 4) {throw new InputFromFIleException();}
                if (adminName != null && adminName != ""){
                    flag = true;
                }
                else if (adminName == null) {
                    throw new NullFieldException();
                }
                else if (adminName == "") {
                    throw new EmptyStringFieldException();
                }
            }
            catch (NullFieldException e){
                System.out.print(e.sendMessage());
            }
            catch (EmptyStringFieldException e){
                System.out.print(e.sendMessage());
            }
            catch (InputFromFIleException e){
                break;
            }
            catch (InputUserException e){
                System.out.println(e.sendMessage());
            }
        }
        flag = false;
        while (!flag){
            try {
                if (isUserInput) {
                    counterInput = 0;
                    System.out.print("Введите дату рождения старосты группы в формате dd.MM.yyyy: ");}
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String strBirthday = line.split(" ")[0];
                counterInput += 1;
                if (counterInput > 4) {throw new InputFromFIleException();}
                birthday = LocalDate.parse(strBirthday, formatter);

                if (birthday != null){
                    flag = true;
                }
                else if (birthday == null) {
                    throw new NullFieldException();
                }
            }
            catch (NullFieldException e){
                System.out.print(e.sendMessage());
            }
            catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты! Попробуйте еще раз");
            }
            catch (InputFromFIleException e){
                break;
            }
            catch (InputUserException e) {
                System.out.println(e.sendMessage());
            }
        }
        flag = false;
        while (!flag) {
            try {
                if (isUserInput) {
                    counterInput = 0;
                    System.out.println("Доступные цвета глаз: BLUE, ORANGE, WHITE");
                    System.out.print("Введите цвет глаз старосты: ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String strEyeColor = line.split(" ")[0];
                counterInput += 1;
                if (counterInput > 4) {throw new InputFromFIleException();}
                if (strEyeColor.equals("BLUE")  ||
                        strEyeColor.equals("ORANGE")  ||
                        strEyeColor.equals("WHITE")){
                    flag = true;
                }
                else {
                    throw new NotFoundEnum();
                }
                eyeColor = EyeColor.valueOf(strEyeColor);
            }
            catch (NotFoundEnum e){
                System.out.println(e.sendMessage());
            }
            catch (InputFromFIleException e){
                break;
            }
            catch (InputUserException e) {
                System.out.println(e.sendMessage());
            }
        }
        flag = false;
        while (!flag) {
            try {
                if (isUserInput) {
                    counterInput = 0;
                    System.out.println("Доступные цвета волос: BLACK, ORANGE, WHITE, BROWN");
                    System.out.print("Введите цвет волос старосты: ");}
                String line = sc.nextLine().strip();
                if (line.split(" ").length > 1) {
                    throw new InputUserException();
                }
                String strHairColor = line.split(" ")[0];
                counterInput += 1;
                if (counterInput > 4){
                    throw new InputFromFIleException();
                }
                if (strHairColor.equals("BLACK")  ||
                        strHairColor.equals("ORANGE")  ||
                        strHairColor.equals("WHITE") ||
                        strHairColor.equals("BROWN")){
                    flag = true;
                }
                else {
                    throw new NotFoundEnum();
                }
                hairColor = HairColor.valueOf(strHairColor);
            }
            catch (NotFoundEnum e){
                System.out.println(e.sendMessage());
            }
            catch (InputFromFIleException e) {
                break;
            }
            catch (InputUserException e){
                System.out.println(e.sendMessage());
            }
        }
        Person adminGroup = new Person(adminName, birthday, eyeColor, hairColor);
        return adminGroup;
    }

    /**
     * Метод нахождения уникального ID
     * @return уникальный ID
     */
    public static int nextID(){
        if (studyGroups == null) {
            getHashSet();}
        ArrayList groups = new ArrayList<>();
        for (StudyGroup group : studyGroups){
            groups.add(group.getID());
        }
        Collections.sort(groups);
        int nextID = 1;
        for (Object number : groups) {
            int newNumber = (int) number;
            if (newNumber == nextID) {
                nextID = nextID + 1;
            }
        }
        return nextID;
    }

    /**
     * Метод для генерации даты и времени создания коллекции HashSet
     * @return дата и время создания коллекции
     */
    public static java.time.LocalDateTime createDateCreating(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        LocalDateTime dateCreating = LocalDateTime.parse(formattedDateTime, formatter);
        return dateCreating;
    }

    /**
     * Создание element
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return element для соответсвующих команд
     */
    public static StudyGroup createStudyGroup(boolean isUserInput){
        StudyGroup group = null;
        Scanner sc = new Scanner(System.in);
        try{
            int id = nextID();
            String name = inputName(sc, isUserInput);
            if (counterInput > 1) {throw new InputFromFIleException();}
            Coordinates coordinates = inputCoordinates(sc, isUserInput);
            if (counterInput > 2) {throw new InputFromFIleException();}
            Integer studentsCount = inputStudentsCount(sc, isUserInput);
            if (counterInput > 1) {throw new InputFromFIleException();}
            Double averageMark = inputAverageMark(sc, isUserInput);
            if (counterInput > 1) {throw new InputFromFIleException();}
            FormOfEducation formOfEducation = inputFormOfEducation(sc, isUserInput);
            if (counterInput > 1) {throw new InputFromFIleException();}
            Semester semester = inputSemesterEnum(sc, isUserInput);
            if (counterInput > 1) {throw new InputFromFIleException();}
            Person adminGroup = inputAdminGroup(sc, isUserInput);
            if (counterInput > 4) {throw new InputFromFIleException();}
            LocalDateTime creatingData = createDateCreating();
            group = new StudyGroup(id, name, coordinates, creatingData, studentsCount, averageMark, formOfEducation, semester, adminGroup);
            }
        catch (InputFromFIleException e){
            setStatusCommand(-1);
            System.out.println();
            System.out.println(e.sendMessage());
            return null;
        }
        catch (NoSuchElementException e){
            System.out.println("Аварийный выход из программы...");
        }
        return group;
    }

    /**
     * Вспомогательный метод для изменения информации об учебной группе
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return измененная учебная группа
     */
    public static StudyGroup updateStudyGroup(Scanner sc, boolean isUserInput){
        StudyGroup group = null;
        try {
            int id = gettingGroupElement().getID();
            if (isUserInput) {System.out.print("(" + gettingGroupElement().getName() + ") ");}
            String name = inputName(sc, isUserInput);
            if (isUserInput) {System.out.print("(" + gettingGroupElement().getCoordinates() + ") ");}
            Coordinates coordinates = inputCoordinates(sc, isUserInput);
            if (isUserInput) {System.out.print("(" + gettingGroupElement().getStudentsCount() + ") ");}
            Integer studentsCount = inputStudentsCount(sc, isUserInput);
            if (isUserInput) {System.out.print("(" + gettingGroupElement().getAverageMark() + ") ");}
            Double averageMark = inputAverageMark(sc, isUserInput);
            if (isUserInput) {System.out.print("(" + gettingGroupElement().getFormOfEducation() + ") ");}
            FormOfEducation formOfEducation = inputFormOfEducation(sc, isUserInput);
            if (isUserInput) {System.out.print("(" + gettingGroupElement().getSemesterEnum() + ") ");}
            Semester semester = inputSemesterEnum(sc, isUserInput);
            if (isUserInput) { System.out.print("(" + gettingGroupElement().getGroupAdmin() + ") ");}
            Person adminGroup = inputAdminGroup(sc, isUserInput);
            LocalDateTime creationDate = gettingGroupElement().getCreationDate();
            group = new StudyGroup(id, name, coordinates, creationDate, studentsCount, averageMark, formOfEducation, semester, adminGroup);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        return group;
    }

    /**
     * Получение element
     */
//    public static void getGroupElement(){
//        try {
//            if (studyGroups == null) {
//                getHashSet();}
//            String arg = historyList.getLast().split(" ")[1];
//            //Перевод из численного в строковый тип
//            int number = 1;
//            number = Integer.parseInt(arg);
//            HashSet<StudyGroup> newStudyGroups = new HashSet<>();
//            for (StudyGroup group : studyGroups) {
//                if (number == group.getID()) {
//                    System.out.println("Коллекция с id: " + number + " найдена.");
//                    System.out.println("Текучие значения указаны в скобках.");
//                    settingGroupElement(group);
//                }
//                else {newStudyGroups.add(group);}
//            }
//            if (newStudyGroups.size() == studyGroups.size()) {
//                throw new NotCollectionIDFound();
//            }
//        }
//        catch (NumberFormatException e){
//            System.out.println("Для коллекции введен не целочисленный id. Введите команду еще раз");
//            setStatusCommand(-1);
//        }
//        catch (NotCollectionIDFound e){
//            System.out.println(e.sendMessage());
//            setStatusCommand(-1);
//        }
//        catch (ArrayIndexOutOfBoundsException e){
//            System.out.println("Введите ID элемента");
//            setStatusCommand(-1);
//        }
//    }
}
