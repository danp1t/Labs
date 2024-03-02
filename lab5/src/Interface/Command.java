package Interface;

import Exceptions.CommandNotFound;

public interface Command {
    void execute();
    String description();

    String get_name_command();
}
