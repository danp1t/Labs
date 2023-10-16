import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class Pound extends PhysicalMove {
    public Pound(Type type, double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }
    protected String describe(){
        return "Использует Pound";
    }

}
