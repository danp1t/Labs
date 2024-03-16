package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает при вводе поля name
 * Поле name не должно равняется пустой строке
 */
public class EmptyStringFieldException extends Exception{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String sendMessage(){
        return "Значение этого поля не может равнятся пустой строке, введите значение поля заново";

    }
}
