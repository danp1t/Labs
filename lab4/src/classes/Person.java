package classes;

import interfaces.Swim;

public class Person implements interfaces.Person {
    private String name;
    private Tail tail;
    private boolean is_in_water;
    //Добавить внутренний класс Tail сюда
    public class Tail implements Swim {



        public String swim(){
            return name + " плавал в воде";
        }
        public boolean getStatus(){
            return is_in_water;
        }}


    public Person(String name, boolean is_in_water){
        this.name = name;
        this.tail = new Tail();
        this.is_in_water = is_in_water;

    }
    public String hug(Thingroof... thingsroofs){
        return this.name + " обнимала на крыше " + (Thingroof.toString(thingsroofs)).replace("[", "").replace("]", "").toLowerCase();
    }
    public String moveAway(){
        return this.name + " отодвинулась от моря";
    }
    public String doNotLike(){
        String s = "";
        boolean status = tail.getStatus();
        if (status){
            s = " не нравиться, что хвост в воде";
        }
        else {
             s = " все нравится!";}
        return s;

    }
    public String sittingRoof(){
        return "сидеть на крыше";
    }
    public String getName(){
        return this.name;
    }

    public boolean equals(Object otherObjects){
        if (this == otherObjects) return true;
        if (otherObjects == null) return false;
        if (getClass() != otherObjects.getClass()) return false;
        Person other = (Person) otherObjects;
        return name.equals(other.name) && tail.equals(other.tail);
        
    }
    public int hashCode(){
        return 3 * name.hashCode() + 11 * tail.hashCode();
    }

    public String toString(){
        return "Person[name=" + this.name + ", tail=" + tail.getStatus() + "]";
    }


}
