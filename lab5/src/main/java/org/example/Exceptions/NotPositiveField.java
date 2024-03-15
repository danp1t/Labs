package org.example.Exceptions;

/**
 * Данный класс описывает исключение, которое возникает при вводе количества студентов в группе и средней оценки в группе
 */
public class NotPositiveField extends Exception{
    /**
     * Метод отправляет сообщение об ошибке
     * @return сообщение об ошибке
     */
    public String send_message(){
        return "Значение этого поля должно быть положительным число. Попробуйте снова";
    }
}
