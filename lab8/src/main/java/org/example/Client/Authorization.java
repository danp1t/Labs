package org.example.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Collections.StudyGroup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.example.Client.Client.primaryStage;
import static org.example.Client.ClientGetAnswer.getAnswer;
import static org.example.Client.ClientResponds.getRespond;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;

public class Authorization {
    public static String login;
    public static String language = "ru";
    public static String password;
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private PasswordField UserPassword;
    @FXML
    private TextField UserLogin;
    @FXML
    private Text authText;
    @FXML
    private Text passwordText;
    @FXML
    private Text loginText;
    @FXML
    private Button Auth;



    @FXML
    private void initialize() {
        Auth.setOnAction(event -> handleLoginButtonClick());
        MenuItem russianItem = new MenuItem("Русский");
        russianItem.setOnAction(event -> changeLanguage("Русский"));

        MenuItem slovakItem = new MenuItem("Словацкий");
        slovakItem.setOnAction(event -> changeLanguage("Словацкий"));

        MenuItem danishItem = new MenuItem("Датский");
        danishItem.setOnAction(event -> changeLanguage("Датский"));

        MenuItem englishItem = new MenuItem("Английский (Австралия)");
        englishItem.setOnAction(event -> changeLanguage("Английский (Австралия)"));

        TextLanguage.getItems().addAll(russianItem, slovakItem, danishItem, englishItem);
    }

    private void changeLanguage(String language) {
        TextLanguage.setText(language);
        switch(language) {
            case "Английский (Австралия)": language =  "en";
            break;
            case "Русский": language = "ru";
            break;
            case "Датский": language = "da";
            break;
            case "Словацкий":  language = "sk";
            break;
        }

        Locale locale = new Locale(language); // Создаем объект Locale для выбранного языка
        ResourceBundle bundle = ResourceBundle.getBundle("locales/gui", locale); // Загружаем ресурсы для выбранного языка

        // Пример использования ресурсов из файла свойств
        String auth = bundle.getString("auth");
        String login_text = bundle.getString("login_text");
        String password_text = bundle.getString("password_text");
        String auth_button = bundle.getString("auth_button");

        // Здесь вы можете установить тексты согласно загруженным ресурсам
        authText.setText(auth);
        Auth.setText(auth_button);
        passwordText.setText(password_text);
        loginText.setText(login_text);
        Authorization.language = language;
    }

    private void handleLoginButtonClick() {
        login = UserLogin.getText();
        password = UserPassword.getText();

        try (DatagramChannel channel = DatagramChannel.open()) {
            int port = 8932;

            channel.socket().setSoTimeout(5000);
            channel.configureBlocking(false);
            boolean flag = false;
            int flag1 = 0;
            boolean readFlag = true;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            //Авторизация
            while (!flag) {
                //Отправить команду на сервер
                StudyGroup element = new StudyGroup();
                String line = "authorizations";
                Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
                buffer = sendCommand(command, channel, serverAddress, buffer);
                flag1 = getAnswer(buffer, channel);
                if (flag1 == 0) {flag = false;}
                else {flag = true;}
                buffer.clear();
            }

            if (flag1 == 2) {
                FXMLLoader loader = new FXMLLoader();
                Parent firstScene = loader.load(getClass().getResource("/org.example.Client/registration.fxml"));

                primaryStage.setScene(new Scene(firstScene));

                // Устанавливаем позицию всплывающего окна относительно основного окна
                primaryStage.setX(primaryStage.getX());
                primaryStage.setY(primaryStage.getY());

                primaryStage.show();
                System.out.println(language);
            }
            else if (flag1 == 3){
                FXMLLoader loader = new FXMLLoader();
                // Создаем контроллер для первой сцены


                Parent firstScene = loader.load(getClass().getResource("/org.example.Client/wrongpassword.fxml"));

                Wrongpassword SecondSceneController = new Wrongpassword();

                // Устанавливаем контроллер для первой сцены
                loader.setController(SecondSceneController);
                primaryStage.setScene(new Scene(firstScene));
                // Устанавливаем позицию всплывающего окна относительно основного окна


                primaryStage.show();

            } else if (flag1 == 1) {
                Parent firstScene = FXMLLoader.load(getClass().getResource("/org.example.Client/main.fxml"));
                primaryStage.setScene(new Scene(firstScene));
                // Устанавливаем позицию всплывающего окна относительно основного окна

                primaryStage.show();
                
            }


        } catch (SocketException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }


    private void handleRegisterButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Stage primaryStage = Client.primaryStage;
        Parent secondScene = loader.load(getClass().getResource("/org.example.Client/registration.fxml"));
        primaryStage.setScene(new Scene(secondScene));
    }



}
