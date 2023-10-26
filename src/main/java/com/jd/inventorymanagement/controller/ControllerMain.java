package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.Main;
import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Part;
import com.jd.inventorymanagement.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author James Dunaway
 */
public class ControllerMain implements Initializable {
    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TextField partsSearchField;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partLvlCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> prodTableView;

    @FXML
    private TextField productsSearchField;

    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    @FXML
    private TableColumn<Product, Integer> prodLvlCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    @FXML
    void onAddPartsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-add-part.fxml"));
        Parent root  = loader.load();

        ControllerAddPart addPart = loader.getController();
        addPart.SetPartId(Inventory.getAllParts().size() + 1);

        Stage stage = new Stage();
        stage.setTitle("Add New Part");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onAddProductsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-add-product.fxml"));
        Parent root = loader.load();

        ControllerAddProduct addProduct = loader.getController();
        addProduct.SetProductId(1);

        Stage stage = new Stage();
        stage.setTitle("Add New Product");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onModifyPartsButton(ActionEvent event) throws IOException {
        // FIXME: This is TOO much for what we need to accomplish.
        int selectedPart = 0;

        try {
            selectedPart = partsTableView.getSelectionModel().getSelectedItem().getId();
        } catch (NullPointerException e) {
            System.out.println("No modifiable part selected.");
        }

        if (selectedPart == 0) {
            return;
        } else {
            System.out.println("Now modifying " + selectedPart + " with ID # " + partsTableView.getSelectionModel().getSelectedItem().getId());
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-mod-part.fxml"));
        Parent root  = loader.load();

        ControllerModPart modPart = loader.getController();
        modPart.PopulatePartInfo(partsTableView.getSelectionModel().getSelectedItem().getId() - 1);

        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onModifyProductsButton(ActionEvent event) throws IOException {
        // TODO: Verify there is a product selected to modify

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-mod-product.fxml"));
        Parent root = loader.load();

        ControllerModProduct modProduct = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Modify Product");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onDeletePartsButton(ActionEvent event) {
        System.out.println("Delete Parts Menu");
    }

    @FXML
    void onDeleteProductsButton(ActionEvent event) {
        System.out.println("Delete Products Menu");
    }

    @FXML
    void onExitButton() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        prodTableView.setItems(Inventory.getAllProducts());

        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}