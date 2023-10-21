package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.model.Part;
import com.jd.inventorymanagement.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


/**
 *
 * @author James Dunaway
 */
public class ViewController {
    @FXML
    private BorderPane mainWindow;

    @FXML
    private AnchorPane addPartWindow;

    @FXML
    private AnchorPane modPartWindow;

    @FXML
    private AnchorPane addProdWindow;

    @FXML
    private AnchorPane modProdWindow;

    //region Parts - Main
    @FXML
    private TableView<Part> tbParts;

    @FXML
    private TableColumn<Part, Integer> partId;

    @FXML
    private TableColumn<Part, Integer> partLvl;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private TextField partsSearchField;

    @FXML
    void onPartsAddButtonClick(ActionEvent event) {
        mainWindow.setVisible(false);
        addPartWindow.setVisible(true);
    }

    @FXML
    void onPartsDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onPartsModifyButtonClick(ActionEvent event) {
        /**
         * TODO: Check that a modifiable part is selected.
         * TODO: Pre-select the appropriate radio button based on current part (in-house, outsourced)
         */
        mainWindow.setVisible(false);
        modPartWindow.setVisible(true);
    }
    //endregion

    //region Parts - Add
    @FXML
    private TextField addPartIdTField;

    @FXML
    private TextField addPartNameTField;

    @FXML
    private TextField addPartLvlTField;

    @FXML
    private TextField addPartPriceTField;

    @FXML
    private TextField addPartMaxTField;

    @FXML
    private TextField addPartMinTField;

    @FXML
    private Label addPartMachIdLabel;

    @FXML
    private TextField addPartMachIdTField;

    @FXML
    private Label addPartCompNameLabel;

    @FXML
    private TextField addPartCompNameTField;

    @FXML
    void onAddPartsSave(ActionEvent event) {
        // TODO: Generate unique ID number

        // TODO: Validate fields
        boolean isInHouse = addPartMachIdLabel.isVisible();

        // TODO: Create the appropriate object, either InHouse or Outsourced, and generate a part ID #


        // TODO: Add new part to part main part table.
    }

    // Had to change the hierarchy made in scene builder in order to get this work (seemed like the most efficient way).
    @FXML
    void onAddPartsCancel(ActionEvent event) {
        for (Node node : addPartWindow.getChildren()) {
            if (node instanceof TextField) {
                ((TextField)node).setText(""); // Clear text
            }
        }

        addPartWindow.setVisible(false);
        mainWindow.setVisible(true);
    }
    //endregion

    //region Parts - Modify
    /**
     * TODO: Add logic to insert part numbers ID into this text field.
     */
    @FXML
    private TextField modifyPartIdTField;

    @FXML
    private Label machineIdLabelMod;

    @FXML
    private TextField machineIdTFieldMod;

    @FXML
    private Label companyNameLabelMod;

    @FXML
    private TextField companyNameTFieldMod;

    @FXML
    void onModPartsSave(ActionEvent event) {

    }

    // Had to change the hierarchy made in scene builder in order to get this work (seemed like the most efficient way).
    @FXML
    void onModPartsCancel(ActionEvent event) {
        for (Node node : modPartWindow.getChildren()) {
            if (node instanceof TextField) {
                ((TextField)node).setText(""); // Clear text
            }
        }

        modPartWindow.setVisible(false);
        mainWindow.setVisible(true);
    }
    //endregion

    @FXML
    void onInHouseClicked(ActionEvent event) {
        if (addPartWindow.isVisible() && addPartMachIdLabel.isVisible() ||
                modPartWindow.isVisible() && machineIdLabelMod.isVisible())
            return;

        if (addPartWindow.isVisible()) {
            addPartCompNameLabel.setVisible(false);
            addPartCompNameTField.setVisible(false);
            addPartMachIdLabel.setVisible(true);
            addPartMachIdTField.setVisible(true);
        } else if (modPartWindow.isVisible()) {
            companyNameLabelMod.setVisible(false);
            companyNameTFieldMod.setVisible(false);
            machineIdLabelMod.setVisible(true);
            machineIdTFieldMod.setVisible(true);
        }
    }

    @FXML
    void onOutsourcedClicked(ActionEvent event) {
        if (addPartWindow.isVisible() && addPartCompNameLabel.isVisible() ||
                modPartWindow.isVisible() && companyNameLabelMod.isVisible())
            return;

        if (addPartWindow.isVisible()) {
            addPartMachIdLabel.setVisible(false);
            addPartMachIdTField.setVisible(false);
            addPartCompNameLabel.setVisible(true);
            addPartCompNameTField.setVisible(true);
        } else if (modPartWindow.isVisible()) {
            machineIdLabelMod.setVisible(false);
            machineIdTFieldMod.setVisible(false);
            companyNameLabelMod.setVisible(true);
            companyNameTFieldMod.setVisible(true);
        }
    }

    //region Products - Main
    @FXML
    private TableView<Product> tbProducts;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, Integer> productLvl;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Double> productPrice;

    @FXML
    private TextField productsSearchField;

    @FXML
    void onProductsAddButtonClick(ActionEvent event) {
        mainWindow.setVisible(false);
        addProdWindow.setVisible(true);
    }

    @FXML
    void onProductsDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onProductsModifyButtonClick(ActionEvent event) {
        mainWindow.setVisible(false);
        modProdWindow.setVisible(true);
    }
    //endregion

    //region Products - Add
    @FXML
    private AnchorPane addProdTFields;

    public void onAddProdPartsAddButtonClick(ActionEvent actionEvent) {
    }

    public void onAddProdRemovePartClicked(ActionEvent actionEvent) {
    }

    public void onAddProdSaveClicked(ActionEvent actionEvent) {
    }

    public void onAddProdCancel(ActionEvent actionEvent) {
        for (Node node : addProdTFields.getChildren()) {
            if (node instanceof TextField) {
                ((TextField)node).setText(""); // Clear text
            }
        }

        addProdWindow.setVisible(false);
        mainWindow.setVisible(true);
    }
    //endregion

    //region Products - Mod
    @FXML
    private AnchorPane modProdTFields;

    public void onModProdPartsAddButtonClick(ActionEvent actionEvent) {
    }

    public void onModProdRemovePartClicked(ActionEvent actionEvent) {
    }

    public void onModProdSaveClicked(ActionEvent actionEvent) {
    }

    public void onModProdCancel(ActionEvent actionEvent) {
        for (Node node : modProdTFields.getChildren()) {
            if (node instanceof TextField) {
                ((TextField)node).setText(""); // Clear text
            }
        }

        modProdWindow.setVisible(false);
        mainWindow.setVisible(true);
    }
    //endregion

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }
}