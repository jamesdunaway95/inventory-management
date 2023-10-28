package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.model.InHouse;
import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * @author James Dunaway
 */
public class ControllerModPart implements Initializable {
    @FXML
    private AnchorPane modPartWindow;

    @FXML
    private RadioButton inHouseRad;

    @FXML
    private RadioButton outsourcedRad;

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

    @FXML
    void onModPartsSave(ActionEvent event) {
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

            InHouse updatedPart = new InHouse(id, name, price, lvl, min, max, machId);
            Inventory.updatePart(id - 1, updatedPart);
        } else {
            String compName = partCompNameTxt.getText();

            Outsourced updatedPart = new Outsourced(id, name, price, lvl, min, max, compName);
            Inventory.updatePart(id - 1, updatedPart);
        }

        Stage stage = (Stage) modPartWindow.getScene().getWindow();
        stage.close();
    }

    // Had to change the hierarchy made in scene builder in order to get this work (seemed like the most efficient way).
    @FXML
    void onModPartsCancel(ActionEvent event) {
        for (Node node : modPartWindow.getChildren()) {
            if (node instanceof TextField) {
                ((TextField)node).setText(""); // Clear text
            }
        }

        Stage stage = (Stage) modPartWindow.getScene().getWindow();
        stage.close();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void PopulatePartInfo(int partId) {
        Outsourced outsourced = null;
        InHouse inHouse = null;

        if (Inventory.lookupPart(partId).getClass().equals(InHouse.class)) {
            inHouseRad.fire();
            inHouse = (InHouse) Inventory.lookupPart(partId);

            partIdTxt.setText(String.valueOf(inHouse.getId()));
            partNameTxt.setText(inHouse.getName());
            partLvlTxt.setText(String.valueOf(inHouse.getStock()));
            partPriceTxt.setText(String.valueOf(inHouse.getPrice()));
            partMaxTxt.setText(String.valueOf(inHouse.getMax()));
            partMinTxt.setText(String.valueOf(inHouse.getMin()));
            partMachIdTxt.setText(String.valueOf(inHouse.getMachineId()));
        } else {
            outsourcedRad.fire();
            outsourced = (Outsourced) Inventory.lookupPart(partId);

            partIdTxt.setText(String.valueOf(outsourced.getId()));
            partNameTxt.setText(outsourced.getName());
            partLvlTxt.setText(String.valueOf(outsourced.getStock()));
            partPriceTxt.setText(String.valueOf(outsourced.getPrice()));
            partMaxTxt.setText(String.valueOf(outsourced.getMax()));
            partMinTxt.setText(String.valueOf(outsourced.getMin()));
            partCompNameTxt.setText(outsourced.getCompanyName());
        }
    }

    private boolean ValidateTextFields() {
        if (!partLvlTxt.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Inventory level must be a whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            partLvlTxt.setText("");
            return false;
        }

        if (!partPriceTxt.getText().matches("\\d{1,6}\\.\\d{1,2}")) {
            JOptionPane.showMessageDialog(new JFrame(), "Price must be a number with up to two decimal places, i.e., $$.$ or $$.$$", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            partPriceTxt.setText("");
            return false;
        }

        if (!partMinTxt.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Min must be a whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            partMinTxt.setText("");
            return false;
        }

        if (!partMaxTxt.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Max must be a whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            partMaxTxt.setText("");
            return false;
        }

        if (partMachIdTxt.isVisible()) {
            if (!partMachIdTxt.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(new JFrame(), "Machine ID must be a whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                partMachIdTxt.setText("");
                return false;
            }
        }

        return true;
    }

    private boolean ValidateInventoryLvl(int min, int lvl, int max) {
        if (min > lvl || min > max) {
            JOptionPane.showMessageDialog(new JFrame(), "Min cannot be greater than the current or max inventory level.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (lvl > max) {
            JOptionPane.showMessageDialog(new JFrame(), "Inventory level must fall between the Min and Max values.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}