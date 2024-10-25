module dev.pandemic.application.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires service;


    opens dev.pandemic.application to javafx.fxml;
    exports dev.pandemic.application;
}