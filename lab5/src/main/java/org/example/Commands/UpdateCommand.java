package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Interface.Command;

import java.util.HashSet;
import java.util.Objects;

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
            if (tokens.length != 2) throw new InputUserException();
            HashSet<StudyGroup> studyGroups = getStudyGroups();
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
            setStudyGroups(newStudyGroups);
            HashSet<StudyGroup> studyGroups1 = getStudyGroups();
            StudyGroup element = createStudyGroup(getIsUserInput());
            if (element != null) {studyGroups1.add(element);}

            if (studyGroups.size() != studyGroups1.size()) {
                setStudyGroups(studyGroups);
            }
            else {setStudyGroups(studyGroups1);}
            System.out.println("Коллекция обновлена");


        }
        catch (NumberFormatException e){
            System.out.println("Аргумент должен быть положительным целым числом большим 0");
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

