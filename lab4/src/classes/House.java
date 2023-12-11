package classes;

import static classes.Guest.isInHouse;

public class House{
    private Person owner;
    private int size;
    private boolean are_there_ghosts;

    public House(Person person, int size, boolean are_there_ghosts){
        this.owner = person;
        this.size = size;
        this.are_there_ghosts = are_there_ghosts;
    }
    public String getOwner(){
        return this.owner.getName();
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
    public boolean getStatusGhost(){
        return are_there_ghosts;
    }
    public Object moveGhost(boolean are_there_ghosts){
        if (!are_there_ghosts) {return "Ничего не потустороннего не происходит";}
        else{
            class Ghost {

                public static String message() {
                    String[] names = {"Призрак-007", "Гарри Поттер", "Знаменитый Игорь"};
                    String[] activity = {"злобно прыгал", "весело бегал", "громко читал"};
                    int n = (int) Math.floor(Math.random() * names.length);
                    int n1 = (int) Math.floor(Math.random() * activity.length);
                    String s = names[n] + ' ' + activity[n1];
                    return s;
                }


            }
            return Ghost.message() + ". Тем самым создавая не комфортную обстановку в доме!";
    }

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
    public int getSize(){
        return this.size;
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
