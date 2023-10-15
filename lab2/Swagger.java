import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {
    public Swagger(Type type, double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }

    protected String describe() {
        return "Использует Swagger";
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, +2);
        Effect.confuse(pokemon);
    }
}
