package org.example.Commands;

import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import static org.example.Managers.CollectionManager.save_hashSet_to_file;
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
    public void execute() {
        System.out.println("Сохранить коллекцию в файл");
        save_hashSet_to_file();
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
    public String get_name_command() {
        return "save";
    }
}
