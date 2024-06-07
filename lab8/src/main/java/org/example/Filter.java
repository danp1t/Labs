package org.example;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Client.Authorization;
import org.example.Client.Commands;
import org.example.Collections.StudyGroup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.example.Client.Authorization.login;
import static org.example.Client.Authorization.password;
import static org.example.Client.ClientResponds.getRespond;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;

public class Filter {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private Button Ready;
    @FXML
    private TextField AverageMark;


    @FXML
    private void initialize() {
        Ready.setOnAction(event -> handleReadyButtonClick());
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

        Authorization.language = language;
    }

    public String handleReadyButtonClick() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "count_greater_than_average_mark " + AverageMark.getText();
            StudyGroup element = new StudyGroup();
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            if (Objects.isNull(command)) {
                String infoString = "Поле не должно быть пустым";
            }
            else {
                sendCommand(command, channel, serverAddress, buffer);
                buffer.clear();
                ArrayList<String> answer = getRespond(buffer, channel);
            }
            return AverageMark.getText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }}
}
