import java.util.Scanner;

public class Main {
    public static void main(String[] argv){
        //Проверка ввода на корректность
        boolean is_true_command = false;
        while (!is_true_command) {
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();

            //Обработка строк

            //Сделать вывод "Аля, команда не найдена, попробуйте еще"

            //Нужно тогда создать список доступных команд. А это можно через enum реализовать
            String[] commands = {"help", "info", "show", "insert", "update", "remove_key",
            "clear", "save", "execute_script", "exit", "remove_greater", "history",
            "remove_lower_key", "remove_all_by_form_of_education", "min_by_creation_date", "filter_by_students_count"};


            for (String command : commands){
                if (input.contains(command)) {
                    is_true_command = true;
                    break;
                }
            }
            if (is_true_command) {
                System.out.println("Ok");
                break;
            }
            else {
                System.out.println("Команда не найдена.");
                System.out.println("Попробуйте ввести снова: ");
            }

        }
    }
}