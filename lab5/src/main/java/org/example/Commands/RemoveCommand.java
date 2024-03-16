package org.example.Commands;

import org.example.Collections.StudyGroup;
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
        HashSet<StudyGroup> studyGroups = getStudyGroups();
        if (studyGroups == null) {
            getHashSet();
            studyGroups = getStudyGroups();}
        System.out.println("Удалить элемент из коллекции");
        int number = 1;
        try {
            if (historyList.getLast().split(" ").length <= 1){
                throw new NotCollectionIDFound();
            }
            String arg = historyList.getLast().split(" ")[1];
            number = Integer.parseInt(arg);
            HashSet<StudyGroup> newStudyGroups = new HashSet<>();

            for (StudyGroup group : studyGroups) {
                if (number == group.getID()) {
                    System.out.println("Коллекция с id: " + number + " удалена.");
                }
                else {
                    newStudyGroups.add(group);
                }
            }
            if (newStudyGroups.size() == studyGroups.size()) {
                throw new NotCollectionIDFound();
            }
            setStudyGroups(newStudyGroups);

        }
        catch (NumberFormatException e){
            System.out.println("Для коллекции введен не целочисленный id. Введите команду еще раз");
            setStatusCommand(-1);
        }
        catch (NotCollectionIDFound e){
            System.out.println(e.sendMessage());
            setStatusCommand(-1);
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
    public String getNameCommand() {
        return "remove_by_id id";
    }
}
