package org.example.Managers;

import org.example.Collections.*;
import org.example.Exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.example.Managers.CollectionManager.getStudyGroups;
import static org.example.Managers.CommandManager.gettingGroupElement;
import static org.example.Managers.CommandManager.setStatusCommand;

public class ElementManager {
    private static Scanner scanner;
    /**
     * Счетчик количества вводов данных для анализа скрипта на ошибки
     */
    private static int counterInput;
    private static boolean isUserInput = true;

    public static boolean getIsUserInput(){
        return isUserInput;
    }

    public static void setIsUserInput(boolean isUserInput1) {
        isUserInput = isUserInput1;
    }
    public static Scanner getScanner(){
        return scanner;
    }

    public static void setScanner(Scanner sc) {
        scanner = sc;
    }

    /**
     * Метод нахождения уникального ID
     * @return уникальный ID
     */
    private static int nextID(){
        //Получение текущей коллекции
        HashSet<StudyGroup> studyGroups = getStudyGroups();
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
     * @param isUserInput вид ввода
     * @return element для соответсвующих команд
     */
    public static StudyGroup createStudyGroup(boolean isUserInput){
        StudyGroup group = null;
        Scanner sc = null;
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

}
