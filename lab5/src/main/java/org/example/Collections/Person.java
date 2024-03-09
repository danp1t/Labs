package org.example.Collections;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDate birthday; //Поле может быть null
    private EyeColor eyeColor; //Поле может быть null
    private HairColor hairColor; //Поле не может быть null

    //Конструктор
    public Person(String name, java.time.LocalDate birthday, EyeColor eyeColor, HairColor hairColor){
        this.name = name;
        this.birthday = birthday;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }
    public boolean validate(){
        if (name == "" || name == null){
            return false;
        }
        if (birthday == null){
            return false;
        }
        if (eyeColor == null){
            return false;
        }
        if (hairColor == null){
            return false;
        }
        return true;
    }

    public String getName(){
        return this.name;
    }

    public java.time.LocalDate getBirthday(){
        return this.birthday;
    }

    public EyeColor getEyeColor(){
        return this.eyeColor;
    }

    public HairColor getHairColor(){
        return this.hairColor;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                '}';
    }
}
