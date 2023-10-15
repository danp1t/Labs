import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest(Type type, double pow, double acc){
        super(Type.PSYCHIC, pow, acc);
    }
    protected String describe(){
        return "Использует Rest";
    }


    protected void applySelfEffects(Pokemon pokemon) {
        Effect effect = new Effect().condition(Status.SLEEP).turns(2);
        pokemon.setCondition(effect);
        pokemon.restore();
    }
}