package com.jd.inventorymanagement;

import com.jd.inventorymanagement.model.InHouse;
import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Outsourced;
import com.jd.inventorymanagement.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 *
 * @author James Dunaway
 */

public class Main extends Application {
    public static void main(String[] args) {
        InHouse part1 = new InHouse(1, "Part One", 5.99, 5, 1, 10, 5);
        Outsourced part2 = new Outsourced(2, "Part Two", 9.99, 6, 3, 6, "THQ");
        InHouse part3 = new InHouse(3, "Part Three", 19.99, 2, 1, 5, 7);
        Outsourced part4 = new Outsourced(4, "Part Four", 15.99, 3, 3, 5, "Franken");
        Outsourced part5 = new Outsourced(5, "Part Five", 1.99, 20, 5, 50, "Nestle");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        Product product1 = new Product(1, "Product One", 59.99, 3, 3, 5);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        Product product2 = new Product(2, "Product Two", 69.99, 3, 1, 7);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        Product product3 = new Product(3, "Product Three", 99.99, 1, 1, 3);
        product1.addAssociatedPart(part4);
        product1.addAssociatedPart(part5);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view-main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}