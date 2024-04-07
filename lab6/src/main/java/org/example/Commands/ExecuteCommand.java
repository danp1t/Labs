package org.example.Commands;

import org.example.Exceptions.*;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.example.Managers.ElementManager.*;
import static org.example.Managers.StartManager.getCommandManager;

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
    public void execute(String[] tokens) {

        try {
            if (getIsUserInput()) {
                recursionDepth = 0;
            }
            //Проверка на то, что у нас один аргумент у команды
            if (tokens.length != 2) throw new InputUserException();
            //Получение аргумента
            String fileName = tokens[1];

            CommandManager commands = getCommandManager();
            System.out.println("Считать и исполнить скрипт из файла: " + fileName);
            //Найти такой файл
            fileName = System.getenv("FILE_DIR_LAB5") + fileName;

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
                        System.out.println("Исполнение скрипта аварийно завершено!");
                        break;
                    }
                    tokens = line.split(" ");
                    setScanner(scan);
                    setIsUserInput(false);
                    commands.addCommandToHistory(command);
                    command.execute(tokens);
                }
            setIsUserInput(true);
            } catch (RecursionLimitException e) {
                System.out.println(e.sendMessage());
            } catch (IOException e) {
                System.out.println("Ошибка чтения из файла или файл не был найден");
            } catch (CommandNotFound e) {
                System.out.println(e.sendMessage());
            }

        }
        catch (InputUserException e) {
            System.out.println("Неверно введены аргументы для команды execute_script");
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
