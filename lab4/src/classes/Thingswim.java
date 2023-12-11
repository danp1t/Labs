package classes;
import interfaces.Swim;
public class Thingswim extends Thing implements Swim{
    private boolean with_people;
    public Thingswim(String name, boolean with_people, boolean is_swimming, int size){
        super(name, is_swimming, size);
        this.with_people = with_people;
    }
    public boolean getIsSwimming(){
        return true;
    }
    public String swim(){
        return "плыли по воде";
    }
    public String isStatus(boolean with_people){
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
        Thingswim other = (Thingswim) otherObjects;
        return with_people == other.with_people;

    }

    public String toString(){
        return "Thingsswim[with_people=" + this.with_people + "]";
    }
}
