package org.example.Collections;

public class Coordinates {
    private double x;
    private Double y; //Поле не может быть null

    //Конструктор
    public Coordinates(double x, Double y){
        this.x = x;
        this.y = y;
    }

    public boolean validate(){
        if (y == null){
            return false;
        }
        return true;
    }

    public double get_x(){
        return this.x;
    }

    public Double get_y(){
    return this.y;
    }
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
