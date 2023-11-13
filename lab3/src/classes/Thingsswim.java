package classes;
import interfaces.Swim;
public class Thingsswim extends Things implements Swim{
    private boolean with_people;
    public Thingsswim(String name, boolean with_people, boolean is_swimming){
        super(name, is_swimming);
        this.with_people = with_people;
    }
    public boolean get_Is_swimming(){
        return true;
    }
    public String swim(){
        return "плыли по воде";
    }
    public String is_status(boolean with_people){
        String s = "";
        if (with_people){
            s = "пустая";
        }
        else {
            s = "с людьми";
        }
    return s;
    }
    public boolean status(){
        return this.with_people;
    }

    public boolean equals(Object otherObjects){
        if (this == otherObjects) return true;
        if (otherObjects == null) return false;
        if (getClass() != otherObjects.getClass()) return false;
        Thingsswim other = (Thingsswim) otherObjects;
        return with_people == other.with_people;

    }

    public String toString(){
        return "Thingsswim[with_people=" + this.with_people + "]";
    }
}
