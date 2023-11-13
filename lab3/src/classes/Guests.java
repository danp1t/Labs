package classes;

import interfaces.Person;

public class Guests implements Person {
    private String name;
    private boolean status;

    public Guests(String name, Boolean status){
        this.name = name;
        this.status = status;
    }

    public boolean is_in_house(){
        return this.status;
    }

    public String get_name() {
        return this.name;
    }
}
