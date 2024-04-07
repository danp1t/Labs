package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает при вводе значений Enum
 */
public class NotFoundEnum extends Exception{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String sendMessage(){
        return "Enum не был найден. Просто скопируйте значение из сообщения ниже";
    }
}
