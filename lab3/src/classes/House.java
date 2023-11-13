package classes;

public class House{
    private String owner;

    public House(String owner){
        this.owner = owner;
    }
    public boolean is_empty(Guests... guests){
        boolean flag = false;
        for (Guests i: guests){
            if (i.is_in_house()){
                flag = true;
                break;
            }
        }
        if (flag){return true;}
        else{return false;}
    }
    public String status_house(Guests... guests){
        boolean flag = is_empty(guests);
        if (flag) {return "Дома были гости";}
        else {return  "Дом был пуст.";}
    }

    public String is_furnishings(Things... things){
        boolean flag = false;
        int count = 0;
        for (Things i: things){
            count++;
        }
        if (count > 10){
            return "Вещей хватит для меблировки гостинной";
        }
        else {return "Вещей не хватит для меблировки гостинной";}
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

}
