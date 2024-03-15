package org.example.Exceptions;

public class RecursionLimitException extends Exception{
    public String send_message(){
        return "ПРЕРЫВАНИЕ!!! Превышен лимит рекурсии. Программа аварийно завершена";
    }
}
