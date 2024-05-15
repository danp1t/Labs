module org.example.lab8 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.lab8 to javafx.fxml;
    exports org.example.lab8;
}