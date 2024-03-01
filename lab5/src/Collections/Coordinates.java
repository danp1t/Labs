package Collections;

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
}
