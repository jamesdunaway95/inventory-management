package com.jd.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableColumn<?, ?> productId;

    @FXML
    private TableColumn<?, ?> productLvl;

    @FXML
    private TableColumn<?, ?> productName;

    @FXML
    private TableColumn<?, ?> productPrice;

    @FXML
    private TextField productsSearchField;

    @FXML
    private TableView<?> tbParts;

    @FXML
    private TableView<?> tbProducts;

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