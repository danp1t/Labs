import ru.ifmo.se.pokemon.*;

public class StunSpore extends StatusMove {
    public StunSpore(Type type, double pow, double acc){
        super(Type.GRASS, pow, acc);
    }
    protected String describe(){
        return "Использует Stun Spore";
    }

    protected void applyOppEffects(Pokemon pokemon) {
            Effect.paralyze(pokemon);
        }
}
