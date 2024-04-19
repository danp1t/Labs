package org.example.Managers;

import org.example.Collections.*;
import org.example.Exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.example.Managers.StartManager.getCollectionManager;


public class ElementManager {
    private static Scanner scanner;
    /**
     * Счетчик количества вводов данных для анализа скрипта на ошибки
     */
    private static int counterInput;
    /**
     * Поле для определения вида ввода команд
     * Если значение true, то значит, что команды вводит человек
     * Если значение false, то это значит, что команды выполняются из скрипта
     */
    private static boolean isUserInput = true;

    /**
     * Getter для получения вида ввода команд
     * @return
     */
    public static boolean getIsUserInput(){
        return isUserInput;
    }

    /**
     * Setter для установления вида ввода команд
     * @param isUserInput1
     */
    public static void setIsUserInput(boolean isUserInput1) {
        isUserInput = isUserInput1;
    }

    /**
     * Setter для смены сканера пользователя на чтения скрипта из файла и наоборот
     * @return меняет сканер
     */

    public static void setScanner(Scanner sc) {
        scanner = sc;
    }

    /**
     * Метод нахождения уникального ID
     * @return уникальный ID
     */
    public int nextID(){
        CollectionManager collectionManager = getCollectionManager();
        //Получение текущей коллекции
        HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        ArrayList<Integer> groups = new ArrayList<>();
        //Положить все ID из коллекции в массив
        for (StudyGroup group : studyGroups){
            groups.add(group.getID());
        }
        //Отсортировать массив
        Collections.sort(groups);
        int nextID = 1;
        for (Integer number : groups) {
            if (number == nextID) {
                nextID = nextID + 1;
            }
        }
        return nextID;
    }

    /**
     * Метод для ввода поля name для element
     * @param sc сканнер
     * @param isUserInput тип ввода
     * @return значение для поля name
     */

    public String inputName(Scanner sc, boolean isUserInput){
        boolean flag = false;
        counterInput = 0;
        String name = null;
        while (!flag){
            try {
                if (isUserInput){
                    counterInput = 0;
                    System.out.print("Введите имя: ");
                }
                name = sc.nextLine().strip();
                counterInput += 1;
                if (!name.isEmpty()) flag = true;
                else if (name.isEmpty()) throw new EmptyStringFieldException();

                if (counterInput > 1) throw new InputFromFIleException();
            }
            catch (EmptyStringFieldException e){
                System.out.println(e.sendMessage());
            }
            catch (InputFromFIleException e){
                break;
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
    public Coordinates inputCoordinates(Scanner sc, boolean isUserInput){
        counterInput = 0;
        if (isUserInput) {System.out.println("Введите координаты");}
        boolean flag = true;
        String[] string = {"x", "y"};
        Double[] coordinates = new Double[2];
        int id = 0;
        for (String str : string) {
            while(flag) {
                try{
                    if (isUserInput) {
                        counterInput = 0;
                        System.out.print(str + " = ");}
                    String line = sc.nextLine().strip();
                    if (line.split(" ").length > 1) {
                        throw new InputUserException();
                    }
                    String x = line.split(" ")[0];
                    counterInput += 1;
                    Double newX = Double.parseDouble(x);
                    flag = false;
                    coordinates[id] = newX;
                    id += 1;
                    if (counterInput > 2) {throw new InputFromFIleException();}
                }
                catch (NumberFormatException e){
                    System.out.println("Ошибка при вводе поля " + str + ". Оно должно быть числом с плавающей точкой. Повторите попытку");
                }
                catch (InputFromFIleException e){
                    break;
                }
                catch (InputUserException e){
                    System.out.println(e.sendMessage());
                }
            }
            flag = true;
        }
        return new Coordinates(coordinates[0], coordinates[1]);
    }

    /**
     * Метод для ввода поля studentsCount для element
     * @param sc сканер
     * @param isUserInput вид ввода
     * @return значение для поля studentsCount
     */
    public Integer inputStudentsCount(Scanner sc, boolean isUserInput){
        counterInput = 0;
        boolean flag = true;
        Integer studentsCount = null;
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
                else if (Objects.isNull(studentsCount)) {
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
    public Double inputAverageMark(Scanner sc, boolean isUserInput){
        boolean flag = true;
        counterInput = 0;
        Double averageMark = null;
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
                else if (Objects.isNull(averageMark)) {
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
    public FormOfEducation inputFormOfEducation(Scanner sc, boolean isUserInput){
        counterInput = 0;
        boolean flag = true;
        FormOfEducation formOfEducation = null;
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
    public Semester inputSemesterEnum(Scanner sc, boolean isUserInput){
        counterInput = 0;
        boolean flag = true;
        Semester semester = null;
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
    public Person inputAdminGroup(Scanner sc, boolean isUserInput){
        counterInput = 0;
        if (isUserInput) {System.out.println("Заполните значение старосты группы");}
        boolean flag = false;
        String adminName = null;
        LocalDate birthday = null;
        EyeColor eyeColor = null;
        HairColor hairColor = null;
        while (!flag){
            try {
                if (isUserInput) {
                    counterInput = 0;
                    System.out.print("Введите имя старосты группы: ");}
                adminName = sc.nextLine().strip();
                counterInput += 1;
                if (counterInput > 4) {throw new InputFromFIleException();}
                if (!Objects.isNull(adminName) && !adminName.isEmpty()){
                    flag = true;
                }
                else if (Objects.isNull(adminName)) {
                    throw new NullFieldException();
                }
                else if (adminName.isEmpty()) {
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

                if (!Objects.isNull(birthday)){
                    flag = true;
                }
                else if (Objects.isNull(birthday)) {
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
     * Метод для генерации даты и времени создания коллекции HashSet
     * @return дата и время создания коллекции
     */
    public LocalDateTime createDateCreating(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return LocalDateTime.parse(formattedDateTime, formatter);
    }

    /**
     * Создание element
     * @param isUserInput вид ввода
     * @return element для соответсвующих команд
     */
    public StudyGroup createStudyGroup(boolean isUserInput){
        StudyGroup group = null;
        Scanner sc;
        if (isUserInput) {sc = new Scanner(System.in);}
        else {sc = scanner;};

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
            System.out.println();
            System.out.println(e.sendMessage());
            return null;
        }
        catch (NoSuchElementException e){
            System.out.println("Аварийный выход из программы...");
        }
        return group;
    }

    public StudyGroup createStudyGroup(int id, StudyGroup element){

        StudyGroup group = new StudyGroup(id, element.getName(), element.getCoordinates(), element.getCreationDate(), element.getStudentsCount(), element.getAverageMark(), element.getFormOfEducation(), element.getSemesterEnum(), element.getGroupAdmin());

        return group;
    }
    public StudyGroup createElement(){
        StudyGroup group = null;
        Scanner sc = new Scanner(System.in);

        try{
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

            group = new StudyGroup(name, coordinates, creatingData, studentsCount, averageMark, formOfEducation, semester, adminGroup);
        }
        catch (InputFromFIleException e){
            System.out.println();
            System.out.println(e.sendMessage());
            return null;
        }
        catch (NoSuchElementException e){
            System.out.println("Аварийный выход из программы...");
        }
        return group;
    }

}
