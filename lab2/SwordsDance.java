import ru.ifmo.se.pokemon.*;

public class SwordsDance extends StatusMove {
    public SwordsDance(Type type, double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }
    protected String describe(){
        return "Использует Swords Dance";
    }
    protected void applySelfEffects(Pokemon pokemon){
        pokemon.setMod(Stat.ATTACK, +2);
    }
}
