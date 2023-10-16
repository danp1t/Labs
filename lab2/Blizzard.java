import ru.ifmo.se.pokemon.*;

public class Blizzard extends SpecialMove {
    public Blizzard(Type type, double pow, double acc){
        super(Type.ICE, pow, acc);
    }
    protected String describe(){
        return "Использует Blizzard";
    }

    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random()*100<=10) {
            Effect.freeze(pokemon);
        }
}
}
