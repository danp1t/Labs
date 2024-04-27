package org.example.Server;

import org.example.Client.Commands;
import org.example.Interface.Command;
import org.example.Managers.CollectionManager;
import org.example.Managers.CommandManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static org.example.Managers.StartManager.getCollectionManager;
import static org.example.Managers.StartManager.getCommandManager;

public class ServerCommandHandler {
    public static void handlerCommand(Commands input_command) {
        CommandManager commands = getCommandManager();

        try {
            Command command = commands.getCommands().get(input_command.getName());
            commands.addCommandToHistory(command);
            command.execute(input_command.getName(), input_command.getArg(), input_command.getElement(), input_command.getLogin());
        }
        catch (NullPointerException | FileNotFoundException e) {
            System.out.println("Команда не найдена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
