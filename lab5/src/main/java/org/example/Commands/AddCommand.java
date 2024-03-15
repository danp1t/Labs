package org.example.Commands;

import org.example.Interface.Command;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.element;

/**
 * Данный класс реализует команду add
 * Команда add добавляет учебную группу в коллекцию HashSet
 * Данный класс реализует интерфейс Command
 */
public class AddCommand implements Command {
    /**
     * Метод исполнения команды
     */
    @Override
    public void execute() {
        HashSet studyGroup = get_study_groups();
        System.out.println("Группа добавлена!");
        studyGroup.add(element);
        set_study_groups(studyGroup);
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "добавляет новый элемент в коллекцию";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String get_name_command() {
        return "add {element}";
    }
}
