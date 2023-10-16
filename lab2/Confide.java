import ru.ifmo.se.pokemon.*;
public class Confide extends StatusMove {
    public Confide() {
        type = Type.NORMAL;

        }
    protected String describe(){
        return "Использует Confide";
    }
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.SPECIAL_ATTACK, -1);
    }
}
