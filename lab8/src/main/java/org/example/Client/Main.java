package org.example.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private Label userLabel;



    @FXML
    private void initialize() {

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
        ResourceBundle bundle = ResourceBundle.getBundle("locales/gui", locale);

        String userLabel2 = bundle.getString("user_main_label");
        userLabel.setText(userLabel2 + " " + Authorization.login);


        // Здесь вы можете выполнить другие действия, связанные с изменением языка
    }
}