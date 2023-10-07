package com.jd.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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
         */
        mainWindow.setVisible(false);
        modPartWindow.setVisible(true);
    }
    //endregion

    //region Parts - Add
    @FXML
    private Label machineIdLabelAdd;

    @FXML
    private TextField machineIdTFieldAdd;

    @FXML
    private Label companyNameLabelAdd;

    @FXML
    private TextField companyNameTFieldAdd;

    @FXML
    void onAddPartsSave(ActionEvent event) {

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
        if (addPartWindow.isVisible() && machineIdLabelAdd.isVisible() ||
                modPartWindow.isVisible() && machineIdLabelMod.isVisible())
            return;

        if (addPartWindow.isVisible()) {
            companyNameLabelAdd.setVisible(false);
            companyNameTFieldAdd.setVisible(false);
            machineIdLabelAdd.setVisible(true);
            machineIdTFieldAdd.setVisible(true);
        } else if (modPartWindow.isVisible()) {
            companyNameLabelMod.setVisible(false);
            companyNameTFieldMod.setVisible(false);
            machineIdLabelMod.setVisible(true);
            machineIdTFieldMod.setVisible(true);
        }
    }

    @FXML
    void onOutsourcedClicked(ActionEvent event) {
        if (addPartWindow.isVisible() && companyNameLabelAdd.isVisible() ||
                modPartWindow.isVisible() && companyNameLabelMod.isVisible())
            return;

        if (addPartWindow.isVisible()) {
            machineIdLabelAdd.setVisible(false);
            machineIdTFieldAdd.setVisible(false);
            companyNameLabelAdd.setVisible(true);
            companyNameTFieldAdd.setVisible(true);
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

    }

    @FXML
    void onProductsDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onProductsModifyButtonClick(ActionEvent event) {

    }
    //endregion

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }
}