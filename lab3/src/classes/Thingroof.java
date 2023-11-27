package classes;

import java.util.Arrays;

public class Thingroof extends Thing {
    public Thingroof(String name, boolean is_swimming){
        super(name, is_swimming);
    }
    public static String toString(Thingroof... thingsroofs){
        String[] names = new String[thingsroofs.length];
        for (int i=0; i < thingsroofs.length; i++){
            String name = thingsroofs[i].getName();
            names[i] = name;
        }
        return Arrays.toString(names);
    }
    public boolean getIsSwimming(){
        return false;
    }
}
