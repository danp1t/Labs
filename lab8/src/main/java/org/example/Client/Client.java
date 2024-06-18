package org.example.Client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.Collections.StudyGroup;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import static org.example.Client.ClientGetAnswer.getAnswer;
import static org.example.Client.ClientResponds.getRespond;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;


public class Client extends Application {
    // Ваш существующий код JavaFX здесь
    public static Stage primaryStage;
    private Parent firstScene;
    private Parent secondScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        this.primaryStage.setTitle("Суперское приложение");

        // Загружаем первую сцену из fxml-файла
        firstScene = loader.load(getClass().getResource("/org.example.Client/authorization.fxml"));

        // Создаем контроллер для первой сцены
        Authorization firstSceneController = new Authorization();

        // Устанавливаем контроллер для первой сцены
        loader.setController(firstSceneController);

        // Устанавливаем сцену и отображаем окно
        primaryStage.setScene(new Scene(firstScene));
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }



    public static void main(String[] args) {



        launch(args);

        // Создаем и запускаем новый поток для сетевых операций
        Thread networkThread = new Thread(new NetworkThread());
        networkThread.start();
        }

}

class NetworkThread implements Runnable {
    @Override
    public void run() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            int port = 8932;

            channel.socket().setSoTimeout(5000);
            channel.configureBlocking(false);
            Scanner sc = new Scanner(System.in);
            boolean flag = false;
            boolean readFlag = true;
            String login = Authorization.login;
            String password = Authorization.password;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            //Авторизация
            System.out.println("Клиент запущен!");
            while (!flag) {
                //Отправить команду на сервер
                StudyGroup element = new StudyGroup();
                String line = "authorizations";
                Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
                buffer = sendCommand(command, channel, serverAddress, buffer);
                int flag1 = getAnswer(buffer, channel);
                if (flag1 == 0) {flag = false;}
                else {flag = true;}
                buffer.clear();
            }
            while (true) {
                buffer.clear();
                System.out.println("Введите команду: ");
                String line = sc.nextLine();
                StudyGroup element = new StudyGroup();
                Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
                if (Objects.isNull(command)) {
                    continue;
                }
                buffer = sendCommand(command, channel, serverAddress, buffer);
                buffer.clear();
                getRespond(buffer, channel);
            }


        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }


}





