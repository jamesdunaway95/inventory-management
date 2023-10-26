package com.jd.inventorymanagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * @author James Dunaway
 */
public class ControllerAddPart implements Initializable {
    @FXML
    private AnchorPane addPartWindow;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partLvlTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private Label partMachIdLbl;

    @FXML
    private TextField partMachIdTxt;

    @FXML
    private Label partCompNameLbl;

    @FXML
    private TextField partCompNameTxt;

    public void SetPartId(int newId) {
        this.partIdTxt.setText(String.valueOf(newId));
    }

    @FXML
    void onInHouseClicked(ActionEvent actionEvent) {
        partMachIdLbl.setVisible(true);
        partMachIdTxt.setVisible(true);

        partCompNameLbl.setVisible(false);
        partCompNameTxt.setVisible(false);
    }

    @FXML
    void onOutsourcedClicked(ActionEvent actionEvent) {
        partCompNameLbl.setVisible(true);
        partCompNameTxt.setVisible(true);

        partMachIdLbl.setVisible(false);
        partMachIdTxt.setVisible(false);
    }

    @FXML
    void onAddPartsSave(ActionEvent event) {
        // TODO: Validate fields

        // TODO: Create the appropriate object, either InHouse or Outsourced

        // TODO: increment part ID #

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

        Stage stage = (Stage) addPartWindow.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}