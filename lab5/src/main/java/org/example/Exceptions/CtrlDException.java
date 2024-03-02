package org.example.Exceptions;

import java.io.IOException;

public class CtrlDException extends IOException {
    public String send_message(){
        return "Ай-ай-ай, как не стыдно ломать программу...";
    }
}
