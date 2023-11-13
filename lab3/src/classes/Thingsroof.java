package classes;

import java.util.Arrays;

public class Thingsroof extends Things{
    public Thingsroof(String name, boolean is_swimming){
        super(name);
    }
    public static String toString(Thingsroof... thingsroofs){
        String[] names = new String[thingsroofs.length];
        for (int i=0; i < thingsroofs.length; i++){
            String name = thingsroofs[i].get_name();
            names[i] = name;
        }
        return Arrays.toString(names);
    }
    public boolean get_Is_swimming(){
        return false;
    }
}
