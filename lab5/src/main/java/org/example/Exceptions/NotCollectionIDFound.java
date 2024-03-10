package org.example.Exceptions;

public class NotCollectionIDFound extends Exception{
    public String send_message(){
        return "ID коллекции не был найден :(";
    }
}
