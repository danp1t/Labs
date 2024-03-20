package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.CommandNotFound;
import org.example.Exceptions.InputUserException;
import org.example.Exceptions.NullFieldException;
import org.example.Exceptions.RecursionLimitException;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;

/**
 * Данный класс реализует команду execute_script
 * Команда execute_script исполняет скрипт из указанного файла
 * Данный класс реализует интерфейс Command
 */
public class ExecuteCommand implements Command {
    /**
     * Номер строки последней исполненной команды
     */
    private static int counterLine;
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
    public void execute(String[] tokens) {
        CommandManager commands = new CommandManager();
        System.out.println("Считать и исполнить скрипт из файла");
        //Найти такой файл
        HashSet<StudyGroup> studyGroups = getStudyGroups();
        if (studyGroups == null) {
            getHashSet();
            studyGroups = getStudyGroups();}

        String fileName = null;
        try {
        if (historyList.getLast().split(" ").length < 2) {
            throw new InputUserException();}
            fileName = historyList.getLast().split(" ")[1];
        }
        catch (InputUserException e) {
            System.out.println("Введите параметр");
        }
        String fileDir = System.getenv("FILE_DIR_LAB5");;
        fileName = fileDir + fileName;
        try (FileReader fr = new FileReader(fileName)){
            recursionDepth += 1;
            if (recursionDepth > MAX_DEPTH) {
                throw new RecursionLimitException();
            }
            Scanner scan = new Scanner(fr);
            counterLine = 1;
            while (scan.hasNext()) {
                String line = scan.nextLine();
                if (line.equals("")) {line = scan.nextLine();}
                String strCommand = line.strip().split(" ")[0];
                Command command = commands.getCommands().get(strCommand);
                if (command == null) {
                    System.out.println("Исполнение скрипта аварийно завершено!");
                    System.out.println("На " + (counterLine - 1) + " строчке найдена неверная команда или строка");
                    break;
                }
                else if (commands.isSimpleCommand(strCommand)) {
                    if (line.strip().split(" ").length > 1) {
                        setStatusCommand(-1);
                        }
                    commands.addCommandToHistory(commands.getCommands().get(strCommand));
                    }
                else if (commands.isCommandWithOneArg(strCommand)) {
                    if (line.strip().split(" ").length > 2) {
                        setStatusCommand(-1);
                    }
                    commands.addCommandToHistory(line);
                    }
                else if (commands.isCommandWithElement(strCommand)) {
                    if (line.strip().split(" ").length > 1) {
                        setStatusCommand(-1);
                        }
                    commands.processingElement(commands.getCommands().get(strCommand), scan, false);
                    }

                else if (commands.isCommandWithElementAndOneArg(strCommand)) {
                    if (line.strip().split(" ").length > 2) {
                        setStatusCommand(-1);
                        }
                    commands.addCommandToHistory(line);
                    getGroupElement();
                    if (gettingGroupElement() == null) {throw new NullFieldException();}
                    commands.updateFunction(scan, false);
                    }

                if (getStatusCommand() == -1){
                    System.out.println("ПРЕРЫВАНИЕ! Последняя команда сгенерировала ошибку");
                    System.out.println("Команда, которая сгенерировала исключение:: " + strCommand);
                    break;
                }
                command.execute(tokens);
                counterLine += 1;
            }

        }
        catch (RecursionLimitException e){
            System.out.println(e.sendMessage());
        }
        catch (IOException e) {
            System.out.println("Ошибка чтения из файла или файл не был найден");
        }
        catch (CommandNotFound e){
            System.out.println(e.sendMessage());
        }
        catch (NullFieldException e) {
            System.out.println("ПРЕРЫВАНИЕ! Последняя команда сгенерировала ошибку");
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
