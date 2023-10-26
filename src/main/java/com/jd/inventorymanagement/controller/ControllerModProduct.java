package com.jd.inventorymanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * @author James Dunaway
 */
public class ControllerModProduct implements Initializable {


    @FXML
    private AnchorPane modProdWindow;

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

        // modProdWindowContainer.setVisible(false);
        // mainWindow.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}