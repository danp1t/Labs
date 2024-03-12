package org.example.Exceptions;

public class EmptyStringFieldException extends Exception{
    public String send_message(){
        return "Значение этого поля не может равнятся пустой строке, введите значение поля заново";

    }
}
