package com.jd.inventorymanagement;

import com.jd.inventorymanagement.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class only handles creating initial data for the Inventory and launching the stage/program.
 * Improvements/Extensions:
    * None.
 * @author James Dunaway
 */

public class Main extends Application {
    /**
     * Create starter parts and products to fill the inventory.
     * This made the testing process much easier.
     * @param args (irrelevant to this program, as-is).
     */
    public static void main(String[] args) {
        InHouse part1 = new InHouse(1, "Part One", 5.99, 5, 1, 10, 5);
        Outsourced part2 = new Outsourced(2, "Part Two", 9.99, 6, 3, 6, "THQ");
        InHouse part3 = new InHouse(3, "Part Three", 19.99, 2, 1, 5, 7);
        Outsourced part4 = new Outsourced(4, "Part Four", 15.99, 3, 3, 5, "Franken");
        Outsourced part5 = new Outsourced(5, "Part Five", 1.99, 20, 5, 50, "Nestle");
        Outsourced part6 = new Outsourced(6, "Part Five: The Sequel", 10.99, 20, 5, 50, "Nestle");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);

        Product product1 = new Product(1, "Product One", 59.99, 3, 3, 5);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        Product product2 = new Product(2, "Product Two", 69.99, 3, 1, 7);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part3);
        Product product3 = new Product(3, "Product Three", 99.99, 1, 1, 3);
        product3.addAssociatedPart(part4);
        product3.addAssociatedPart(part5);
        Product product4 = new Product(4, "Product Three: Sequel", 999.99, 1, 1, 3);
        product4.addAssociatedPart(part4);
        product4.addAssociatedPart(part4);
        product4.addAssociatedPart(part5);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);

        launch();
    }

    /**
     * This start method is used to load the initial stage (main menu).
     * @param stage - the stage to load
     * @throws IOException - irrelevant
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view-main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
}