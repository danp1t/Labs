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
import static org.example.Server.ServerResponds.setByteBuffer;

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
    public void execute(String[] tokens) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            buffer.put("Информация о коллекции\n".getBytes());
            if (tokens.length != 1) throw new InputUserException();
            buffer.put(printInfoHashSet().getBytes());

        }
        catch (InputUserException e) {
            buffer.put("Команда info не должна содержать аргументов".getBytes());
        }
        setByteBuffer(buffer);
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
