module dev.pandemic.pandemic {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires jakarta.xml.bind;
    requires java.sql;
    requires modelmapper;
    requires java.rmi;
    requires java.management;


    opens dev.pandemic to javafx.fxml;
    opens dev.pandemic.controller;
    opens dev.pandemic.model to jakarta.xml.bind;
    opens dev.pandemic.dto to jakarta.xml.bind;
    exports dev.pandemic;
    exports dev.pandemic.controller;
    exports dev.pandemic.enumerations;
    exports dev.pandemic.model;
    exports dev.pandemic.networking.rmi;
}