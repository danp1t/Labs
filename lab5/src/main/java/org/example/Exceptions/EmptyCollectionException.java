package org.example.Exceptions;

public class EmptyCollectionException extends Exception{
    public String send_message(){
        return "Действие не может быть выполнено, потому что коллекция пустая";
    }
}
