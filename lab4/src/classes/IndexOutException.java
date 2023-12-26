package classes;

public class IndexOutException extends RuntimeException{
    public String sendMessage(){
        return "Выход за границы массива";
    }
}
