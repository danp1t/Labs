package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает при нахождении элементов коллекции по их ID
 */
public class NotCollectionIDFound extends Exception{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String sendMessage(){
        return "ID коллекции не был найден :(";
    }
}
