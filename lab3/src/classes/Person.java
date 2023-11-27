package classes;

public class Person implements interfaces.Person {
    private String name;
    private Tail tail;
    //Конструктор
    public Person(String name){
        this.name = name;
        this.tail = new Tail(name, true);

    }
    //Methods
    //Метод обнять
    public String hug(Thingroof... thingsroofs){
        return this.name + " обнимала на крыше " + (Thingroof.toString(thingsroofs)).replace("[", "").replace("]", "").toLowerCase();
    }
    //Метод отодвинуться от моря
    public String moveAway(){
        return this.name + " отодвинулась от моря";
    }
    //Метод "не нравится, еcли хвост в воде"
    public String doNotLike(){
        String s = "";
        boolean status = tail.getStatus(tail);
        if (status){
            s = " не нравиться, что хвост в воде";
        }
        return s;
    }
    //Метод "сидеть"
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
        return "Mumi_mom[name=" + this.name + ", tail=" + tail.getStatus(tail) + "]";
    }


}
