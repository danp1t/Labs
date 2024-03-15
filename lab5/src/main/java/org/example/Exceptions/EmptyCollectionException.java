package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает, когда коллекция HashSet пуста
 * Но команда предполагает, что коллекция содержит хотя бы один элемент
 */
public class EmptyCollectionException extends Exception{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String send_message(){
        return "Действие не может быть выполнено, потому что коллекция пустая";
    }
}
