package org.example.Server;


import org.example.Client.Commands;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import static org.example.Server.ServerResponds.byteBufferArrayList;
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
                if (!isNatural) {
                    isNatural = isNewUser(login, password);
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
    public static boolean isNewUser(String login, String password) throws SQLException, IOException, NoSuchAlgorithmException {
        String url = "jdbc:postgresql://pg:5432/studs";
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));
        Connection db = DriverManager.getConnection(url, info);
        String query = "SELECT password FROM person__123 where person__123.login = ?";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            buffer.put("Логин найден в базе данных\n".getBytes());
            buffer.put("Проверка пароля\n".getBytes());
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            boolean flag = hashtext.equals(rs.getString(1));
            if (flag) buffer.put("Введен корректный пароль\n".getBytes());
            else buffer.put("Введен некорректный пароль\nНапрягите свою память, чтобы вспомнить пароль".getBytes());
            byteBufferArrayList.add(buffer);
            buffer.clear();
            return flag;
        }
        else {
            Register(login, password);
            return true;
        }

    }

    public static void Register(String login, String password) throws IOException, SQLException, NoSuchAlgorithmException {
        String url = "jdbc:postgresql://pg:5432/studs";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("Логин не обнаружен в базе данных\n".getBytes());
        buffer.put("Будет создан новый пользователь с введенным логином и паролем\n".getBytes());
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));
        Connection db = DriverManager.getConnection(url, info);
        String getNextIDPerson = "SELECT nextval('person__123_id_seq');";
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery(getNextIDPerson);
        rs.next();
        int personNextId = rs.getInt(1);
        MessageDigest md = MessageDigest.getInstance("SHA-384");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        String query = "INSERT INTO person__123 VALUES(?, ?, ?);";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setInt(1, personNextId);
        ps.setString(2, login);
        ps.setString(3, hashtext);
        ps.execute();
        buffer.put("Пользователь добавлен в базу данных".getBytes());
        byteBufferArrayList.add(buffer);
        buffer.clear();
    }

    public static DatagramSocket getServerSocket() {
        return serverSocket;
    }
}