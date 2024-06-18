package org.example.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Collections.*;
import org.example.Exceptions.NotFoundEnum;
import org.example.Exceptions.NotPositiveField;
import org.example.Managers.ElementManager;
import org.w3c.dom.Element;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.example.Client.Authorization.login;
import static org.example.Client.Authorization.password;
import static org.example.Client.Client.primaryStage;
import static org.example.Client.ClientResponds.getRespond;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;

public class AddMin {
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
        Double x1 = null;
        Double y1 = null;
        Integer studentCount = null;
        try {
            x1 = Double.parseDouble(x.getText());
        }
        catch (NumberFormatException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Ошибка при вводе поля. Оно должно быть числом с плавающей точкой. Повторите попытку";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            x.setText("");
        }
        try {
            y1 = Double.parseDouble(y.getText());
        }
        catch (NumberFormatException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Ошибка при вводе поля. Оно должно быть числом с плавающей точкой. Повторите попытку";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            y.setText("");
        }

        try {
            studentCount = Integer.parseInt(studentsCount.getText());
            if (studentCount <= 0) {
                throw new NotPositiveField();
            }
        }
        catch (NumberFormatException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Ошибка при вводе поля. Оно должно быть числом с плавающей точкой. Повторите попытку" + "\n" + "Значение этого поля должно быть положительным число. Попробуйте снова";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            studentsCount.setText("");
        } catch (NotPositiveField e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = e.sendMessage();
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            studentsCount.setText("");
        }
        Double averageMark1 = Double.valueOf(0);
        try {
            averageMark1 = Double.parseDouble(averageMark.getText());
            if (averageMark1 <= 0.0) {
                throw new NotPositiveField();
            }
        }
        catch (NumberFormatException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Введенное значение не соответствует целочисленному типу. Повторите ввод еще раз";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            averageMark.setText("");
        }
        catch (NotPositiveField e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = e.sendMessage();
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            averageMark.setText("");
        }
        FormOfEducation formOfEducation = FormOfEducation.valueOf(formatEducation.getText());

        Semester semester1 = Semester.valueOf(semester.getText());

        String adminName1 = adminName.getText();
        java.time.LocalDate birthday1 = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String bithdayStr = birthday.getText();
            birthday1 = LocalDate.parse(bithdayStr, formatter);
        }
        catch (DateTimeParseException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Неверный формат даты! Попробуйте еще раз";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            birthday.setText("");
        }



        EyeColor eyeColor = EyeColor.valueOf(eye.getText());
        HairColor hairColor = HairColor.valueOf(hair.getText());
        Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
        Coordinates coordinates = new Coordinates(x1, y1);
        LocalDateTime creatingData = createDateCreating();
        StudyGroup studyGroup = new StudyGroup(name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

        return studyGroup;
    }
    public StudyGroup getElement(int ID) {
        String name = groupName.getText();
        Double x1 = null;
        Double y1 = null;
        Integer studentCount = null;
        try {
            x1 = Double.parseDouble(x.getText());
        }
        catch (NumberFormatException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Ошибка при вводе поля. Оно должно быть числом с плавающей точкой. Повторите попытку";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            x.setText("");
        }
        try {
            y1 = Double.parseDouble(y.getText());
        }
        catch (NumberFormatException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Ошибка при вводе поля. Оно должно быть числом с плавающей точкой. Повторите попытку";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            y.setText("");
        }

        try {
            studentCount = Integer.parseInt(studentsCount.getText());
            if (studentCount <= 0) {
                throw new NotPositiveField();
            }
        }
        catch (NumberFormatException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Ошибка при вводе поля. Оно должно быть числом с плавающей точкой. Повторите попытку" + "\n" + "Значение этого поля должно быть положительным число. Попробуйте снова";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            studentsCount.setText("");
        } catch (NotPositiveField e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = e.sendMessage();
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            studentsCount.setText("");
        }
        Double averageMark1 = Double.valueOf(0);
        try {
            averageMark1 = Double.parseDouble(averageMark.getText());
            if (averageMark1 <= 0.0) {
                throw new NotPositiveField();
            }
        }
        catch (NumberFormatException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Введенное значение не соответствует целочисленному типу. Повторите ввод еще раз";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            averageMark.setText("");
        }
        catch (NotPositiveField e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = e.sendMessage();
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            averageMark.setText("");
        }
        FormOfEducation formOfEducation = FormOfEducation.valueOf(formatEducation.getText());


        Semester semester1 = Semester.valueOf(semester.getText());


        String adminName1 = adminName.getText();
        java.time.LocalDate birthday1 = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String bithdayStr = birthday.getText();
            birthday1 = LocalDate.parse(bithdayStr, formatter);
        }
        catch (DateTimeParseException e) {
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = "Неверный формат даты! Попробуйте еще раз";
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("ERROR");
            stage.show();
            birthday.setText("");
        }



        EyeColor eyeColor = EyeColor.valueOf(eye.getText());
        HairColor hairColor = HairColor.valueOf(hair.getText());
        Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
        Coordinates coordinates = new Coordinates(x1, y1);
        LocalDateTime creatingData = createDateCreating();
        StudyGroup studyGroup = new StudyGroup(ID, name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

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
        if (Objects.isNull(element)) {
            return;
        }
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "add_if_min";
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
