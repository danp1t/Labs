package org.example.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Wrongpassword {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private Button Ok;
    @FXML
    private Text wrongPassword1;
    @FXML
    private Text wrongPassword2;

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
        ResourceBundle bundle = ResourceBundle.getBundle("locales/gui", locale); // Загружаем ресурсы для выбранного языка

        // Пример использования ресурсов из файла свойств
        String message1 = bundle.getString("wrong_password_1");
        String message2 = bundle.getString("wrong_password_2");
        String textButton = bundle.getString("wrong_password_button");

        // Здесь вы можете установить тексты согласно загруженным ресурсам
        wrongPassword1.setText(message1);
        wrongPassword2.setText(message2);
        Ok.setText(textButton);
    }

    private void handleOkButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Stage primaryStage = Client.primaryStage;
        Parent secondScene = loader.load(getClass().getResource("/org.example.Client/authorization.fxml"));
        primaryStage.setScene(new Scene(secondScene));
    }
}
