package org.example.Commands;

import org.example.Collections.Semester;
import org.example.Collections.StudyGroup;
import org.example.Exceptions.EmptyCollectionException;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;

import java.nio.ByteBuffer;
import java.util.HashSet;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.setByteBuffer;

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
        setByteBuffer(buffer);
    }
    /**
     * Вывод любого объекта из коллекции, значение поля semesterEnum которого является минимальным
     * Вспомогательный метод для команды min_by_semester_enum
     * @return объект из коллекции, значение поля semesterEnum которого является минимальным
     */
    private String printMinBySemesterEnum() {
        CollectionManager collectionManager = getCollectionManager();
        HashSet<StudyGroup> hashSet = collectionManager.getStudyGroups();
        Semester minSemester = Semester.SIXTH;
        StudyGroup minGroup = null;

        try {
            if (hashSet.isEmpty()) {
                throw new EmptyCollectionException();
            }
            for (StudyGroup group : hashSet) {
                Semester groupSemester = group.getSemesterEnum();

                if (groupSemester == Semester.SECOND) {
                    minGroup = group;
                    break;
                } else if (minSemester == Semester.SIXTH && groupSemester == Semester.FIFTH) {
                    minSemester = groupSemester;
                    minGroup = group;
                } else if (minSemester == Semester.SIXTH && minGroup == null) {
                    minGroup = group;
                }
            }
            return collectionManager.beatifulOutputElementJson(collectionManager.parseStudyGroupToJson(minGroup));

        } catch (EmptyCollectionException e) {
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
