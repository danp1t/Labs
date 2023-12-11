package classes;

public class MaxSizeLimitException extends Exception{
    public String sendMessage(){
        return "Размер дома слишком мал для такого количества вещей";
    }
}
