package Function;

import java.util.Scanner;

public class ProcessingString {

    public boolean isTrueCommand(String input){
        boolean is_true_command = false;
        String[] commands = {"help", "info", "show", "insert", "update", "remove_key",
                    "clear", "save", "execute_script", "exit", "remove_greater", "history",
                    "remove_lower_key", "remove_all_by_form_of_education", "min_by_creation_date", "filter_by_students_count"};


        for (String command : commands){
            if (input.contains(command)) {
                is_true_command = true;
                break;
            }
        }
        return is_true_command;
    }


}

