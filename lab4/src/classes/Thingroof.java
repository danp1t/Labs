package classes;

import java.util.Arrays;

public class Thingroof extends Thing {
    public Thingroof(String name, boolean is_swimming, int size){
        super(name, is_swimming, size);
    }
    public static String toString(Thingroof... thingsroofs) throws IndexOutException{
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
