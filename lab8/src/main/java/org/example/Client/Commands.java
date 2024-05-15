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

    public Commands(String name, String arg, StudyGroup element, String login, String password) {
        this.name = name;
        this.arg = arg;
        this.element = element;
        this.login = login;
        this.password = password;
    }

    public Commands(String name, String arg, String login, String password) {
        this.name = name;
        this.arg = arg;
        this.element = null;
        this.login = login;
        this.password = password;
    }

    public Commands(String name, StudyGroup element, String login, String password) {
        this.name = name;
        this.arg = null;
        this.element = element;
        this.login = login;
        this.password = password;
    }

    public Commands(String name, String login, String password) {
        this.name = name;
        this.arg = null;
        this.element = null;
        this.login = login;
        this.password = password;
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

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }
}
