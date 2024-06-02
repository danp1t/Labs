module org.example.Client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.slf4j;
    requires java.sql;
    requires json.simple;
    requires com.google.gson;
    requires org.postgresql.jdbc;

    opens org.example.Client to javafx.fxml;
    exports org.example.Client;
}