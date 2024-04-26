package org.example.Client;

import org.example.Collections.StudyGroup;

import java.io.Serializable;
import java.util.Objects;

public class Commands implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String arg;
    private StudyGroup element;
    private String login;
    private String password;

    public Commands(String name, String arg, StudyGroup element) {
        this.name = name;
        this.arg = arg;
        this.element = element;
    }

    public Commands(String name, String arg) {
        this.name = name;
        this.arg = arg;
        this.element = null;
    }

    public Commands(String name, StudyGroup element) {
        this.name = name;
        this.arg = null;
        this.element = element;
    }

    public Commands(String name) {
        this.name = name;
        this.arg = null;
        this.element = null;
    }

    public String toString() {
        String string = "{name: " + name;
        if (!Objects.isNull(arg)) {
            string += ", arg: " + arg;
        }
        if (!Objects.isNull(element)) {
            string += ", element: " + element.toString();
        }
        string += "}";

        return string;
    }

    public String getName() {
        return this.name;
    }

    public String getArg() {
        return this.arg;
    }

    public StudyGroup getElement() {
        return this.element;
    }
}
