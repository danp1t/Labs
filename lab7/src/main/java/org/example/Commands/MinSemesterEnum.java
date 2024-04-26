package org.example.Commands;

import org.example.Collections.Semester;
import org.example.Collections.StudyGroup;
import org.example.Exceptions.EmptyCollectionException;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Objects;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;


/**
 * Данный класс реализует команду min_by_semester_enum
 * Команда min_by_semester_enum выводит любой объект из коллекции, значение поля semesterEnum которого является минимальным
 * Данный класс реализует интерфейс Command
 */
public class MinSemesterEnum implements Command {
    /**
     * Метод выполнения команды
     */
    @Override
    public void execute(String name, String arg, StudyGroup element) {
        ByteBuffer buffer = ByteBuffer.allocate(4098);

        buffer.put("Вывод любого объекта из коллекции, значение поля semesterEnum которого является минимальным\n".getBytes());
        buffer.put(printMinBySemesterEnum().getBytes());
        byteBufferArrayList.add(buffer);
        buffer.clear();
    }
    /**
     * Вывод любого объекта из коллекции, значение поля semesterEnum которого является минимальным
     * Вспомогательный метод для команды min_by_semester_enum
     * @return объект из коллекции, значение поля semesterEnum которого является минимальным
     */
    private String printMinBySemesterEnum() {
        CollectionManager collectionManager = getCollectionManager();
        HashSet<StudyGroup> hashSet = collectionManager.getStudyGroups();
        try {
        StudyGroup minGroup = hashSet.stream()
                .filter(group -> group.getSemesterEnum() == Semester.SECOND)
                .findFirst()
                .orElseGet(() ->
                        hashSet.stream()
                                .filter(group -> group.getSemesterEnum() == Semester.FIFTH)
                                .findFirst()
                                .orElseGet(() ->
                                        hashSet.stream()
                                                .findFirst()
                                                .orElse(null)
                                )
                );
        if (Objects.isNull(minGroup)) throw new EmptyCollectionException();
        return collectionManager.beatifulOutputElementJson(collectionManager.parseStudyGroupToJson(minGroup));
        }


         catch (EmptyCollectionException e) {
            return e.sendMessage();
        }
    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит любой объект из коллекции, значение поля semesterEnum которого является минимальным";
    }
    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "min_by_semester_enum";
    }
}
