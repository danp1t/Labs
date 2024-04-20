package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.*;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;

import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.example.Managers.ElementManager.*;
import static org.example.Managers.StartManager.getCommandManager;
import static org.example.Server.ServerResponds.*;

/**
 * Данный класс реализует команду execute_script
 * Команда execute_script исполняет скрипт из указанного файла
 * Данный класс реализует интерфейс Command
 */
public class ExecuteCommand implements Command {
    /**
     * Так как функции может в скрипте вызывать саму себя, то нужно ограничить глубину рекурсии
     * Поле содержит максимальную глубину рекурсии
     */
    private static final int MAX_DEPTH = 1000;
    /**
     * Текучая глубина рекурсии
     */
    private static int recursionDepth = 0;

    /**
     * Метод выполнения команды
     * 1. Находим нужный файл со скриптом
     * 2. Считываем строку
     * 3. Анализируем тип команды
     * 4. Выполняем команду
     */
    @Override
    public void execute(String name, String arg, StudyGroup element) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
            if (getIsUserInput()) {
                recursionDepth = 0;
            }
            //Проверка на то, что у нас один аргумент у команды
            //Получение аргумента
            String fileName = arg;

            CommandManager commands = getCommandManager();
            buffer.put(("Считать и исполнить скрипт из файла: " + fileName + "\n").getBytes());
            //Найти такой файл
            //fileName = System.getenv("FILE_DIR_LAB5") + fileName;
            fileName = "/home/danp1t/github/Labs/lab6/src/main/java/org/example/Files/" + fileName;
            try (FileReader fr = new FileReader(fileName)) {
                recursionDepth += 1;
                if (recursionDepth > MAX_DEPTH) {
                    throw new RecursionLimitException();
                }
                Scanner scan = new Scanner(fr);
                while (scan.hasNext()) {
                    String line = scan.nextLine();
                    if (line.isEmpty()) {
                        line = scan.nextLine();
                    }
                    String strCommand = line.strip().split(" ")[0];
                    Command command = commands.getCommands().get(strCommand);
                    if (command == null) {
                        buffer.put("Исполнение скрипта аварийно завершено!".getBytes());
                        break;
                    }
                    setScanner(scan);
                    setIsUserInput(false);
                    String args;
                    commands.addCommandToHistory(command);
                    if (line.strip().split(" ").length == 2) {
                        args = line.strip().split(" ")[1];
                    }
                    else {
                        args = null;
                    }
                    command.execute(line.strip().split(" ")[0], args, null);
                }
                byteBufferArrayList.add(buffer);
                buffer.clear();
            } catch (RecursionLimitException e) {
                buffer.put(e.sendMessage().getBytes());
            } catch (IOException e) {
                buffer.put("Ошибка чтения из файла или файл не был найден".getBytes());
            } catch (CommandNotFound e) {
                buffer.put(e.sendMessage().getBytes());
            }





    }

    /**
     * Метод описания действия команды
     * Данное описание используется в команде help
     * @return возвращает описание действия команды
     */
    @Override
    public String description() {
        return "считывает и исполняет скрипт из указанного файла";
    }
    /**
     * Метод, который возвращает название и синтаксис команды
     * Данное название используется в команде help
     * @return возвращает название команды
     */
    @Override
    public String getNameCommand() {
        return "execute_script file_name";
    }
}
