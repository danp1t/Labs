package org.example.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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
        // Здесь вы можете выполнить другие действия, связанные с изменением языка
    }

    private void handleOkButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Stage primaryStage = Client.primaryStage;
        Parent secondScene = loader.load(getClass().getResource("/org.example.Client/main.fxml"));
        primaryStage.setScene(new Scene(secondScene));
    }
}
