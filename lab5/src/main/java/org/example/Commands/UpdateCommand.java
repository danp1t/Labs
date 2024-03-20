package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Interface.Command;

import java.util.HashSet;

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
    public void execute(String[] tokens) {
        try {
            HashSet<StudyGroup> studyGroups = getStudyGroups();
            if (studyGroups == null) {
                getHashSet();
                studyGroups = getStudyGroups();}
            String arg = historyList.getLast().split(" ")[1];
            int number = 1;
            number = Integer.parseInt(arg);
            HashSet<StudyGroup> newStudyGroups = new HashSet<>();
            for (StudyGroup group : studyGroups) {
                if (number != group.getID()) {
                    newStudyGroups.add(group);
                }
            }
            if (newStudyGroups.size() == studyGroups.size()) {
                throw new NotCollectionIDFound();
            }
            setStudyGroups(newStudyGroups);
            studyGroups = getStudyGroups();
            studyGroups.add(getElement());
            setStudyGroups(studyGroups);
            System.out.println("Коллекция обновлена");


        }
        catch (NumberFormatException e){
            setStatusCommand(-1);
        }
        catch (NotCollectionIDFound e){
            setStatusCommand(-1);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Введите ID элемента");
            setStatusCommand(-1);
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
    public String getNameCommand() {
        return "update id {element}";
    }
}

