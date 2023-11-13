package classes;

public enum Nouns {
    MALE1(""),
    MALE2("а"),
    MALE3("у"),
    MALE4(""),
    MALE5("ом"),
    MALE6("е"),
    FEMALE1(""),
    FEMALE2("у"),
    FEMALE3("е"),
    FEMALE4("у"),
    FEMALE5("ой"),
    FEMALE6("е"),
    NEUTER1(""),
    NEUTER2("а"),
    NEUTER3("у"),
    NEUTER4(""),
    NEUTER5("ом"),
    NEUTER6("е");

    public String name;
    Nouns(String name) {this.name = name;}

    public String getName() {
        return name;
    }
}
