package org.example.Interface;

import org.example.Collections.StudyGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Интерфейс Command
 */
public interface Command  {
    /**
     * Абстрактный метод исполнения команды
     * @throws FileNotFoundException
     */
    void execute(String name, String arg, StudyGroup element, String login) throws IOException, SQLException;

    /**
     * Абстрактный метод получение описания команды
     * @return описание команды
     */
    String description();

    /**
     * Абстрактный метод получение названия и синтаксиса команды
     * @return название и синтаксист команды
     */
    String getNameCommand();
}
