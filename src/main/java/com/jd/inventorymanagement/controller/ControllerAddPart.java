package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.model.InHouse;
import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Outsourced;
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
        if (!ValidateTextFields()) {
            return;
        }

        int id = Integer.parseInt(partIdTxt.getText());
        String name = partNameTxt.getText();
        double price = Double.parseDouble(partPriceTxt.getText());
        int lvl = Integer.parseInt(partLvlTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());

        if (!ValidateInventoryLvl(min, lvl, max)) {
            return;
        }

        if (partMachIdTxt.isVisible()) {
            int machId = Integer.parseInt(partMachIdTxt.getText());

            InHouse newPart = new InHouse(id, name, price, lvl, min, max, machId);
            Inventory.addPart(newPart);
        } else {
            String compName = partCompNameTxt.getText();

            Outsourced newPart = new Outsourced(id, name, price, lvl, min, max, compName);
            Inventory.addPart(newPart);
        }

        Stage stage = (Stage) addPartWindow.getScene().getWindow();
        stage.close();
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

    // Attempted to validate inputs with try-catch but couldn't get it to work correctly. This method of utilizing the .matches() with regex works but took more effort.
    private boolean ValidateTextFields() {
        // TODO: Implement proper error dialog
        if (!partLvlTxt.getText().matches("\\d+")) {
            System.out.println("Inventory level should be a whole number.");
            partLvlTxt.setText("");
            return false;
        }

        // TODO: Implement proper error dialog
        if (!partPriceTxt.getText().matches("\\d{1,2}\\.\\d{1,2}")) {
            System.out.println("Price should be a number with up to two decimal places.");
            partPriceTxt.setText("");
            return false;
        }

        // TODO: Implement proper error dialog
        if (!partMinTxt.getText().matches("\\d+")) {
            System.out.println("Min should be a whole number.");
            partMinTxt.setText("");
            return false;
        }

        // TODO: Implement proper error dialog
        if (!partMaxTxt.getText().matches("\\d+")) {
            System.out.println("Max should be a whole number.");
            partMaxTxt.setText("");
            return false;
        }

        // TODO: Implement proper error dialog
        if (partMachIdTxt.isVisible()) {
            if (!partMachIdTxt.getText().matches("\\d+")) {
                System.out.println("Machine ID should be a whole number.");
                partMachIdTxt.setText("");
                return false;
            }
        }

        return true;
    }

    private boolean ValidateInventoryLvl(int min, int lvl, int max) {
        // TODO: Implement proper error dialog
        if (min > lvl || min > max) {
            System.out.println("Min cannot be greater than the current or max inventory level.");
            return false;
        }

        // TODO: Implement proper error dialog
        if (lvl > max) {
            System.out.println("Inventory level should be between the Min and Max values.");
            return false;
        }

        return true;
    }
}