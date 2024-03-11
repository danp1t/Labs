package org.example.Exceptions;

public class NotFoundEnum extends Exception{
    public String send_message(){
        return "Enum не был найден. Вводите значение с заглавной буквы";
    }
}
