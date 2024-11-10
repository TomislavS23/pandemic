module dev.pandemic.application.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires service;
    requires java.desktop;
    requires java.logging;


    opens dev.pandemic.application to javafx.fxml;
    exports dev.pandemic.application;
    exports dev.pandemic.application.controller;
    opens dev.pandemic.application.controller to javafx.fxml;
}