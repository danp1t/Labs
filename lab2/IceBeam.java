import ru.ifmo.se.pokemon.*;

public class IceBeam extends SpecialMove {
    public IceBeam(Type type, double pow, double acc){
        super(Type.ICE, pow, acc);

    }
    protected String describe(){
        return "Использует Ice Beam";
    }
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random()*100<=10) {
            Effect.freeze(pokemon);
        }
    }
}
