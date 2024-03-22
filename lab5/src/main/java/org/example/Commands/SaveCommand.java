package org.example.Commands;

import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;

import java.io.FileWriter;
import java.io.IOException;

import static org.example.Managers.CollectionManager.beatifulOutputJson;

/**
 * Данный класс реализует команду save
 * Команда save сохраняет коллекцию в файл
 * Данный класс реализует интерфейс Command
 */
public class SaveCommand implements Command {
    /**
     * Метод исполнение команды
     */
    @Override
    public void execute(String[] tokens) {
        try {
            if (tokens.length != 1) throw new InputUserException();
            System.out.println("Сохранить коллекцию в файл");
            saveHashSetToFile();
        }
        catch (InputUserException e) {
            System.out.println("Команда save не должна содержать аргументов");
        }
    }

    /**
     * Данный метод сохраняет нашу коллекцию в файл
     */
    public static void saveHashSetToFile() {
        String jsonString = beatifulOutputJson();
        String pathJson = System.getenv("JSON_FILE_LAB5");
        try(FileWriter fileWriter = new FileWriter(pathJson)) {
            fileWriter.write(jsonString);
        }
        catch (IOException e){
            System.out.println("Ошибка при сохранении коллекции в файл");
        }
    }
    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "сохраняет коллекцию в файл";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "save";
    }
}
