package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает при чтении скрипта из файла
 * Это исключение возникает при неверном наборе команды или поля для коллекции
 */
public class InputFromFIleException extends Exception{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String send_message(){
        return "Возникла ошибка при чтении строки из файла";
    }
}
