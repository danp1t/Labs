package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает при вводе данных полей, которые не должны равняться null
 */
public class NullFieldException extends Exception{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String send_message(){
        return "Значение этого поля не должно равняться Null";
    }
}
