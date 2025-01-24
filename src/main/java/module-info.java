module dev.pandemic.pandemic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens dev.pandemic to javafx.fxml;
    opens dev.pandemic.controller;
    exports dev.pandemic;
    exports dev.pandemic.controller;
}