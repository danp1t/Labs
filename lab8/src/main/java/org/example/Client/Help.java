package org.example.Client;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.example.Client.Authorization.language;

public class Help {
    @FXML
    private MenuButton TextLanguage;
    @FXML
    private Text add;
    @FXML
    private Text helpMain;
    @FXML
    private Text addIfMin;
    @FXML
    private Text addIfMin2;
    @FXML
    private Text execute;
    @FXML
    private Text show;
    @FXML
    private Text clear;
    @FXML
    private Text update;
    @FXML
    private Text remove;
    @FXML
    private Text addIfMax;
    @FXML
    private Text addIfMax2;
    @FXML
    private Text history;
    @FXML
    private Text count;
    @FXML
    private Text count2;
    @FXML
    private Text help;
    @FXML
    private Text exit;
    @FXML
    private Text filter;
    @FXML
    private Text filter2;
    @FXML
    private Text minBySem;
    @FXML
    private Text minBySem2;
    @FXML
    private Text info;

    @FXML
    private void initialize() {
        Locale locale = new Locale(language); // Создаем объект Locale для выбранного языка
        ResourceBundle bundle = ResourceBundle.getBundle("locales/gui", locale); // Загружаем ресурсы для выбранного языка
        String helpMainMessage = bundle.getString("main_help");
        String addMessage = bundle.getString("add_help");
        String addMin = bundle.getString("add_min_help");
        String addMin2 = bundle.getString("add_min_help2");
        String executeMessage = bundle.getString("execute_help");
        String showMessage = bundle.getString("show_help");
        String clearMessage = bundle.getString("clear_help");
        String updateMessage = bundle.getString("update_help");
        String removeMessage = bundle.getString("remove_help");
        String addMax = bundle.getString("add_max_help");
        String addMax2 = bundle.getString("add_max_help2");
        String historyMessage = bundle.getString("history_help");
        String countMessage = bundle.getString("count_help");
        String count2Message = bundle.getString("count_help2");
        String helpMessage = bundle.getString("help_help");
        String exitMessage = bundle.getString("exit_help");
        String filterMessage = bundle.getString("filter_help");
        String filterMessage2 = bundle.getString("filter_help2");
        String minSem = bundle.getString("min_sem_help");
        String minSem2 = bundle.getString("min_sem_help2");
        String infoMessage = bundle.getString("info_help");


        // Здесь вы можете установить тексты согласно загруженным ресурсам
        helpMain.setText(helpMainMessage);
        add.setText(addMessage);
        addIfMin.setText(addMin);
        addIfMin2.setText(addMin2);
        execute.setText(executeMessage);
        show.setText(showMessage);
        clear.setText(clearMessage);
        update.setText(updateMessage);
        remove.setText(removeMessage);
        addIfMax.setText(addMax);
        addIfMax2.setText(addMax2);
        history.setText(historyMessage);
        count.setText(countMessage);
        count2.setText(count2Message);
        help.setText(helpMessage);
        exit.setText(exitMessage);
        filter.setText(filterMessage);
        filter2.setText(filterMessage2);
        minBySem.setText(minSem);
        minBySem2.setText(minSem2);
        info.setText(infoMessage);

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
        ResourceBundle bundle = ResourceBundle.getBundle("locales/gui", locale); // Загружаем ресурсы для выбранного языка

        // Пример использования ресурсов из файла свойств
        String helpMainMessage = bundle.getString("main_help");
        String addMessage = bundle.getString("add_help");
        String addMin = bundle.getString("add_min_help");
        String addMin2 = bundle.getString("add_min_help2");
        String executeMessage = bundle.getString("execute_help");
        String showMessage = bundle.getString("show_help");
        String clearMessage = bundle.getString("clear_help");
        String updateMessage = bundle.getString("update_help");
        String removeMessage = bundle.getString("remove_help");
        String addMax = bundle.getString("add_max_help");
        String addMax2 = bundle.getString("add_max_help2");
        String historyMessage = bundle.getString("history_help");
        String countMessage = bundle.getString("count_help");
        String count2Message = bundle.getString("count_help2");
        String helpMessage = bundle.getString("help_help");
        String exitMessage = bundle.getString("exit_help");
        String filterMessage = bundle.getString("filter_help");
        String filterMessage2 = bundle.getString("filter_help2");
        String minSem = bundle.getString("min_sem_help");
        String minSem2 = bundle.getString("min_sem_help2");
        String infoMessage = bundle.getString("info_help");


        // Здесь вы можете установить тексты согласно загруженным ресурсам
        helpMain.setText(helpMainMessage);
        add.setText(addMessage);
        addIfMin.setText(addMin);
        addIfMin2.setText(addMin2);
        execute.setText(executeMessage);
        show.setText(showMessage);
        clear.setText(clearMessage);
        update.setText(updateMessage);
        remove.setText(removeMessage);
        addIfMax.setText(addMax);
        addIfMax2.setText(addMax2);
        history.setText(historyMessage);
        count.setText(countMessage);
        count2.setText(count2Message);
        help.setText(helpMessage);
        exit.setText(exitMessage);
        filter.setText(filterMessage);
        filter2.setText(filterMessage2);
        minBySem.setText(minSem);
        minBySem2.setText(minSem2);
        info.setText(infoMessage);
        Authorization.language = language;
    }
}
