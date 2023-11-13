package classes;
public abstract class Things {
    private String name;
    protected boolean is_swimming;
    public Things(String name, boolean is_swimming){
        this.name = name;
        this.is_swimming = is_swimming;
    }
    public String get_name(){return this.name;};

    public abstract boolean get_Is_swimming();

}
