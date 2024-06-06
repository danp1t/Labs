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

public class Registration {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private Button Ok;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField Login;
    @FXML
    private Text registationMessage1;
    @FXML
    private Text registationMessage2;
    @FXML
    private Text registationMessage3;



    @FXML
    private void initialize() {
        Ok.setOnAction(event -> {
            try {
                handleOkButtonClick();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
        String message1 = bundle.getString("registation_message_1");
        String message2 = bundle.getString("registation_message_2");
        String message3 = bundle.getString("registation_message_3");

        registationMessage1.setText(message1);
        registationMessage2.setText(message2);
        registationMessage3.setText(message3);

    }

    private void handleOkButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Stage primaryStage = Client.primaryStage;
        Parent secondScene = loader.load(getClass().getResource("/org.example.Client/main.fxml"));
        primaryStage.setScene(new Scene(secondScene));
    }
}
