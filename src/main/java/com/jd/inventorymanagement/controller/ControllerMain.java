package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.Main;
import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Part;
import com.jd.inventorymanagement.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    void onPartSearchClicked(ActionEvent event) {
        if (partsSearchField.getText().isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
            return;
        }

        try {
            Integer.parseInt(partsSearchField.getText());

            Part searchedPart = Inventory.lookupPart(Integer.parseInt(partsSearchField.getText()) - 1);

            if (searchedPart == null) {
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }

            partsTableView.getSelectionModel().select(searchedPart);

        } catch (NumberFormatException e) {
            ObservableList<Part> searchedParts = Inventory.lookupPart(partsSearchField.getText());

            if(searchedParts.size() == 1) {
                partsTableView.getSelectionModel().select(searchedParts.get(0));
                return;
            }

            if (!searchedParts.isEmpty()) {
                partsTableView.setItems(searchedParts);
            } else {
                partsTableView.setItems(searchedParts);
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @FXML
    void onProductSearchClicked(ActionEvent event) {
        if (productsSearchField.getText().isEmpty()) {
            prodTableView.setItems(Inventory.getAllProducts());
            return;
        }

        try {
            Integer.parseInt(productsSearchField.getText());

            Product searchedProduct = Inventory.lookupProduct(Integer.parseInt(productsSearchField.getText()) - 1);

            if (searchedProduct == null) {
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }

            prodTableView.getSelectionModel().select(searchedProduct);

        } catch (NumberFormatException e) {
            ObservableList<Product> searchedProducts = Inventory.lookupProduct(productsSearchField.getText());

            if(searchedProducts.size() == 1) {
                prodTableView.getSelectionModel().select(searchedProducts.get(0));
                return;
            }

            if (!searchedProducts.isEmpty()) {
                prodTableView.setItems(searchedProducts);
            } else {
                prodTableView.setItems(searchedProducts);
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @FXML
    void onAddPartsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-add-part.fxml"));
        Parent root  = loader.load();

        ControllerAddPart addPart = loader.getController();
        addPart.SetPartId(Inventory.getAllParts().size() + 1);

        Stage stage = new Stage();
        stage.setTitle("Add New Part");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void onAddProductsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-add-product.fxml"));
        Parent root = loader.load();

        ControllerAddProduct addProduct = loader.getController();
        addProduct.SetProductId(Inventory.getAllProducts().size() + 1);

        Stage stage = new Stage();
        stage.setTitle("Add New Product");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    // At first I utilized an over-complicated try-catch method due to getting null errors. After trying different methods, I landed on this much more elegant method.
    @FXML
    void onModifyPartsButton(ActionEvent event) throws IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must first select a valid part.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-mod-part.fxml"));
        Parent root  = loader.load();

        ControllerModPart modPart = loader.getController();
        modPart.PopulatePartInfo(partsTableView.getSelectionModel().getSelectedItem().getId() - 1);

        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    // At first I utilized an over-complicated try-catch method due to getting null errors. After trying different methods, I landed on this much more elegant method.
    @FXML
    void onModifyProductsButton(ActionEvent event) throws IOException {
        Product selectedProduct = prodTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must first select a valid product.", "No Product Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-mod-product.fxml"));
        Parent root = loader.load();

        ControllerModProduct modProduct = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Modify Product");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void onDeletePartsButton(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must first select a valid part.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.lookupProduct(i).getAllAssociatedParts().contains(selectedPart)) {
                JOptionPane.showMessageDialog(new JFrame(), Inventory.lookupProduct(i ).getName() + " currently utilizes " + selectedPart.getName() + ", therefore, it cannot be deleted.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (CreateDeleteAlert("Are you sure you want to delete " + selectedPart.getName() + "?")) {
            partsTableView.getItems().remove(selectedPart);
        }
    }

    @FXML
    void onDeleteProductsButton(ActionEvent event) {
        Product selectedProduct = prodTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must first select a valid product.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (CreateDeleteAlert("Are you sure you want to delete " + selectedProduct.getName() + "?")) {
            prodTableView.getItems().remove(selectedProduct);
        }
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

    private boolean CreateDeleteAlert(String message) {
        Stage stage = (Stage) prodTableView.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;

        Alert deleteAlert = new Alert(type, "");

        deleteAlert.initModality(Modality.APPLICATION_MODAL);
        deleteAlert.initOwner(stage);

        deleteAlert.getDialogPane().setHeaderText("Delete");
        deleteAlert.getDialogPane().setContentText(message);

        Optional<ButtonType> result = deleteAlert.showAndWait();
        if(result.get() == ButtonType.OK)
        {

            return true;
        }
        else if (result.get() == ButtonType.CANCEL)
        {
            return false;
        }

        return false;
    }
}