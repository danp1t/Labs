package org.example.Exceptions;

import java.io.IOException;

/**
 * Данный класс описывает исключение, которое возникает при вводе пользователем комбинации клавиш Ctrl+D
 * Данная комбинация клавиш ломает программу
 */
public class CtrlDException extends IOException {
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String sendMessage(){
        return "Ай-ай-ай, как не стыдно ломать программу...";
    }
}
