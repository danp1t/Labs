import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args){
        Battle b = new Battle();
        Pokemon Tauros = new Tauros("Бык", 1);
        Pokemon Surskit = new Surskit("Паук", 1);
        Pokemon Masquerain = new Masquerain("Бабочка", 1);
        Pokemon Seedot = new Seedot("Юла", 1);
        Pokemon Nuzleaf = new Nuzleaf("Деревяшка", 1);
        Pokemon Shiftry = new Shiftry("Крутой", 1);
        b.addAlly(Tauros);
        b.addAlly(Masquerain);
        b.addAlly(Nuzleaf);
        b.addFoe(Surskit);
        b.addFoe(Seedot);
        b.addFoe(Shiftry);
        b.go();
    }
}