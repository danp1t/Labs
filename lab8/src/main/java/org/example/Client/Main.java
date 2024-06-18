package org.example.Client;

import javafx.animation.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.example.Client.Authorization.*;
import static org.example.Client.Authorization.language;
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
    public static int MAINID;
    public static boolean MAINFLAG = false;
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

    private HashMap<String, Color> colorMap;
    private HashMap<Integer, Label> infoMap;
    private Random random;

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
                String ownerID = receivedTable.getWhoCreatedId();
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

            nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            xColumn.setCellFactory(TextFieldTableCell.<Table, Double>forTableColumn(new DoubleStringConverter()));
            yColumn.setCellFactory(TextFieldTableCell.<Table, Double>forTableColumn(new DoubleStringConverter()));
            studentCountColumn.setCellFactory(TextFieldTableCell.<Table, Integer>forTableColumn(new IntegerStringConverter()));
            averageMarkColumn.setCellFactory(TextFieldTableCell.<Table, Double>forTableColumn(new DoubleStringConverter()));
            BithdayColumn.setCellFactory(TextFieldTableCell.<Table, LocalDate>forTableColumn(new LocalDateStringConverter()));
            AdminNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());




            tableTable.setItems(tables);
            tableTable.setEditable(true);


            tableTable.setRowFactory(tv -> {
                TableRow<Table> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        Table rowData = row.getItem();
                        if (rowData.getWhoCreatedId().equals(login)) {
                            nameColumn.setEditable(true);
                            xColumn.setEditable(true);
                            yColumn.setEditable(true);
                            studentCountColumn.setEditable(true);
                            averageMarkColumn.setEditable(true);
                            BithdayColumn.setEditable(true);
                            AdminNameColumn.setEditable(true);

                        } else {
                            nameColumn.setEditable(false);
                            xColumn.setEditable(false);
                            yColumn.setEditable(false);
                            studentCountColumn.setEditable(false);
                            averageMarkColumn.setEditable(false);
                            BithdayColumn.setEditable(false);
                            AdminNameColumn.setEditable(false);
                        }
                    }
                    if (event.getButton() == MouseButton.SECONDARY) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            Table rowData = row.getItem();
                            if (rowData.getWhoCreatedId().equals(login)) {
                                try (DatagramChannel channel1 = DatagramChannel.open()) {
                                    channel1.configureBlocking(false);
                                    int port1 = 8932;
                                    ByteBuffer buffer1 = ByteBuffer.allocate(8192);
                                    InetSocketAddress serverAddress1 = new InetSocketAddress("192.168.10.80", port1);
                                    String line1 = "remove_by_id " + rowData.getId();
                                    StudyGroup element1 = new StudyGroup();
                                    Commands command1 = getCommand(line1, channel1, serverAddress1, buffer1, login, password, element1);
                                    sendCommand(command1, channel1, serverAddress1, buffer1);
                                    System.out.println(getRespond(buffer1, channel1));
                                    Thread.sleep(500);
                                    updateTable();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                    }}}
                });
                return row;
            });
            nameColumn.setOnEditCommit(event -> {
                Table editedTable = event.getTableView().getItems().get(event.getTablePosition().getRow());
                TableColumn<Table, ?> editedColumn = event.getTableColumn();
                Object newValue = event.getNewValue();

                // Добавьте код для сбора остальных данных из строки

                // Отправить данные на сервер (замените эту строку на ваш реальный метод отправки данных на сервер)
                Integer id = editedTable.getId();
                String name = (String) newValue;
                Double x1 = editedTable.getX();
                Double y1 = editedTable.getY();
                Integer studentCount = editedTable.getStudentsCount();
                Double averageMark1 = editedTable.getAverageMark();
                FormOfEducation formOfEducation = editedTable.getFormOfEducation();
                Semester semester1 = editedTable.getSemester();
                String adminName1 = editedTable.getAdminName();
                java.time.LocalDate birthday1 = editedTable.getBirthday();
                EyeColor eyeColor = editedTable.getAdminEyeColor();
                HairColor hairColor = editedTable.getAdminHairColor();
                Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
                Coordinates coordinates = new Coordinates(x1, y1);
                LocalDateTime creatingData = createDateCreating();
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

                try (DatagramChannel channel1 = DatagramChannel.open()) {
                    channel1.configureBlocking(false);
                    int port1 = 8932;
                    ByteBuffer buffer1 = ByteBuffer.allocate(8192);
                    InetSocketAddress serverAddress1 = new InetSocketAddress("192.168.10.80", port1);
                    String line1 = "update " + id;
                    StudyGroup element1 = studyGroup;
                    Commands command1 = getCommand(line1, channel1, serverAddress1, buffer1, login, password, element1);
                    sendCommand(command1, channel1, serverAddress1, buffer1);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updateTable();

            });
            xColumn.setOnEditCommit(event -> {
                Table editedTable = event.getTableView().getItems().get(event.getTablePosition().getRow());
                TableColumn<Table, ?> editedColumn = event.getTableColumn();
                Object newValue = event.getNewValue();

                // Добавьте код для сбора остальных данных из строки

                // Отправить данные на сервер (замените эту строку на ваш реальный метод отправки данных на сервер)
                Integer id = editedTable.getId();
                String name = editedTable.getGroupName();
                Double x1 = (Double) newValue;
                Double y1 = editedTable.getY();
                Integer studentCount = editedTable.getStudentsCount();
                Double averageMark1 = editedTable.getAverageMark();
                FormOfEducation formOfEducation = editedTable.getFormOfEducation();
                Semester semester1 = editedTable.getSemester();
                String adminName1 = editedTable.getAdminName();
                java.time.LocalDate birthday1 = editedTable.getBirthday();
                EyeColor eyeColor = editedTable.getAdminEyeColor();
                HairColor hairColor = editedTable.getAdminHairColor();
                Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
                Coordinates coordinates = new Coordinates(x1, y1);
                LocalDateTime creatingData = createDateCreating();
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

                try (DatagramChannel channel1 = DatagramChannel.open()) {
                    channel1.configureBlocking(false);
                    int port1 = 8932;
                    ByteBuffer buffer1 = ByteBuffer.allocate(8192);
                    InetSocketAddress serverAddress1 = new InetSocketAddress("192.168.10.80", port1);
                    String line1 = "update " + id;
                    StudyGroup element1 = studyGroup;
                    Commands command1 = getCommand(line1, channel1, serverAddress1, buffer1, login, password, element1);
                    sendCommand(command1, channel1, serverAddress1, buffer1);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updateTable();

            });
            yColumn.setOnEditCommit(event -> {
                Table editedTable = event.getTableView().getItems().get(event.getTablePosition().getRow());
                TableColumn<Table, ?> editedColumn = event.getTableColumn();
                Object newValue = event.getNewValue();

                // Добавьте код для сбора остальных данных из строки

                // Отправить данные на сервер (замените эту строку на ваш реальный метод отправки данных на сервер)
                Integer id = editedTable.getId();
                String name = editedTable.getGroupName();
                Double x1 = editedTable.getX();
                Double y1 = (Double) newValue;
                Integer studentCount = editedTable.getStudentsCount();
                Double averageMark1 = editedTable.getAverageMark();
                FormOfEducation formOfEducation = editedTable.getFormOfEducation();
                Semester semester1 = editedTable.getSemester();
                String adminName1 = editedTable.getAdminName();
                java.time.LocalDate birthday1 = editedTable.getBirthday();
                EyeColor eyeColor = editedTable.getAdminEyeColor();
                HairColor hairColor = editedTable.getAdminHairColor();
                Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
                Coordinates coordinates = new Coordinates(x1, y1);
                LocalDateTime creatingData = createDateCreating();
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

                try (DatagramChannel channel1 = DatagramChannel.open()) {
                    channel1.configureBlocking(false);
                    int port1 = 8932;
                    ByteBuffer buffer1 = ByteBuffer.allocate(8192);
                    InetSocketAddress serverAddress1 = new InetSocketAddress("192.168.10.80", port1);
                    String line1 = "update " + id;
                    StudyGroup element1 = studyGroup;
                    Commands command1 = getCommand(line1, channel1, serverAddress1, buffer1, login, password, element1);
                    sendCommand(command1, channel1, serverAddress1, buffer1);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updateTable();

            });
            studentCountColumn.setOnEditCommit(event -> {
                Table editedTable = event.getTableView().getItems().get(event.getTablePosition().getRow());
                TableColumn<Table, ?> editedColumn = event.getTableColumn();
                Object newValue = event.getNewValue();

                // Добавьте код для сбора остальных данных из строки

                // Отправить данные на сервер (замените эту строку на ваш реальный метод отправки данных на сервер)
                Integer id = editedTable.getId();
                String name = editedTable.getGroupName();
                Double x1 = editedTable.getX();
                Double y1 = editedTable.getY();
                Integer studentCount = (Integer) newValue;
                Double averageMark1 = editedTable.getAverageMark();
                FormOfEducation formOfEducation = editedTable.getFormOfEducation();
                Semester semester1 = editedTable.getSemester();
                String adminName1 = editedTable.getAdminName();
                java.time.LocalDate birthday1 = editedTable.getBirthday();
                EyeColor eyeColor = editedTable.getAdminEyeColor();
                HairColor hairColor = editedTable.getAdminHairColor();
                Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
                Coordinates coordinates = new Coordinates(x1, y1);
                LocalDateTime creatingData = createDateCreating();
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

                try (DatagramChannel channel1 = DatagramChannel.open()) {
                    channel1.configureBlocking(false);
                    int port1 = 8932;
                    ByteBuffer buffer1 = ByteBuffer.allocate(8192);
                    InetSocketAddress serverAddress1 = new InetSocketAddress("192.168.10.80", port1);
                    String line1 = "update " + id;
                    StudyGroup element1 = studyGroup;
                    Commands command1 = getCommand(line1, channel1, serverAddress1, buffer1, login, password, element1);
                    sendCommand(command1, channel1, serverAddress1, buffer1);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updateTable();

            });
            averageMarkColumn.setOnEditCommit(event -> {
                Table editedTable = event.getTableView().getItems().get(event.getTablePosition().getRow());
                TableColumn<Table, ?> editedColumn = event.getTableColumn();
                Object newValue = event.getNewValue();

                // Добавьте код для сбора остальных данных из строки

                // Отправить данные на сервер (замените эту строку на ваш реальный метод отправки данных на сервер)
                Integer id = editedTable.getId();
                String name = editedTable.getGroupName();
                Double x1 = editedTable.getX();
                Double y1 = editedTable.getY();
                Integer studentCount = editedTable.getStudentsCount();
                Double averageMark1 = (Double) newValue;
                FormOfEducation formOfEducation = editedTable.getFormOfEducation();
                Semester semester1 = editedTable.getSemester();
                String adminName1 = editedTable.getAdminName();
                java.time.LocalDate birthday1 = editedTable.getBirthday();
                EyeColor eyeColor = editedTable.getAdminEyeColor();
                HairColor hairColor = editedTable.getAdminHairColor();
                Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
                Coordinates coordinates = new Coordinates(x1, y1);
                LocalDateTime creatingData = createDateCreating();
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

                try (DatagramChannel channel1 = DatagramChannel.open()) {
                    channel1.configureBlocking(false);
                    int port1 = 8932;
                    ByteBuffer buffer1 = ByteBuffer.allocate(8192);
                    InetSocketAddress serverAddress1 = new InetSocketAddress("192.168.10.80", port1);
                    String line1 = "update " + id;
                    StudyGroup element1 = studyGroup;
                    Commands command1 = getCommand(line1, channel1, serverAddress1, buffer1, login, password, element1);
                    sendCommand(command1, channel1, serverAddress1, buffer1);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updateTable();

            });
            BithdayColumn.setOnEditCommit(event -> {
                Table editedTable = event.getTableView().getItems().get(event.getTablePosition().getRow());
                TableColumn<Table, ?> editedColumn = event.getTableColumn();
                Object newValue = event.getNewValue();

                // Добавьте код для сбора остальных данных из строки

                // Отправить данные на сервер (замените эту строку на ваш реальный метод отправки данных на сервер)
                Integer id = editedTable.getId();
                String name = editedTable.getGroupName();
                Double x1 = editedTable.getX();
                Double y1 = editedTable.getY();
                Integer studentCount = editedTable.getStudentsCount();
                Double averageMark1 = editedTable.getAverageMark();
                FormOfEducation formOfEducation = editedTable.getFormOfEducation();
                Semester semester1 = editedTable.getSemester();
                String adminName1 = editedTable.getAdminName();
                java.time.LocalDate birthday1 = (LocalDate) newValue;
                EyeColor eyeColor = editedTable.getAdminEyeColor();
                HairColor hairColor = editedTable.getAdminHairColor();
                Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
                Coordinates coordinates = new Coordinates(x1, y1);
                LocalDateTime creatingData = createDateCreating();
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

                try (DatagramChannel channel1 = DatagramChannel.open()) {
                    channel1.configureBlocking(false);
                    int port1 = 8932;
                    ByteBuffer buffer1 = ByteBuffer.allocate(8192);
                    InetSocketAddress serverAddress1 = new InetSocketAddress("192.168.10.80", port1);
                    String line1 = "update " + id;
                    StudyGroup element1 = studyGroup;
                    Commands command1 = getCommand(line1, channel1, serverAddress1, buffer1, login, password, element1);
                    sendCommand(command1, channel1, serverAddress1, buffer1);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updateTable();

            });
            AdminNameColumn.setOnEditCommit(event -> {
                Table editedTable = event.getTableView().getItems().get(event.getTablePosition().getRow());
                TableColumn<Table, ?> editedColumn = event.getTableColumn();
                Object newValue = event.getNewValue();

                // Добавьте код для сбора остальных данных из строки

                // Отправить данные на сервер (замените эту строку на ваш реальный метод отправки данных на сервер)
                Integer id = editedTable.getId();
                String name = editedTable.getGroupName();
                Double x1 = editedTable.getX();
                Double y1 = editedTable.getY();
                Integer studentCount = editedTable.getStudentsCount();
                Double averageMark1 = editedTable.getAverageMark();
                FormOfEducation formOfEducation = editedTable.getFormOfEducation();
                Semester semester1 = editedTable.getSemester();
                String adminName1 = (String) newValue;
                java.time.LocalDate birthday1 = editedTable.getBirthday();
                EyeColor eyeColor = editedTable.getAdminEyeColor();
                HairColor hairColor = editedTable.getAdminHairColor();
                Person admin = new Person(adminName1, birthday1, eyeColor, hairColor);
                Coordinates coordinates = new Coordinates(x1, y1);
                LocalDateTime creatingData = createDateCreating();
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creatingData, studentCount, averageMark1, formOfEducation, semester1, admin);

                try (DatagramChannel channel1 = DatagramChannel.open()) {
                    channel1.configureBlocking(false);
                    int port1 = 8932;
                    ByteBuffer buffer1 = ByteBuffer.allocate(8192);
                    InetSocketAddress serverAddress1 = new InetSocketAddress("192.168.10.80", port1);
                    String line1 = "update " + id;
                    StudyGroup element1 = studyGroup;
                    Commands command1 = getCommand(line1, channel1, serverAddress1, buffer1, login, password, element1);
                    sendCommand(command1, channel1, serverAddress1, buffer1);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                updateTable();

            });




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        updateTable();
        Locale locale = new Locale(language); // Создаем объект Locale для выбранного языка
        ResourceBundle bundle = ResourceBundle.getBundle("locales/gui", locale);

        String userLabel2 = bundle.getString("user_main_label");
        userLabel.setText(userLabel2 + " " + login);


        colorMap = new HashMap<>();
        infoMap = new HashMap<>();
        random = new Random();

        removeButton.setOnAction(event -> handleRemoveButtonClick());
        addIfMaxButton.setOnAction(event -> handleAddIfMaxButtonClick());
        addIfMinButton.setOnAction(event -> handleAddIfMinButtonClick());

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
                String ownerID = receivedTable.getWhoCreatedId();
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
        }
    }
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
                String line = "remove_by_id " + id;
                StudyGroup element = new StudyGroup();
                Commands command = getCommand(line, channel, serverAddress, buffer, login, password, element);
                sendCommand(command, channel, serverAddress, buffer);
                System.out.println(getRespond(buffer, channel));
                Thread.sleep(500);
                updateTable();



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
        infoMap.clear();

        for (var group : tableTable.getItems()) {

            Table table = (Table) group;
            var creatorName = table.getWhoCreatedId();

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
            // Вычисляем размер куба в зависимости от studentsCount
            var size = Math.min(100, Math.max(50, table.getStudentsCount() * 1.5) / 2);

// Создаем куб с вычисленным размером

            var rectangle = new Rectangle(size, size, colorMap.get(creatorName));
            double x = Math.abs(table.getX());
            double y = Math.abs(table.getY());
            while (x >= 720) {
                x = x / 10;
            }
            while (y >= 370 || y < 100) {
                y = y / 2;
            }

            var id = new Text('#' + String.valueOf(table.getId()));
            String whoCreatedId = table.getWhoCreatedId();
            String groupName = table.getGroupName();
            Double xStr = table.getX();
            Double yStr = table.getY();
            LocalDateTime creationDate = table.getCreationDate();
            int studentsCount = table.getStudentsCount();
            Double averageMark = table.getAverageMark();
            FormOfEducation formOfEducation = table.getFormOfEducation();
            Semester semester = table.getSemester();
            int adminId = table.getAdminId();
            String adminName = table.getAdminName();
            LocalDate birthday = table.getBirthday();
            HairColor adminHairColor = table.getAdminHairColor();
            EyeColor adminEyeColor = table.getAdminEyeColor();
            String label = "whoCreatedID = " + whoCreatedId + "\n" + "groupName = " + groupName + "\n" + "x = " + xStr + "\n" + "y = " +yStr + "\n" + "creationDate = " + creationDate + "\n" + "studentsCount =" + studentsCount + "\n" + "averageMark = " + averageMark + "\n" + "formOfEducation =" + formOfEducation + "\n" + "semester = " + semester + "\n" + "adminId = " + adminId + "\n" + "adminName = " + adminName + "\n" + "birthday = " + birthday + "\n" + "adminHairColor =" + adminHairColor + "\n" + "adminEyeColor =" + adminEyeColor + "\n";

            System.out.println(label);
            var info = new Label(label);

            info.setVisible(false);

            rectangle.setOnMouseEntered(mouseEvent -> {
                id.setVisible(false);
                info.setVisible(true);
                rectangle.setFill(colorMap.get(creatorName).brighter());
            });

            rectangle.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {


                        MAINFLAG = true;
                        MAINID = table.getId();


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

            });

            rectangle.setOnMouseExited(mouseEvent -> {
                id.setVisible(true);
                info.setVisible(false);
                rectangle.setFill(colorMap.get(creatorName));
            });

            id.setFont(Font.font("Segoe UI", size / 5));
            info.setStyle("-fx-background-color: white; -fx-border-color: #c0c0c0; -fx-border-width: 2");
            info.setFont(Font.font("Segoe UI", 15));

            visualPane.getChildren().add(rectangle);
            visualPane.getChildren().add(id);

            infoMap.put(table.getId(), info);

            if (!refresh) {
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1.5), rectangle);
                rotateTransition.setByAngle(360);
                rotateTransition.setCycleCount(Animation.INDEFINITE);
                rotateTransition.setAutoReverse(false);
                rotateTransition.play();

                ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), rectangle);
                scaleTransition.setByX(1.2);
                scaleTransition.setByY(1.2);
                scaleTransition.setCycleCount(2);
                scaleTransition.setAutoReverse(true);
                scaleTransition.play();

                Path path = new Path();
                path.getElements().add(new MoveTo(1500, 200));
                path.getElements().add(new HLineTo(x));
                path.getElements().add(new VLineTo(y));

                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.seconds(1));
                pathTransition.setNode(rectangle);
                pathTransition.setPath(path);
                pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                pathTransition.play();

                id.setLayoutX(x - id.getLayoutBounds().getWidth() / 2);
                id.setLayoutY(y);

                info.setLayoutX(x + size * 1.5);
                info.setLayoutY(y - 50);
            } else {
                rectangle.setX(x);
                rectangle.setY(y);
                id.setLayoutX(x - id.getLayoutBounds().getWidth() / 2);
                id.setLayoutY(y);
                info.setLayoutX(x + size * 1.5);
                info.setLayoutY(y - 50);

                FillTransition darkTransition = new FillTransition(Duration.seconds(0.75), rectangle);
                darkTransition.setToValue(colorMap.get(creatorName).darker().darker());

                FillTransition brightTransition = new FillTransition(Duration.seconds(0.75), rectangle);
                brightTransition.setToValue(colorMap.get(creatorName));

                SequentialTransition sequentialTransition = new SequentialTransition(darkTransition, brightTransition);
                sequentialTransition.setCycleCount(2);
                sequentialTransition.setAutoReverse(true);
                sequentialTransition.play();
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
                String ownerID = receivedTable.getWhoCreatedId();
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
    private LocalDateTime createDateCreating() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return LocalDateTime.parse(formattedDateTime, formatter);
    }
}