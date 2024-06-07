package org.example.Client;

import javafx.animation.PathTransition;
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
    private Button removeButton;
    @FXML
    private Button minBySemesterButton;
    @FXML
    private Tab visualTab;
    @FXML
    private AnchorPane visualPane;
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        removeButton.setOnAction(event -> handleRemoveButtonClick());
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
        visualTab.setOnSelectionChanged(event -> visualise(false));
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
    private void handleRemoveButtonClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Введите значение поля id:");
        dialog.setHeaderText(null);
        dialog.setContentText("Введите значение поля id:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> {
            String id = value;
            try (DatagramChannel channel = DatagramChannel.open()) {
                channel.configureBlocking(false);
                int port = 8932;
                ByteBuffer buffer = ByteBuffer.allocate(8192);
                InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
                System.out.println(id);
                String line = "remove_by_id " + id;
                StudyGroup element = new StudyGroup();
                Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
                sendCommand(command, channel, serverAddress, buffer);
                System.out.println(getRespond(buffer, channel));
                Thread.sleep(500);



            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateTable();

        });


    }
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

    public void visualise(boolean refresh) {
        visualPane.getChildren().clear();
        //infoMap.clear();

        for (var group : tableTable.getItems()) {
            var creatorName = "Danil";

            if (!colorMap.containsKey(creatorName)) {
                var r = random.nextDouble();
                var g = random.nextDouble();
                var b = random.nextDouble();
                if (Math.abs(r - g) + Math.abs(r - b) + Math.abs(b - g) < 0.6) {
                    r += (1 - r) / 1.4;
                    g += (1 - g) / 1.4;
                    b += (1 - b) / 1.4;
                }
                colorMap.put(creatorName, Color.color(r, g, b));
            }

            var size = Math.min(125, Math.max(75, product.getPrice() * 2) / 2);

            var circle = new Circle(size, colorMap.get(creatorName));
            double x = Math.abs(product.getCoordinates().getX());
            while (x >= 720) {
                x = x / 10;
            }
            double y = Math.abs(product.getCoordinates().getY());
            while (y >= 370) {
                y = y / 3;
            }
            if (y < 100) y += 125;

            var id = new Text('#' + String.valueOf(product.getId()));
            var info = new Label(new ProductPresenter(localizator).describe(product));

            info.setVisible(false);
            circle.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    doubleClickUpdate(product);
                }
            });

            circle.setOnMouseEntered(mouseEvent -> {
                id.setVisible(false);
                info.setVisible(true);
                circle.setFill(colorMap.get(creatorName).brighter());
            });

            circle.setOnMouseExited(mouseEvent -> {
                id.setVisible(true);
                info.setVisible(false);
                circle.setFill(colorMap.get(creatorName));
            });

            id.setFont(Font.font("Segoe UI", size / 1.4));
            info.setStyle("-fx-background-color: white; -fx-border-color: #c0c0c0; -fx-border-width: 2");
            info.setFont(Font.font("Segoe UI", 15));

            visualPane.getChildren().add(circle);
            visualPane.getChildren().add(id);

            infoMap.put(product.getId(), info);
            if (!refresh) {
                var path = new Path();
                path.getElements().add(new MoveTo(-500, -150));
                path.getElements().add(new HLineTo(x));
                path.getElements().add(new VLineTo(y));
                id.translateXProperty().bind(circle.translateXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.translateYProperty().add(id.getLayoutBounds().getHeight() / 4));
                info.translateXProperty().bind(circle.translateXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.translateYProperty().subtract(120));
                var transition = new PathTransition();
                transition.setDuration(Duration.millis(750));
                transition.setNode(circle);
                transition.setPath(path);
                transition.setOrientation(PathTransition.OrientationType.NONE);
                transition.play();
            } else {
                circle.setCenterX(x);
                circle.setCenterY(y);
                info.translateXProperty().bind(circle.centerXProperty().add(circle.getRadius()));
                info.translateYProperty().bind(circle.centerYProperty().subtract(120));
                id.translateXProperty().bind(circle.centerXProperty().subtract(id.getLayoutBounds().getWidth() / 2));
                id.translateYProperty().bind(circle.centerYProperty().add(id.getLayoutBounds().getHeight() / 4));
                var darker = new FillTransition(Duration.millis(750), circle);
                darker.setFromValue(colorMap.get(creatorName));
                darker.setToValue(colorMap.get(creatorName).darker().darker());
                var brighter = new FillTransition(Duration.millis(750), circle);
                brighter.setFromValue(colorMap.get(creatorName).darker().darker());
                brighter.setToValue(colorMap.get(creatorName));
                var transition = new SequentialTransition(darker, brighter);
                transition.play();
            }
        }

        for (var id : infoMap.keySet()) {
            visualPane.getChildren().add(infoMap.get(id));
        }
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