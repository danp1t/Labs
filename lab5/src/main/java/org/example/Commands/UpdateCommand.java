package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Interface.Command;

import java.util.HashSet;
import java.util.Scanner;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;

/**
 * Данный класс реализует команду update
 * Команда update обновляет значение элемента коллекции по id
 * Данный класс реализует интерфейс Command
 */
public class UpdateCommand implements Command {
    /**
     * Метод исполнение команды
     * 1. Считываем id
     * 2. Ищем по id элемент коллекции
     * 3. Обновляем коллекцию
     */
    @Override
    public void execute() {
        try {
            HashSet<StudyGroup> studyGroups = get_study_groups();
            if (studyGroups == null) {get_HashSet();
                studyGroups = get_study_groups();}
            String arg = history_list.getLast().split(" ")[1];
            int number = 1;
            number = Integer.parseInt(arg);
            HashSet<StudyGroup> new_study_groups = new HashSet<>();
            for (StudyGroup group : studyGroups) {
                if (number != group.getID()) {
                    new_study_groups.add(group);
                }
            }
            if (new_study_groups.size() == studyGroups.size()) {
                throw new NotCollectionIDFound();
            }
            set_study_groups(new_study_groups);
            studyGroups = get_study_groups();
            studyGroups.add(element);
            set_study_groups(studyGroups);
            System.out.println("Коллекция обновлена");


        }
        catch (NumberFormatException e){
            status_command = -1;
        }
        catch (NotCollectionIDFound e){
            status_command = -1;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Введите ID элемента");
            status_command = -1;
        }
        catch (NullPointerException e) {
            System.out.println("Ошибочка вышла");
        }
    }


    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "обновляет значение элемента коллекции, id которого равен заданному";
    }
    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String get_name_command() {
        return "update id {element}";
    }
}

