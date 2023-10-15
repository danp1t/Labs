import ru.ifmo.se.pokemon.*;

public class Nuzleaf extends Pokemon {
    public Nuzleaf(String name, int level){
        super(name, level);
        super.setType(Type.GRASS, Type.DARK);
        super.setStats(70, 70, 40, 60, 40, 60);
        Rest Rest = new Rest(Type.PSYCHIC, 0, 0);
        SwordsDance SwordsDance = new SwordsDance(Type.NORMAL, 0, 0);
        Pound Pound = new Pound(Type.NORMAL, 40, 100);
        super.setMove(Rest, SwordsDance, Pound);
    }
}
