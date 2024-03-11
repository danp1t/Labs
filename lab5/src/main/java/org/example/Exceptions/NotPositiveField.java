package org.example.Exceptions;

public class NotPositiveField extends Exception{
    public String send_message(){
        return "Значение этого поля должно быть положительным число. Попробуйте снова";
    }
}
