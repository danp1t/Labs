package org.example.Commands;

import org.example.Exceptions.CommandNotFound;
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
    @Override
    public void execute() {

        CommandManager commands = new CommandManager();
        System.out.println("Считать и исполнить скрипт из файла");
        //Найти такой файл
        if (study_groups == null) {get_HashSet();}
        String file_name = history_list.getLast().split(" ")[1];
        String fileName = "/home/danp1t/github/Labs/lab5/src/main/java/org/example/Files/script.txt";
        try {
            FileReader fr = new FileReader(fileName);
            Scanner scan = new Scanner(fr);
            counter_line = 1;
            while (scan.hasNext()) {
                String line = scan.nextLine();
                String str_command = line.split(" ")[0];
                Command command = commands.get_commands().get(str_command);
                if (command == null) {
                    System.out.println("Исполнение скрипта аварийно завершено!");
                    System.out.println("На " + (counter_line - 1) + " строчке найдена неверная команда или строка");
                    break;
                }
                else if (commands.is_simple_command(str_command)) {
                    commands.add_command_in_history(commands.get_commands().get(str_command));
                    command.execute();
                    }
                else if (commands.is_command_with_one_arg(str_command)) {
                    commands.add_command_in_history(line);
                    command.execute();
                    }
                else if (commands.is_command_with_element(str_command)) {
                    commands.processing_element(commands.get_commands().get(str_command), scan, false);
                    command.execute();}

                else if (commands.is_command_with_element_and_one_arg(str_command)) {
                    commands.add_command_in_history(line);
                    get_group_element();
                    commands.update_function(scan, false);
                    command.execute();
                    }

                if (status_command == -1){
                    System.out.println("ПРЕРЫВАНИЕ! Последняя команда сгенерировала ошибку");
                    System.out.println("На " + (counter_line + counter_input) + " строчке находится команда, которая сгенерировала исключение " + str_command);
                    break;
                }

                counter_line += 1;
            }
            fr.close();

        }
        catch (IOException e) {
            System.out.println("Ошибка чтения из файла");
        }
        catch (CommandNotFound e){
            System.out.println(e.send_message());
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
