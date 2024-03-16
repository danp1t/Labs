package org.example.Commands;

import org.example.Collections.Person;
import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;

import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.history_list;
/**
 * Данный класс реализует команду filter_contains_name
 * Команда filter_contains_name выводит элементы, значение поля name которых содержит заданную подстроку
 * Данный класс реализует интерфейс Command
 */
public class FilterContainsNameCommand implements Command {
    /**
     * Метод выполнение команды
     * 1. Считываем подстроку
     * 2. Смотрим поля name у каждого элемента
     * 3. Выводим результат
     */
    @Override
    public void execute() {
        HashSet<StudyGroup> studyGroups = get_study_groups();
        System.out.println("Фильтр...");
        try{
        if (studyGroups == null) {get_HashSet();
            studyGroups = get_study_groups();}
        boolean flag = false;
        if (history_list.getLast().split(" ").length < 2){
            throw new InputUserException();
        }
        String filter_name = history_list.getLast().split(" ")[1];

        for (StudyGroup group : studyGroups){
            String name = group.getName();
            Person admin = group.getGroupAdmin();
            String admin_name = admin.getName();
            if (name.contains(filter_name) || admin_name.contains(filter_name)) {
                System.out.println(beatiful_output_element_json(parse_studyGroup_to_json(group)));
                flag = true;
            }

        }
        if (!flag) {
            System.out.println("Ничего с таким именем не было найдено");
        }
        }catch (InputUserException e){
            System.out.println("Введите параметр");
        }

    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит элементы, значение поля name которых содержит заданную подстроку";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String get_name_command() {
        return "filter_contains_name name";
    }
}

