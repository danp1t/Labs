package org.example.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Registration {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private Button Reg;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField Login;

    @FXML
    private void initialize() {
        Reg.setOnAction(event -> handleRegisterButtonClick());
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
    private void handleRegisterButtonClick(){
        System.out.println(Login.getText());
        System.out.println(Password.getText());
    }
}
