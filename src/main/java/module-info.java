module com.jd.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jd.inventorymanagement to javafx.fxml;
    exports com.jd.inventorymanagement;
}