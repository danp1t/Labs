package classes;

import interfaces.Swim;

public class Plants implements Swim {
    private String name;

    public Plants(String name){
        this.name = name;
    }
    public String swim(){
        return " проплывали мимо";
    }
    public String getName(){
        return this.name;
    }

    public boolean equals(Object otherObjects){
        if (this == otherObjects) return true;
        if (otherObjects == null) return false;
        if (getClass() != otherObjects.getClass()) return false;
        Plants other = (Plants) otherObjects;
        return name.equals(other.name);

    }
    public int hashCode(){
        return 52 * name.hashCode();
    }

    public String toString(){
        return "Plants[name=" + this.name + "]";
    }
}
