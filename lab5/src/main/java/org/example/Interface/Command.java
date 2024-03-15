package org.example.Interface;

import java.io.FileNotFoundException;

/**
 * Интерфейс Command
 */
public interface Command {
    /**
     * Абстрактный метод исполнения команды
     * @throws FileNotFoundException
     */
    void execute() throws FileNotFoundException;

    /**
     * Абстрактный метод получение описания команды
     * @return описание команды
     */
    String description();

    /**
     * Абстрактный метод получение названия и синтаксиса команды
     * @return название и синтаксист команды
     */
    String get_name_command();
}
