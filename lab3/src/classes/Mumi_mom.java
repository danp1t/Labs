package classes;

import interfaces.Person;

public class Mumi_mom implements Person {
    private String name;
    private Tail tail;
    //Конструктор
    public Mumi_mom(String name){
        this.name = name;
        this.tail = new Tail(name, true);

    }
    //Methods
    //Метод обнять
    public String hug(Thingsroof... thingsroofs){
        return this.name + " обнимала на крыше " + (Thingsroof.toString(thingsroofs)).replace("[", "").replace("]", "").toLowerCase();
    }
    //Метод отодвинуться от моря
    public String move_away(){
        return this.name + " отодвинулась от моря";
    }
    //Метод "не нравится, еcли хвост в воде"
    public String do_not_like(){
        String s = "";
        boolean status = tail.getStatus(tail);
        if (status){
            s = " не нравиться, что хвост в воде";
        }
        return s;
    }
    //Метод "сидеть"
    public String sitting_roof(){
        return "сидеть на крыше";
    }
    public String get_name(){
        return this.name;
    }

    public boolean equals(Object otherObjects){
        if (this == otherObjects) return true;
        if (otherObjects == null) return false;
        if (getClass() != otherObjects.getClass()) return false;
        Mumi_mom other = (Mumi_mom) otherObjects;
        return name.equals(other.name) && tail.equals(other.tail);
        
    }
    public int hashCode(){
        return 3 * name.hashCode() + 11 * tail.hashCode();
    }

    public String toString(){
        return "Mumi_mom[name=" + this.name + ", tail=" + tail.getStatus(tail) + "]";
    }


}
