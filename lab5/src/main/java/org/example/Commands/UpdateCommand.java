package org.example.Commands;

import org.example.Collections.StudyGroup;
import org.example.Exceptions.NotCollectionIDFound;
import org.example.Interface.Command;

import java.util.HashSet;
import java.util.Scanner;

import static org.example.Managers.CollectionManager.*;
import static org.example.Managers.CommandManager.*;

public class UpdateCommand implements Command {
    @Override
    public void execute() {
        try {
            if (study_groups == null) {get_HashSet();}
            String arg = history_list.getLast().split(" ")[1];
            //Перевод из численного в строковый тип
            int number = 1;
            boolean flag = false;
            number = Integer.parseInt(arg);
            HashSet<StudyGroup> new_study_groups = new HashSet<>();
            for (StudyGroup group : study_groups) {
                if (number == group.getID()) {
                    flag = true;
                    System.out.println("Коллекция с id: " + number + " найдена.");
                    System.out.println("Текучие значения указаны в скобках.");
                    element = group;

                    System.out.println(element);
                }
                else {new_study_groups.add(group);}
            }
            if (new_study_groups.size() == study_groups.size()) {
                throw new NotCollectionIDFound();
            }
            study_groups = new_study_groups;
            study_groups.add(element);
            System.out.println("Коллекция обновлена");


        }
        catch (NumberFormatException e){
            System.out.println("Для коллекции введен не целочисленный id. Введите команду еще раз");
            status_command = -1;
        }
        catch (NotCollectionIDFound e){
            System.out.println(e.send_message());
            status_command = -1;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Введите ID элемента");
            status_command = -1;
        }
    }



    @Override
    public String description() {
        return "обновляет значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String get_name_command() {
        return "update id {element}";
    }
}

