import ru.ifmo.se.pokemon.*;

public class Masquerain extends Surskit {
    public Masquerain(String name, int level){
        super(name, level);
        super.setType(Type.BUG, Type.FLYING);
        super.setStats(70, 60, 62, 100, 82, 80);
        Blizzard Blizzard = new Blizzard(Type.ICE, 110, 70);
        ShadowBall ShadowBall = new ShadowBall(Type.GHOST, 80, 100);
        Facade Facade = new Facade(Type.NORMAL, 70, 100);
        StunSpore StunSpore = new StunSpore(Type.GRASS, 0, 75);
        super.setMove(Blizzard, ShadowBall, Facade, StunSpore);

    }
}
