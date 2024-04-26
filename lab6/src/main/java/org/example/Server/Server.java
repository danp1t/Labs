package org.example.Server;


import org.example.Client.Commands;
import org.example.Collections.EyeColor;
import org.example.Collections.HairColor;
import org.example.Collections.Person;
import org.example.Managers.ElementManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Scanner;

import static org.example.Server.ServerCommandHandler.handlerCommand;
import static org.example.Server.ServerConnection.connection;
import static org.example.Server.ServerReadRequest.readRequest;
import static org.example.Server.ServerResponds.sendResponds;
import static org.example.Server.WrongPassword.sendMessage;;

public class Server {
    private static DatagramSocket serverSocket;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException, SQLException {
        Integer port = 8932;
        System.out.println(port);
        serverSocket = connection(port);
        boolean isNatural = false;
        try {
            logger.info("Server started");
            while (true) {
                DatagramPacket receivePacket = ServerReadRequest.getDatagramPacket(serverSocket);
                Commands receivedMessage = readRequest(receivePacket);
                //Проверка регистрации
                String login = receivedMessage.getLogin();
                String password = receivedMessage.getPassword();
                isNatural = isNewUser(login, password);
                if (!isNatural) {
                    sendMessage(serverSocket, receivePacket, "Вы ввели неправильный пароль. Попробуйте еще раз: ");
                    continue;
                }
                if (!receivedMessage.getName().equals("authorizations")) {
                    logger.info("Received from client: " + receivedMessage.getName());
                    //Авторизация или регистрация.
                    handlerCommand(receivedMessage);
                    sendResponds(serverSocket, receivePacket);
                    logger.info("Response sent to client");
                }
                else {
                    sendMessage(serverSocket, receivePacket, "Авторизация прошла успешно");
                }

            }


        } catch (Exception e) {
            logger.error("Error occurred: ", e);
        }
    }
    public static boolean isNewUser(String login, String password) throws SQLException, IOException {
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));
        Connection db = DriverManager.getConnection(url, info);
        String query = "SELECT password FROM person__123 where person__123.login = ?";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            boolean flag = password.equals(rs.getString(1));
            return flag;
        }
        else {
            Register(login, password);
            return true;
        }
    }

    public static void Register(String login, String password) throws IOException, SQLException {
        String url = "jdbc:postgresql://pg:5432/studs";
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));
        Connection db = DriverManager.getConnection(url, info);
        String getNextIDPerson = "SELECT nextval('person__123_id_seq');";
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery(getNextIDPerson);
        rs.next();
        int personNextId = rs.getInt(1);

        String query = "INSERT INTO person__123 VALUES(?, ?, ?);";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setInt(1, personNextId);
        ps.setString(2, login);
        ps.setString(3, password);

        ps.execute();

    }

    public static DatagramSocket getServerSocket() {
        return serverSocket;
    }
}