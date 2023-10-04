package com.jd.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
/**
 *
 * @author James Dunaway
 */
public class ViewController {

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
    private TableView<Part> tbParts;

    @FXML
    private TableView<Product> tbProducts;

    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }

    @FXML
    void onPartsAddButtonClick(ActionEvent event) {

    }

    @FXML
    void onPartsDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onPartsModifyButtonClick(ActionEvent event) {

    }

    @FXML
    void onProductsAddButtonClick(ActionEvent event) {

    }

    @FXML
    void onProductsDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onProductsModifyButtonClick(ActionEvent event) {

    }

}