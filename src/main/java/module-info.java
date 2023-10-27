module com.jd.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.jd.inventorymanagement to javafx.fxml;
    exports com.jd.inventorymanagement;
    exports com.jd.inventorymanagement.controller;
    opens com.jd.inventorymanagement.controller to javafx.fxml;
    exports com.jd.inventorymanagement.model;
    opens com.jd.inventorymanagement.model to javafx.fxml;
}