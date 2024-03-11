package org.example.Exceptions;

public class NullFieldException extends Exception{
    public String send_message(){
        return "Значение этого поля не должно равнятся Null";
    }
}
