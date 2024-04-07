package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.ElementManager;

import java.util.HashSet;

import static org.example.Managers.ElementManager.*;
import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;
import static org.example.Managers.StartManager.getCollectionManager;

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
            CollectionManager collectionManager = getCollectionManager();
            if (tokens.length != 2) throw new InputUserException();
            HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
            int number = Integer.parseInt(tokens[1]);
            HashSet<StudyGroup> newStudyGroups = new HashSet<>();
            for (StudyGroup group : studyGroups) {
                if (number != group.getID()) {
                    newStudyGroups.add(group);
                }
            }
            if (newStudyGroups.size() == studyGroups.size()) {
                throw new NotCollectionIDFound();
            }
            collectionManager.setStudyGroups(newStudyGroups);
            ElementManager elementManager = new ElementManager();
            HashSet<StudyGroup> studyGroups1 = collectionManager.getStudyGroups();
            StudyGroup element = elementManager.createStudyGroup(getIsUserInput());
            if (element != null) {studyGroups1.add(element);}

            if (studyGroups.size() != studyGroups1.size()) {
                collectionManager.setStudyGroups(studyGroups);
            }
            else {collectionManager.setStudyGroups(studyGroups1);}
            System.out.println("Коллекция обновлена");


        }
        catch (NumberFormatException e){
            System.out.println("Аргумент должен быть положительным целым числом большим 0");
        }
        catch (NotCollectionIDFound e){
            System.out.println("ID коллекции не найден");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Введите ID элемента");
        }
        catch (NullPointerException e) {
            System.out.println("Ошибочка вышла");
        }
        catch (InputUserException e) {
            System.out.println("Команда update должна содержать один аргумент");
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

