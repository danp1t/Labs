package org.example.Collections;

/**
 * Данный класс описывает старост учебных групп
 */
public class Person {
    /**
     * Поле имени старосты
     * Данное поле не может принимать значение null или быть пустой строкой
     */
    private String name;
    /**
     * Поле дня рождения старосты группы
     * Данное поле не может принимать значение null
     */
    private java.time.LocalDate birthday;
    /**
     * Поле цвета волос старосты группы
     * Данное поле не может принимать значение null
     */
    private HairColor hairColor;
    /**
     * Поле цвета глаз старосты группы
     * Данное поле не может принимать значение null
     */
    private EyeColor eyeColor;

    //Конструктор

    /**
     * Конструктор данного класса
     * @param name имя старосты
     * @param birthday день рождения старосты
     * @param eyeColor цвет глаз старосты
     * @param hairColor цвет волос старосты
     */
    public Person(String name, java.time.LocalDate birthday, EyeColor eyeColor, HairColor hairColor){
        this.name = name;
        this.birthday = birthday;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    /**
     * Getter для поля name
     * @return возвращает имя старосты группы
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter для поля birthday
     * @return возращает дату дня рождения старосты
     */
    public java.time.LocalDate getBirthday(){
        return this.birthday;
    }

    /**
     * Getter для поля eyeColor
     * @return возвращает цвет глаз старосты
     */
    public EyeColor getEyeColor(){
        return this.eyeColor;
    }

    /**
     * Getter для поля hairColor
     * @return возвращает цвет волос старосты
     */
    public HairColor getHairColor(){
        return this.hairColor;
    }

    /**
     * Метод для представления класса в текстовом формате
     * @return возвращает текстовое содержание класса
     */
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
