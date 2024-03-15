package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает, когда превышен лимит рекурсии
 */
public class RecursionLimitException extends Exception{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String send_message(){
        return "ПРЕРЫВАНИЕ!!! Превышен лимит рекурсии. Программа аварийно завершена";
    }
}
