import ru.ifmo.se.pokemon.*;

public class Seedot extends Pokemon {
    public Seedot(String name, int level){
        super(name, level);
        super.setType(Type.GRASS);
        super.setStats(40, 40, 50, 30, 30, 30);
        Rest Rest = new Rest(Type.PSYCHIC, 0, 0);
        SwordsDance SwordsDance = new SwordsDance(Type.NORMAL, 0, 0);
        super.setMove(Rest, SwordsDance);
    }
}
