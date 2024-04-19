package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.InputUserException;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Server.Server;

import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.HashSet;

import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Server.ServerResponds.byteBufferArrayList;

/**
 * Данный класс реализует команду info
 * Команда info выводит информацию о коллекции(тип коллекции, дата инициализации, количество элементов, является ли множество пустым)
 * Данный класс реализует интерфейс Command
 */
public class InfoCommand implements Command {
    /**
     * Метод исполнение команды
     */
    @Override
    public void execute(String name, String arg, StudyGroup element) {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        buffer.put("Информация о коллекции\n".getBytes());
        buffer.put(printInfoHashSet().getBytes());

        byteBufferArrayList.add(buffer);
        buffer.clear();
    }


    /**
     * Вспомогательный метод для команды info
     * @return строку для команды info
     */
    private String printInfoHashSet(){
        CollectionManager collectionManager = getCollectionManager();
        HashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        return "Тип: " + studyGroups.getClass() + "\n" +
                "Дата инициализации: " + collectionManager.getCreateDateHashSet() + "\n" +
                "Количество элементов: " + studyGroups.size() + "\n" +
                "Множество пустое: " + studyGroups.isEmpty();
    }
    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "info";
    }


}
