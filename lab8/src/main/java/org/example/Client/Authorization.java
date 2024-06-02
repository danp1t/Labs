package org.example.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Authorization {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private PasswordField UserPassword;
    @FXML
    private TextField UserLogin;
    @FXML
    private Button Auth;
    @FXML
    private Button Reg;


    @FXML
    private void initialize() {
        Auth.setOnAction(event -> handleLoginButtonClick());
        Reg.setOnAction(event -> {
            try {
                handleRegisterButtonClick();
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
    }

    private void handleLoginButtonClick() {
        String username = UserLogin.getText();
        String password = UserPassword.getText();

        // Здесь вы можете выполнить действия с полученными данными, например, их проверку или передачу на сервер для аутентификации.

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }
    private void handleRegisterButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Stage primaryStage = Main.primaryStage;
        Parent secondScene = loader.load(getClass().getResource("/org.example.Client/registration.fxml"));
        primaryStage.setScene(new Scene(secondScene));
    }


}
