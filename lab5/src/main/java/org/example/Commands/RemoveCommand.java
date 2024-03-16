package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Interface.Command;
import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;

/**
 * Данный класс реализует команду remove_by_id
 * Команда remove_by_id удаляет элемент из коллекции по его id
 * Данный класс реализует интерфейс Command
 */
public class RemoveCommand implements Command {
    /**
     * Метод исполнение команды
     * 1. Считываем id объекта
     * 2. Ищем объект по его id
     * 3. Удаляем объект
     */
    @Override
    public void execute() {
        HashSet<StudyGroup> studyGroups = get_study_groups();
        if (studyGroups == null) {get_HashSet();
            studyGroups = get_study_groups();}
        System.out.println("Удалить элемент из коллекции");
        int number = 1;
        try {
            if (history_list.getLast().split(" ").length <= 1){
                throw new NotCollectionIDFound();
            }
            String arg = history_list.getLast().split(" ")[1];
            number = Integer.parseInt(arg);
            HashSet<StudyGroup> new_study_groups = new HashSet<>();

            for (StudyGroup group : studyGroups) {
                if (number == group.getID()) {
                    System.out.println("Коллекция с id: " + number + " удалена.");
                }
                else {
                    new_study_groups.add(group);
                }
            }
            if (new_study_groups.size() == studyGroups.size()) {
                throw new NotCollectionIDFound();
            }
            set_study_groups(new_study_groups);

        }
        catch (NumberFormatException e){
            System.out.println("Для коллекции введен не целочисленный id. Введите команду еще раз");
            set_status_command(-1);
        }
        catch (NotCollectionIDFound e){
            System.out.println(e.send_message());
            set_status_command(-1);
        }
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "удаляет элемент из коллекции по его id";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String get_name_command() {
        return "remove_by_id id";
    }
}
