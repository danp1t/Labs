package org.example.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Collections.*;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.example.Client.Authorization.login;
import static org.example.Client.Authorization.password;
import static org.example.Client.ClientGetAnswer.getAnswer;
import static org.example.Client.ClientSendCommand.sendCommand;
import static org.example.Client.SerializableCommand.getCommand;

public class Main {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private Label userLabel;
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




    @FXML
    private void initialize() {
        ObservableList<Table> tables = FXCollections.observableArrayList();
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            int port = 8932;
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            InetSocketAddress serverAddress = new InetSocketAddress("192.168.10.80", port);
            String line = "getTable";
            Commands command = getCommand(line, channel, serverAddress, buffer, login, password);
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