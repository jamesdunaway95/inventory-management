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
        System.out.println("Add parts button clicked");
        mainWindow.setVisible(false);
        addPartWindow.setVisible(true);
    }

    @FXML
    void onPartsDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onPartsModifyButtonClick(ActionEvent event) {
    }
    //endregion

    //region Parts - Add
    @FXML
    private Label machineIdLabel;

    @FXML
    private TextField machineIdTField;

    @FXML
    private Label companyNameLabel;

    @FXML
    private TextField companyNameTField;

    @FXML
    void onInHouseClicked(ActionEvent event) {
        if (machineIdLabel.isVisible()) {
            return;
        }
        else {
            companyNameLabel.setVisible(false);
            companyNameTField.setVisible(false);
            machineIdLabel.setVisible(true);
            machineIdTField.setVisible(true);
        }
    }

    @FXML
    void onOutsourcedClicked(ActionEvent event) {
        if (companyNameLabel.isVisible()) {
            return;
        }
        else {
            machineIdLabel.setVisible(false);
            machineIdTField.setVisible(false);
            companyNameLabel.setVisible(true);
            companyNameTField.setVisible(true);
        }
    }

    @FXML
    void onAddPartsSave(ActionEvent event) {

    }

    @FXML
    void onAddPartsCancel(ActionEvent event) {
        for (Node node : addPartWindow.getChildren()) {
            System.out.println("Id: " + node.getId());
            if (node instanceof TextField) {
                ((TextField)node).setText(""); // Clear text
            }
        }

        addPartWindow.setVisible(false);
        mainWindow.setVisible(true);
    }
    //endregion

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