package classes;
public abstract class Thing {
    private String name;
    protected boolean is_swimming;
    public Thing(String name, boolean is_swimming){
        this.name = name;
        this.is_swimming = is_swimming;
    }
    public String getName(){return this.name;};

    public abstract boolean getIsSwimming();

    public static String isFurnishings(Thing... things){
        boolean flag = false;
        int count = things.length;
        
        if (count > 10){
            return "Вещей хватит для меблировки гостинной";
        }
        else {return "Вещей не хватит для меблировки гостинной";}
    }


}
