package classes;

import static classes.Guest.isInHouse;

public class House{
    private String owner;

    public House(Person person){
        this.owner = person.getName();
    }
    public String getOwner(){
        return this.owner;
    }
    public boolean isEmpty(House house, Guest... guests){
        boolean flag = false;
        for (Guest i: guests){
            if (isInHouse(house, i)){
                flag = true;
                break;
            }
        }
        if (flag){return true;}
        else{return false;}
    }
    public String statusHouse(Guest[] guests, House house){
        boolean flag = false;
        for (Guest i: guests){
            boolean flag1 = isEmpty(house, i);
            if (flag1) {
                flag = true;
                break;}
        }

        if (flag) {return "Дома были гости";}
        else {return  "Дом был пуст.";}
    }


    public boolean equals(Object otherObjects){
        if (this == otherObjects) return true;
        if (otherObjects == null) return false;
        if (getClass() != otherObjects.getClass()) return false;
        House other = (House) otherObjects;
        return owner.equals(other.owner);

    }
    public int hashCode(){
        return 5 * owner.hashCode();
    }

    public String toString(){
        return "House[owner=" + this.owner + "]";
    }

    public class Ghost{

    }
}
