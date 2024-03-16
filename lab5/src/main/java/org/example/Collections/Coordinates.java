package org.example.Collections;

/**
 * @author danp1t
 * Класс, описывающий местоположение учебной группы
 */
public class Coordinates {
    /** Поле первой координаты x*/
    private double x;
    /** Поле второй координаты y
     * Данное поле не может принимать значение null*/
    private Double y;

    /** Конструктор данного класса */
    public Coordinates(double x, Double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Getter для поля x
     * @return возвращает координату x
     */
    public double getX(){
        return this.x;
    }

    /**
     * Getter для поля y
     * @return возвращает координату y
     */
    public Double getY(){
    return this.y;
    }

    /**
     * Метод для представления класса в текстовом формате
     * @return возвращает текстовое содержание класса
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
