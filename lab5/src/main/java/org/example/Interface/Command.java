package org.example.Interface;

import java.io.FileNotFoundException;

public interface Command {
    void execute() throws FileNotFoundException;
    String description();

    String get_name_command();
}
