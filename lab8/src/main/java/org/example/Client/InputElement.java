package org.example.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import org.example.Collections.*;
import org.example.Managers.ElementManager;
import org.w3c.dom.Element;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.example.Client.Authorization.login;
import static org.example.Client.Authorization.password;
import static org.example.Client.Client.primaryStage;
import static org.example.Client.ClientResponds.getRespond;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;

public class InputElement {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private MenuButton formatEducation;
    @FXML
    private MenuButton semester;
    @FXML
    private MenuButton eye;
    @FXML
    private MenuButton hair;
    @FXML
    private TextField groupName;
    @FXML
    private TextField studentsCount;
    @FXML
    private TextField x;
    @FXML
    private TextField y;
    @FXML
    private TextField averageMark;
    @FXML
    private TextField adminName;
    @FXML
    private TextField birthday;
    @FXML
    private Button Ready;


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


        MenuItem distant = new MenuItem("DISTANCE_EDUCATION");
        distant.setOnAction(event -> changeFormat("DISTANCE_EDUCATION"));
        MenuItem fulltime = new MenuItem("FULL_TIME_EDUCATION");
        fulltime.setOnAction(event -> changeFormat("FULL_TIME_EDUCATION"));
        MenuItem evening = new MenuItem("EVENING_CLASSES");
        evening.setOnAction(event -> changeFormat("EVENING_CLASSES"));
        formatEducation.getItems().addAll(distant, fulltime, evening);

        //SECOND, FIFTH, SIXTH
        MenuItem second = new MenuItem("SECOND");
        second.setOnAction(event -> changeSemester("SECOND"));
        MenuItem fifth = new MenuItem("FIFTH");
        fifth.setOnAction(event -> changeSemester("FIFTH"));
        MenuItem sixth = new MenuItem("SIXTH");
        sixth.setOnAction(event -> changeSemester("SIXTH"));
        semester.getItems().addAll(second, fifth, sixth);

        //BLUE, ORANGE, WHITE
        MenuItem blue = new MenuItem("BLUE");
        blue.setOnAction(event -> changeEye("BLUE"));
        MenuItem orange = new MenuItem("ORANGE");
        orange.setOnAction(event -> changeEye("ORANGE"));
        MenuItem white = new MenuItem("WHITE");
        white.setOnAction(event -> changeEye("WHITE"));
        eye.getItems().addAll(blue, orange, white);

        //BLACK, ORANGE, WHITE, BROWN
        MenuItem black1 = new MenuItem("BLACK");
        black1.setOnAction(event -> changeHair("BLACK"));
        MenuItem orange1 = new MenuItem("ORANGE");
        orange1.setOnAction(event -> changeHair("ORANGE"));
        MenuItem white1 = new MenuItem("WHITE");
        white1.setOnAction(event -> changeHair("WHITE"));
        MenuItem brown1 = new MenuItem("BROWN");
        brown1.setOnAction(event -> changeHair("BROWN"));


        hair.getItems().addAll(black1, orange1, white1, brown1);

    }

    private void changeHair(String format) {
        hair.setText(format);
    }

    private void changeEye(String format) {
        eye.setText(format);
    }

    private void changeSemester(String format) {
        semester.setText(format);
    }

    private void changeFormat(String format) {
        formatEducation.setText(format);
    }

    private void changeLanguage(String language) {
        TextLanguage.setText(language);
        switch (language) {
            case "Английский (Австралия)":
                language = "en";
                break;
            case "Русский":
                language = "ru";
                break;
            case "Датский":
                language = "da";
                break;
            case "Словацкий":
                language = "sk";
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

    public StudyGroup getElement() {
        String name = groupName.getText();
        Double x1 = Double.parseDouble(x.getText());
        Double y1 = Double.parseDouble(y.getText());
        Integer studentCount = Integer.parseInt(studentsCount.getText());
        Double averageMark1 = Double.parseDouble(averageMark.getText());
        FormOfEducation formOfEducation = FormOfEducation.valueOf(formatEducation.getText());
        Semester semester1 = Semester.valueOf(semester.getText());
        String adminName1 = adminName.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String bithdayStr = birthday.getText();
        System.out.println(bithdayStr);
        java.time.LocalDate birthday1 = LocalDate.parse(bithdayStr, formatter);
        EyeColor eyeColor = EyeColor.valueOf(eye.getText());
        HairColor hairColor = HairColor.valueOf(hair.getText());
        Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
        Coordinates coordinates = new Coordinates(x1, y1);
        LocalDateTime creatingData = createDateCreating();
        StudyGroup studyGroup = new StudyGroup(name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

        return studyGroup;
    }

    private LocalDateTime createDateCreating() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return LocalDateTime.parse(formattedDateTime, formatter);
    }

    private void handleReadyButtonClick() {
        StudyGroup element = getElement();
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "add";
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            sendCommand(command, channel, serverAddress, buffer);
            buffer.clear();
            ArrayList<String> answer = getRespond(buffer, channel);

            Parent firstScene = FXMLLoader.load(getClass().getResource("/org.example.Client/main.fxml"));
            primaryStage.setScene(new Scene(firstScene));
            // Устанавливаем позицию всплывающего окна относительно основного окна

            primaryStage.show();




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}