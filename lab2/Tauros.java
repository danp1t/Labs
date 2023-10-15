import ru.ifmo.se.pokemon.*;
public class Tauros extends Pokemon {
    public Tauros(String name, int level){
        super(name, level);
        super.setType(Type.NORMAL);
        super.setStats(75, 100, 95, 40, 70, 110);
        IceBeam IceBeam = new IceBeam(Type.ICE, 90, 100);
        RockTomb RockTomb = new RockTomb(Type.ROCK, 60, 95);
        Swagger Swagger = new Swagger(Type.NORMAL, 0, 85);
        Confide Confide = new Confide();
        super.setMove(IceBeam, RockTomb, Swagger, Confide);
    }
}
