package org.example.Collections;

public class StudyGroup {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer studentsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private Double averageMark; //Значение поля должно быть больше 0, Поле не может быть null
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле не может быть null

    //Конструктор
    public StudyGroup(String name, Coordinates coordinates, Integer studentsCount, Double averageMark, FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin){
        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }
    public StudyGroup(int id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, Integer studentsCount, Double averageMark, FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }


    //Генератор id

    //Генератор текущей даты

    //Проверка на валидность
    public boolean validate(){
        if (id <= 0){
            return false;
        }
        if (name == "" || name == null){
            return false;
        }
        if (coordinates == null){
            return false;
        }
        if (creationDate == null){
            return false;
        }
        if (studentsCount <= 0 || studentsCount == null){
            return false;
        }
        if (averageMark <= 0 || averageMark == null){
            return false;
        }
        if (formOfEducation == null){
            return false;
        }
        if (semesterEnum == null){
            return false;
        }
        if (groupAdmin == null){
            return false;
        }
        return true;
    }

    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public java.time.LocalDateTime getCreationDate(){
        return creationDate;
    }

    public Integer getStudentsCount(){
        return studentsCount;
    }
    public Double getAverageMark(){
        return averageMark;
    }

    public FormOfEducation getFormOfEducation(){
        return formOfEducation;
    }

    public Semester getSemesterEnum(){
        return semesterEnum;
    }

    public Person getGroupAdmin(){
        return groupAdmin;
    }
}

