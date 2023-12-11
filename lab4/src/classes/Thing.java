package classes;
public abstract class Thing {
    private String name;
    private int size;
    protected boolean is_swimming;
    public Thing(String name, boolean is_swimming, int size){
        this.name = name;
        this.is_swimming = is_swimming;
        this.size = size;
    }
    public String getName(){return this.name;};

    public abstract boolean getIsSwimming();

    public static int sizeThings(Thing... things){
        int final_size = 0;
        for (Thing th : things){
            final_size = th.size + final_size;
        }
        
        return final_size;
    }



}
