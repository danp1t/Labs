package org.example.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Collections.*;
import org.example.Filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.Client.Authorization.login;
import static org.example.Client.Authorization.password;
import static org.example.Client.Client.primaryStage;
import static org.example.Client.ClientGetAnswer.getAnswer;
import static org.example.Client.ClientResponds.getRespond;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;

public class Main {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private Label userLabel;
    @FXML
    private Button showButton;
    @FXML
    private Button filterButton;
    @FXML
    private Button counterGreaterButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button addIfMaxButton;
    @FXML
    private Button addIfMinButton;
    @FXML
    private Button addButton;
    @FXML
    private Button minBySemesterButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button infoButton;
    @FXML
    private TableView tableTable;
    @FXML
    private TableColumn<Table, Integer> ownerColumn;
    @FXML
    private TableColumn<Table, Integer> idColumn;
    @FXML
    private TableColumn<Table, String> nameColumn;
    @FXML
    private TableColumn<Table, Double> xColumn;
    @FXML
    private TableColumn<Table, Double> yColumn;
    @FXML
    private TableColumn<Table, java.time.LocalDateTime> dateColumn;
    @FXML
    private TableColumn<Table, Integer> studentCountColumn;
    @FXML
    private TableColumn<Table, Double> averageMarkColumn;
    @FXML
    private TableColumn<Table, FormOfEducation> formOfEducationColumn;
    @FXML
    private TableColumn<Table, Semester> SemesterColumn;
    @FXML
    private TableColumn<Table, Integer> GroupAdminColumn;
    @FXML
    private TableColumn<Table, String> AdminNameColumn;
    @FXML
    private TableColumn<Table, java.time.LocalDate> BithdayColumn;
    @FXML
    private TableColumn<Table, HairColor> HairColumn;
    @FXML
    private TableColumn<Table, EyeColor> EyeColumn;

    private void updateTable() {
        ObservableList<Table> tables = FXCollections.observableArrayList();
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "getTable";
            StudyGroup element = new StudyGroup();
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            sendCommand(command, channel, serverAddress, buffer);

            // Получение размера массива
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            int n = ByteBuffer.wrap(data).getInt();
            for (int i = 0; i < n; i++) {
                buffer.clear();
                SocketAddress server = channel.receive(buffer);
                buffer.flip();
                data = new byte[buffer.remaining()];
                buffer.get(data);

                ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Table receivedTable = (Table) ois.readObject();
                ois.close();
                int ownerID = receivedTable.getWhoCreatedId();
                int ID = receivedTable.getId();
                String name = receivedTable.getGroupName();
                Double x = receivedTable.getX();
                Double y = receivedTable.getY();
                LocalDateTime creation = receivedTable.getCreationDate();
                Integer studentsCount = receivedTable.getStudentsCount();
                Double averageMark = receivedTable.getAverageMark();
                FormOfEducation formOfEducation = receivedTable.getFormOfEducation();
                Semester semester = receivedTable.getSemester();
                int adminID = receivedTable.getAdminId();
                String adminName = receivedTable.getAdminName();
                LocalDate birthday = receivedTable.getBirthday();
                HairColor hairColor = receivedTable.getAdminHairColor();
                EyeColor eyeColor = receivedTable.getAdminEyeColor();

                tables.add(new Table(ownerID, ID, name, x, y, creation, studentsCount, averageMark, formOfEducation, semester, adminID, adminName, birthday, hairColor, eyeColor));
                ownerColumn.setCellValueFactory(new PropertyValueFactory<>("whoCreatedId"));
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("groupName"));
                xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
                yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
                studentCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
                averageMarkColumn.setCellValueFactory(new PropertyValueFactory<>("averageMark"));
                formOfEducationColumn.setCellValueFactory(new PropertyValueFactory<>("formOfEducation"));
                SemesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
                GroupAdminColumn.setCellValueFactory(new PropertyValueFactory<>("adminId"));
                AdminNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
                BithdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
                HairColumn.setCellValueFactory(new PropertyValueFactory<>("adminHairColor"));
                EyeColumn.setCellValueFactory(new PropertyValueFactory<>("adminEyeColor"));
            }


            tableTable.setItems(tables);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        addIfMaxButton.setOnAction(event -> handleAddIfMaxButtonClick());
        addIfMinButton.setOnAction(event -> handleAddIfMinButtonClick());
        updateTable();
        addButton.setOnAction(event -> handleAddButtonClick());
        filterButton.setOnAction(event -> handleFilterButtonClick());
        minBySemesterButton.setOnAction(event -> handleMinBySemButtonClick());
        counterGreaterButton.setOnAction(event -> handleCounterButtonClick());
        showButton.setOnAction(event -> updateTable());
        historyButton.setOnAction(event -> handleHistoryButtonClick());
        infoButton.setOnAction(event -> handleInfoButtonClick());
        helpButton.setOnAction(event -> handleHelpButtonClick());
        clearButton.setOnAction(event -> {
            try {
                handleClearButtonClick();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        exitButton.setOnAction(event -> {
            try {
                handleExitButtonClick();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        MenuItem russianItem = new MenuItem("Русский");
        russianItem.setOnAction(event -> {
            try {
                changeLanguage("Русский");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        MenuItem slovakItem = new MenuItem("Словацкий");
        slovakItem.setOnAction(event -> {
            try {
                changeLanguage("Словацкий");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        MenuItem danishItem = new MenuItem("Датский");
        danishItem.setOnAction(event -> {
            try {
                changeLanguage("Датский");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        MenuItem englishItem = new MenuItem("Английский (Австралия)");
        englishItem.setOnAction(event -> {
            try {
                changeLanguage("Английский (Австралия)");
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        TextLanguage.getItems().addAll(russianItem, slovakItem, danishItem, englishItem);
    }
    private void handleFilterButtonClick() {
        ObservableList<Table> tables = FXCollections.observableArrayList();
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "getTable";
            StudyGroup element = new StudyGroup();
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            sendCommand(command, channel, serverAddress, buffer);

            // Получение размера массива
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            int n = ByteBuffer.wrap(data).getInt();
            for (int i = 0; i < n; i++) {
                buffer.clear();
                SocketAddress server = channel.receive(buffer);
                buffer.flip();
                data = new byte[buffer.remaining()];
                buffer.get(data);

                ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Table receivedTable = (Table) ois.readObject();
                ois.close();
                int ownerID = receivedTable.getWhoCreatedId();
                int ID = receivedTable.getId();
                String name = receivedTable.getGroupName();
                Double x = receivedTable.getX();
                Double y = receivedTable.getY();
                LocalDateTime creation = receivedTable.getCreationDate();
                Integer studentsCount = receivedTable.getStudentsCount();
                Double averageMark = receivedTable.getAverageMark();
                FormOfEducation formOfEducation = receivedTable.getFormOfEducation();
                Semester semester = receivedTable.getSemester();
                int adminID = receivedTable.getAdminId();
                String adminName = receivedTable.getAdminName();
                LocalDate birthday = receivedTable.getBirthday();
                HairColor hairColor = receivedTable.getAdminHairColor();
                EyeColor eyeColor = receivedTable.getAdminEyeColor();

                tables.add(new Table(ownerID, ID, name, x, y, creation, studentsCount, averageMark, formOfEducation, semester, adminID, adminName, birthday, hairColor, eyeColor));
                ownerColumn.setCellValueFactory(new PropertyValueFactory<>("whoCreatedId"));
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("groupName"));
                xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
                yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
                studentCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
                averageMarkColumn.setCellValueFactory(new PropertyValueFactory<>("averageMark"));
                formOfEducationColumn.setCellValueFactory(new PropertyValueFactory<>("formOfEducation"));
                SemesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
                GroupAdminColumn.setCellValueFactory(new PropertyValueFactory<>("adminId"));
                AdminNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
                BithdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
                HairColumn.setCellValueFactory(new PropertyValueFactory<>("adminHairColor"));
                EyeColumn.setCellValueFactory(new PropertyValueFactory<>("adminEyeColor"));
            }


            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Введите значение поля name:");
            dialog.setHeaderText(null);
            dialog.setContentText("Введите значение поля name:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(value -> {
                String searchString = value;

                // Фильтрация таблицы по adminName и groupName
                ObservableList<Table> filteredList = FXCollections.observableArrayList();
                for (Table table : tables) {
                    if (table.getAdminName().contains(searchString) || table.getGroupName().contains(searchString)) {
                        filteredList.add(table);
                    }
                }

                tableTable.setItems(filteredList);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }}
        ;
    private void handleAddIfMaxButtonClick() {
        FXMLLoader loader = new FXMLLoader();
        // Создаем контроллер для первой сцены


        Parent firstScene = null;
        try {
            firstScene = loader.load(getClass().getResource("/org.example.Client/AddMax.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputElement SecondSceneController = new InputElement();

        // Устанавливаем контроллер для первой сцены
        loader.setController(SecondSceneController);
        primaryStage.setScene(new Scene(firstScene));
        // Устанавливаем позицию всплывающего окна относительно основного окна


        primaryStage.show();
    }
    private void handleAddIfMinButtonClick() {
        FXMLLoader loader = new FXMLLoader();
        // Создаем контроллер для первой сцены


        Parent firstScene = null;
        try {
            firstScene = loader.load(getClass().getResource("/org.example.Client/AddMax.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputElement SecondSceneController = new InputElement();

        // Устанавливаем контроллер для первой сцены
        loader.setController(SecondSceneController);
        primaryStage.setScene(new Scene(firstScene));
        // Устанавливаем позицию всплывающего окна относительно основного окна


        primaryStage.show();
    }
    private void handleAddButtonClick() {
        FXMLLoader loader = new FXMLLoader();
        // Создаем контроллер для первой сцены


        Parent firstScene = null;
        try {
            firstScene = loader.load(getClass().getResource("/org.example.Client/inputElement.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputElement SecondSceneController = new InputElement();

        // Устанавливаем контроллер для первой сцены
        loader.setController(SecondSceneController);
        primaryStage.setScene(new Scene(firstScene));
        // Устанавливаем позицию всплывающего окна относительно основного окна


        primaryStage.show();
    }
    private void handleCounterButtonClick() {

        FXMLLoader loader = new FXMLLoader();
        // Создаем контроллер для первой сцены


        Parent firstScene = null;
        try {
            firstScene = loader.load(getClass().getResource("/org.example.Client/counter.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Counter SecondSceneController = new Counter();

        // Устанавливаем контроллер для первой сцены
        loader.setController(SecondSceneController);
        primaryStage.setScene(new Scene(firstScene));
        // Устанавливаем позицию всплывающего окна относительно основного окна


        primaryStage.show();
    }
    private void handleMinBySemButtonClick() {
        ObservableList<Table> tables = FXCollections.observableArrayList();
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "getTable";
            StudyGroup element = new StudyGroup();
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            sendCommand(command, channel, serverAddress, buffer);

            // Получение размера массива
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            int n = ByteBuffer.wrap(data).getInt();
            for (int i = 0; i < n; i++) {
                buffer.clear();
                SocketAddress server = channel.receive(buffer);
                buffer.flip();
                data = new byte[buffer.remaining()];
                buffer.get(data);

                ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Table receivedTable = (Table) ois.readObject();
                ois.close();
                int ownerID = receivedTable.getWhoCreatedId();
                int ID = receivedTable.getId();
                String name = receivedTable.getGroupName();
                Double x = receivedTable.getX();
                Double y = receivedTable.getY();
                LocalDateTime creation = receivedTable.getCreationDate();
                Integer studentsCount = receivedTable.getStudentsCount();
                Double averageMark = receivedTable.getAverageMark();
                FormOfEducation formOfEducation = receivedTable.getFormOfEducation();
                Semester semester = receivedTable.getSemester();
                int adminID = receivedTable.getAdminId();
                String adminName = receivedTable.getAdminName();
                LocalDate birthday = receivedTable.getBirthday();
                HairColor hairColor = receivedTable.getAdminHairColor();
                EyeColor eyeColor = receivedTable.getAdminEyeColor();

                tables.add(new Table(ownerID, ID, name, x, y, creation, studentsCount, averageMark, formOfEducation, semester, adminID, adminName, birthday, hairColor, eyeColor));
                ownerColumn.setCellValueFactory(new PropertyValueFactory<>("whoCreatedId"));
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("groupName"));
                xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
                yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
                studentCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
                averageMarkColumn.setCellValueFactory(new PropertyValueFactory<>("averageMark"));
                formOfEducationColumn.setCellValueFactory(new PropertyValueFactory<>("formOfEducation"));
                SemesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
                GroupAdminColumn.setCellValueFactory(new PropertyValueFactory<>("adminId"));
                AdminNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
                BithdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
                HairColumn.setCellValueFactory(new PropertyValueFactory<>("adminHairColor"));
                EyeColumn.setCellValueFactory(new PropertyValueFactory<>("adminEyeColor"));
            }

            if (!tables.isEmpty()) {
                Table minSemesterTable = tables.stream()
                        .min(Comparator.comparing(Table::getSemester))
                        .orElse(null);

                if (minSemesterTable != null) {
                    ObservableList<Table> filteredList = FXCollections.observableArrayList();
                    filteredList.add(minSemesterTable);
                    tableTable.setItems(filteredList);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ;











    }
        private void handleInfoButtonClick() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "info";
            StudyGroup element = new StudyGroup();
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            sendCommand(command, channel, serverAddress, buffer);
            buffer.clear();
            ArrayList<String> answer = getRespond(buffer, channel);
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = answer.get(0);
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("Информация о коллекции");
            stage.show();

    } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }}

        private void handleExitButtonClick() throws IOException, InterruptedException {


        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "exit";
            StudyGroup element = new StudyGroup();
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            sendCommand(command, channel, serverAddress, buffer);


        }
    }
    private void handleClearButtonClick() throws IOException, InterruptedException {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "clear";
            StudyGroup element = new StudyGroup();
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            sendCommand(command, channel, serverAddress, buffer);
            updateTable();
    }
    }
    private void handleHistoryButtonClick() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "history";
            StudyGroup element = new StudyGroup();
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
            sendCommand(command, channel, serverAddress, buffer);
            buffer.clear();
            ArrayList<String> answer = getRespond(buffer, channel);
            Stage stage = new Stage();
            TextArea textArea = new TextArea();

            // Здесь можно добавить логику для получения информации из консоли
            // Например, если у вас есть строка с информацией infoString, то можно добавить:
            String infoString = answer.get(0);
            textArea.setText(infoString);

            Scene scene = new Scene(new Group(textArea), 400, 200);
            stage.setScene(scene);
            stage.setTitle("History");
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }}

    private void handleHelpButtonClick() {
        // Создаем всплывающее окно
        Stage popupStage = new Stage();
        popupStage.setTitle("Help");
        Parent firstScene;
        try {
            firstScene = FXMLLoader.load(getClass().getResource("/org.example.Client/help.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        popupStage.setScene(new Scene(firstScene));

        // Устанавливаем позицию всплывающего окна относительно основного окна
        popupStage.setX(primaryStage.getX() + 50);
        popupStage.setY(primaryStage.getY() + 50);

        popupStage.show();
    }
    private void changeLanguage(String language) throws IOException, InterruptedException, ClassNotFoundException {
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
        userLabel.setText(userLabel2 + " " + login);


        // Здесь вы можете выполнить другие действия, связанные с изменением языка
    }
}