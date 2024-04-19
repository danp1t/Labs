package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.ElementManager;

import java.nio.ByteBuffer;
import java.util.HashSet;

import static org.example.Managers.ElementManager.*;
import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

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
    public void execute(String name, String arg, StudyGroup element123) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            CollectionManager collectionManager = getCollectionManager();
            HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
            int number = Integer.parseInt(arg);
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
            HashSet<StudyGroup> studyGroups1 = collectionManager.getStudyGroups();;
            int id = elementManager.nextID();
            StudyGroup element = elementManager.createStudyGroup(id, element123);
            if (element != null) {studyGroups1.add(element);}

            if (studyGroups.size() != studyGroups1.size()) {
                collectionManager.setStudyGroups(studyGroups);
            }
            else {collectionManager.setStudyGroups(studyGroups1);}
            buffer.put("Коллекция обновлена\n".getBytes());
        }
        catch (NumberFormatException e){
            buffer.put("Аргумент должен быть положительным целым числом большим 0".getBytes());
        }
        catch (NotCollectionIDFound e){
            buffer.put("ID коллекции не найден".getBytes());
        }
        catch (ArrayIndexOutOfBoundsException e){
            buffer.put("Введите ID элемента\n".getBytes());
        }
        catch (NullPointerException e) {
            buffer.put("Ошибочка вышла".getBytes());
        }

        byteBufferArrayList.add(buffer);
        buffer.clear();
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

