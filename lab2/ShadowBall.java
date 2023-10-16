import ru.ifmo.se.pokemon.*;

public class ShadowBall extends SpecialMove {
    public ShadowBall(Type type, double pow, double acc){
        super(Type.GHOST, pow, acc);
    }
    protected String describe(){
        return "Использует Shadow Ball";
    }

    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random()*100<=20) {
            pokemon.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }
}
