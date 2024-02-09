import Function.ProcessingString;

import java.util.Scanner;

public class Main {
    public static void main(String[] argv) {
        Scanner in = new Scanner(System.in);

        //Обработка ввода
        boolean isTrueCommand = false;
        ProcessingString string = new ProcessingString();

        while (!isTrueCommand) {
            String input = in.nextLine();
            isTrueCommand = string.isTrueCommand(input);
            if (!isTrueCommand) {
                System.out.println("Команда не найдена :(");
                System.out.println("Введите команду снова: ");
            }
        }

        //Обработка команд



    }
}