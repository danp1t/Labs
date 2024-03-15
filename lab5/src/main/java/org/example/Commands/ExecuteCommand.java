package org.example.Commands;

import org.example.Exceptions.CommandNotFound;
import org.example.Exceptions.NullFieldException;
import org.example.Exceptions.RecursionLimitException;
import org.example.Interface.Command;
import org.example.Managers.CommandManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;

public class ExecuteCommand implements Command {
    public static int counter_line;
    private static final int MAX_DEPTH = 1000;
    private static int recursionDepth = 0;
    @Override
    public void execute() {
        CommandManager commands = new CommandManager();
        System.out.println("Считать и исполнить скрипт из файла");
        //Найти такой файл
        if (study_groups == null) {get_HashSet();}
        String file_name = history_list.getLast().split(" ")[1];
        String file_dir = System.getenv("FILE_DIR_LAB5");;
        String fileName = file_dir + file_name;
        try {
            recursionDepth += 1;
            if (recursionDepth > MAX_DEPTH) {
                throw new RecursionLimitException();
            }
            FileReader fr = new FileReader(fileName);
            Scanner scan = new Scanner(fr);
            counter_line = 1;
            while (scan.hasNext()) {
                String line = scan.nextLine();
                if (line.equals("")) {line = scan.nextLine();}
                String str_command = line.strip().split(" ")[0];
                Command command = commands.get_commands().get(str_command);
                if (command == null) {
                    System.out.println("Исполнение скрипта аварийно завершено!");
                    System.out.println("На " + (counter_line - 1) + " строчке найдена неверная команда или строка");
                    break;
                }
                else if (commands.is_simple_command(str_command)) {
                    if (line.strip().split(" ").length > 1) {
                        status_command =-1;
                        }
                    commands.add_command_in_history(commands.get_commands().get(str_command));
                    }
                else if (commands.is_command_with_one_arg(str_command)) {
                    if (line.strip().split(" ").length > 2) {
                        status_command =-1;
                    }
                    commands.add_command_in_history(line);
                    }
                else if (commands.is_command_with_element(str_command)) {
                    if (line.strip().split(" ").length > 1) {
                        status_command =-1;
                        }
                    commands.processing_element(commands.get_commands().get(str_command), scan, false);
                    }

                else if (commands.is_command_with_element_and_one_arg(str_command)) {
                    if (line.strip().split(" ").length > 2) {
                        status_command =-1;
                        }
                    commands.add_command_in_history(line);
                    get_group_element();
                    if (group_element == null) {throw new NullFieldException();}
                    commands.update_function(scan, false);
                    }

                if (status_command == -1){
                    System.out.println("ПРЕРЫВАНИЕ! Последняя команда сгенерировала ошибку");
                    System.out.println("На " + (counter_line + counter_input) + " строчке находится команда, которая сгенерировала исключение:: " + str_command);
                    break;
                }
                command.execute();
                counter_line += 1;
            }
            fr.close();

        }
        catch (RecursionLimitException e){
            System.out.println(e.send_message());
        }
        catch (IOException e) {
            System.out.println("Ошибка чтения из файла или файл не был найден");
        }
        catch (CommandNotFound e){
            System.out.println(e.send_message());
        }
        catch (NullFieldException e) {
            System.out.println("ПРЕРЫВАНИЕ! Последняя команда сгенерировала ошибку");
        }


    }

    @Override
    public String description() {
        return "считывает и исполняет скрипт из указанного файла";
    }

    @Override
    public String get_name_command() {
        return "execute_script file_name";
    }
}
