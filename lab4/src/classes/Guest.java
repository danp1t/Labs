package classes;

import interfaces.Person;

public class Guest implements Person {
    private String name;
    private boolean status;
    private String house;

    public Guest(String name, Boolean status, House house){
        this.name = name;
        this.status = status;
        this.house = house.getOwner();
    }
    public static boolean isYourHouse(Guest guest){
        return guest.house == guest.name;
    }
    public static boolean isInHouse(House house, Guest guest){
        boolean flag = false;
        if (isYourHouse(guest) == false){
            flag = house.getOwner() == guest.house;
        }
        return flag;
    }

    public String getName() {
        return this.name;
    }
}
