package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.model.Part;
import com.jd.inventorymanagement.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * @author James Dunaway
 */
public class ControllerAddProduct implements Initializable {
    @FXML
    private AnchorPane addProdTFields;

    @FXML
    private TextField prodIdTxt;

    @FXML
    private TextField prodInvTxt;

    @FXML
    private TextField prodMaxTxt;

    @FXML
    private TextField prodMinTxt;

    @FXML
    private TextField prodNameTxt;

    @FXML
    private TextField prodPriceTxt;

    public void SetProductId(int newId) {
        this.prodIdTxt.setText(String.valueOf(newId));
    }

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

        //addProdWindow.setVisible(false);
        //mainWindow.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}