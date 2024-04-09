package org.example.Server;

import org.example.Interface.Command;
import org.example.Managers.CommandManager;

import java.io.FileNotFoundException;

import static org.example.Managers.StartManager.getCommandManager;

public class ServerCommandHandler {
    public static void handlerCommand(String[] tokens) {
        CommandManager commands = getCommandManager();

        try {
            Command command = commands.getCommands().get(tokens[0]);
            commands.addCommandToHistory(command);
            command.execute(tokens);
        }
        catch (NullPointerException | FileNotFoundException e) {
            System.out.println("Команда не найдена");
        }
    }
}
