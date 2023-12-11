package classes;

public enum Adjective {
    FEMALE("ая"),
    NEUTER("ое"),
    MALE("ой"),
    MN("ые");

    public String name;
    Adjective(String name) {this.name = name;}

    public String getName() {
        return name;
    }
}
