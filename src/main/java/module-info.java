module dev.pandemic.pandemic {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires jakarta.xml.bind;


    opens dev.pandemic to javafx.fxml;
    opens dev.pandemic.controller;
    opens dev.pandemic.model to jakarta.xml.bind;
    exports dev.pandemic;
    exports dev.pandemic.controller;
    exports dev.pandemic.enumerations;
}