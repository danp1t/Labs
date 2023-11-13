import ru.ifmo.se.pokemon.*;

public class Shiftry extends Nuzleaf {
    public Shiftry(String name, int level){
        super(name, level);
        super.setType(Type.GRASS, Type.DARK);
        super.setStats(90, 100, 60, 90, 60, 80);
        Rest Rest = new Rest(Type.PSYCHIC, 0, 0);
        SwordsDance SwordsDance = new SwordsDance(Type.NORMAL, 0 ,0);
        Pound Pound = new Pound(Type.NORMAL, 40, 100);
        Swagger Swagger = new Swagger(Type.NORMAL, 0, 85);
        super.setMove(Rest, SwordsDance, Pound, Swagger);
    }
}
