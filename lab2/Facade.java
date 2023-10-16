import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade(Type type, double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }
    protected String describe(){
        return "Использует Facade";
    }

    protected void applySelfEffects(Pokemon pokemon, double pow) {
        if (pokemon.getCondition()==Status.PARALYZE || pokemon.getCondition()==Status.BURN || pokemon.getCondition()==Status.POISON)
            pokemon.setMod(Stat.HP, (int) (pow * 2));

    }
}
