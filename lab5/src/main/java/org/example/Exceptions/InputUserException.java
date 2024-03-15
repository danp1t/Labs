package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает при неверном пользовательском вводе данных
 */
public class InputUserException extends Exception{
    public String send_message(){
        return "Произошла ошибка при вводе данных";
    }
}
