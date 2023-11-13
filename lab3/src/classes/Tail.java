package classes;
import interfaces.Swim;
public class Tail implements Swim {
    private String owner;
    private boolean is_in_water;

    public Tail(String owner, boolean is_in_water){
        this.owner = owner;
        this.is_in_water = is_in_water;

    }

    public String swim(){
        return this.owner + " плавал в воде";
    }
    public boolean getStatus(Tail tail){
        return tail.is_in_water;
    }

    public boolean equals(Object otherObjects){
        if (this == otherObjects) return true;
        if (otherObjects == null) return false;
        if (getClass() != otherObjects.getClass()) return false;
        Tail other = (Tail) otherObjects;
        return owner.equals(other.owner) && is_in_water == other.is_in_water;

    }
    public int hashCode(){
        int i = 0;
        if (is_in_water) {i=1;}
        return 74 * owner.hashCode() + i;
    }

    public String toString(){
        return "Tail[owner=" + this.owner + ", is_in_water=" + is_in_water +"]";
    }
}
