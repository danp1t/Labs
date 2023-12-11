package classes;

public class SizeHouseException extends Exception{
    public String sendMessage(){
        return "Размер предмета слишком большой";
    }
}
