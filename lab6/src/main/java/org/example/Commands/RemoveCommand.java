package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Exceptions.NotPositiveField;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.nio.ByteBuffer;
import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.setByteBuffer;

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
    public void execute(String[] tokens) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //Получить id
        try {
            CollectionManager collectionManager = getCollectionManager();
            //Проверка на то, что у нас один аргумент
            if (tokens.length != 2) throw new InputUserException();
            int id = Integer.parseInt(tokens[1]);
            //Проверить, что он положительный и является целочисленным числом
            if (id <= 0) throw new NotPositiveField();

            //Получение текущей коллекции
            HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
            buffer.put(("Удалить элемент из коллекции по id: " + id + "\n").getBytes());
            HashSet<StudyGroup> newStudyGroups = new HashSet<>();

            for (StudyGroup group : studyGroups) {
                if (id == group.getID()) {
                    buffer.put(("Коллекция с id: " + id + " удалена.").getBytes());
                } else {
                    newStudyGroups.add(group);
                    }
                }
            if (newStudyGroups.size() == studyGroups.size()) throw new NotCollectionIDFound();
            collectionManager.setStudyGroups(newStudyGroups);


        }
        catch (InputUserException e) {
            buffer.put("Неверно введен аргумент для команды remove_by_id".getBytes());
        }
        catch (NotPositiveField | NumberFormatException e){
            buffer.put("Аргументом этой строки должно быть положительное целое число, которое больше 0".getBytes());
        }
        catch (NotCollectionIDFound e) {
            buffer.put(e.sendMessage().getBytes());
        }
        setByteBuffer(buffer);
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
