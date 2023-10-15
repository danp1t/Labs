import ru.ifmo.se.pokemon.*;

import static ru.ifmo.se.pokemon.Stat.SPEED;

public class RockTomb extends PhysicalMove {
    public RockTomb(Type type, double pow, double acc){
        super(Type.ROCK, pow, acc);
    }
    protected String describe(){
        return "Использует Rock Tomb";
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPEED, -1);
    }
}
