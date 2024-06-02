package org.example.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class Main extends Application {

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
    }
}