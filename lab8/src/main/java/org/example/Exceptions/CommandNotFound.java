package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которая может возникнуть при вводе
 * Исключение возникает, когда команда не была найдена в списке команд
 */
public class CommandNotFound extends NullPointerException{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String sendMessage(){
        return "Команда не найдена.";
    }
}
