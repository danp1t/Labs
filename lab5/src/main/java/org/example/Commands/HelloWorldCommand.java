package org.example.Commands;

import org.example.Interface.Command;

import java.io.FileNotFoundException;

public class HelloWorldCommand implements Command {
    @Override
    public void execute() throws FileNotFoundException {
        System.out.println("Hello, world!");
    }

    @Override
    public String description() {
        return "Выводит привет мир";
    }

    @Override
    public String getNameCommand() {
        return "hello";
    }
}
