package org.example.Exceptions;

public class InputFromFIleException extends Exception{
    public String send_message(){
        return "Возникла ошибка при чтении строки из файла";
    }
}
