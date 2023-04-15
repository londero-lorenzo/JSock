module start {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens graphics.start to javafx.fxml;
    exports graphics.start;
    opens graphics.controllers to javafx.fxml;
    exports graphics.controllers;
}