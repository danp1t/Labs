import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args){
        Battle b = new Battle();
        Pokemon Tauros = new Tauros("Бык", 20);
        Pokemon Surskit = new Surskit("Паук", 20);
        Pokemon Masquerain = new Masquerain("Бабочка", 30);
        Pokemon Seedot = new Seedot("Юла", 20);
        Pokemon Nuzleaf = new Nuzleaf("Деревяшка", 35);
        Pokemon Shiftry = new Shiftry("Крутой", 35);
        b.addAlly(Surskit);
        b.addAlly(Nuzleaf);
        b.addAlly(Tauros);
        b.addFoe(Masquerain);
        b.addFoe(Seedot);
        b.addFoe(Shiftry);
        b.go();
    }
}
