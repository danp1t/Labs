package org.example.Exceptions;

public class CommandNotFound extends NullPointerException{

    public String send_message(){
        return "Команда не найдена. Введите команду снова: ";
    }
}
