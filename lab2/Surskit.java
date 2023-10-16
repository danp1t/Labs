import ru.ifmo.se.pokemon.*;

public class Surskit extends Pokemon {
    public Surskit(String name, int level){
        super(name, level);
        super.setType(Type.BUG, Type.WATER);
        super.setStats(40, 30, 32, 50, 52, 65);
        Blizzard Blizzard = new Blizzard(Type.ICE, 110, 70);
        ShadowBall ShadowBall = new ShadowBall(Type.GHOST, 80, 100);
        Facade Facade = new Facade(Type.NORMAL, 70, 100);
        super.setMove(Blizzard, ShadowBall, Facade);
    }
}
