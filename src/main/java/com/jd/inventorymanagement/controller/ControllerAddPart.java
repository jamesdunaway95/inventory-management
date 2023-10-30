package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.model.InHouse;
import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Outsourced;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;


/**
 * This class controls the logic/behavior of the add part window.
 * Since this class does not hold any persistent data, it merely creates a new part object, then adds it to the
 * inventory class.
 * Improvement/Extension Ideas:
    * Implementing separate classes for methods reused across multiple classes.
    * The current error delivery method is a bit messy. Could be improved with a dedicated class or method and cleaner methods.
    * A cleaner/simpler way to determine if a product is InHouse or Outsourced. Using the visibility of the objects in the scene works but seems messy.
    * A more complex system could benefit from a more complex ID generator.
 * @author James Dunaway
 */
public class ControllerAddPart {
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

    /**
     * This public method was created so the main controller can pass the ID, since the main controller is persistent.
     * @param newId - The generated part id that will be used for the new part.
     */
    public void SetPartId(int newId) {
        this.partIdTxt.setText(String.valueOf(newId));
    }

    /**
     * This method shows the part machine objects and hides the company name objects when the radio button is clicked.
     */
    @FXML
    void onInHouseClicked() {
        partMachIdLbl.setVisible(true);
        partMachIdTxt.setVisible(true);

        partCompNameLbl.setVisible(false);
        partCompNameTxt.setVisible(false);
    }

    /**
     * This method shows the company name objects and hides the part machine objects when the radio button is clicked.
     */
    @FXML
    void onOutsourcedClicked() {
        partCompNameLbl.setVisible(true);
        partCompNameTxt.setVisible(true);

        partMachIdLbl.setVisible(false);
        partMachIdTxt.setVisible(false);
    }

    /**
     * This class handles the creation of the part object and adds said object to the inventory class.
     * First, it validates the required text fields meet the specified guidelines (using the ValidateTextFields() method).
     * Second, it converts the text field data into the appropriate types (e.g., int, double).
     * Third, it creates the appropriate object based on the current scene (InHouse vs. Outsourced) and adds it to the
     * inventory.
     * Lastly, it closes out the window.
     */
    @FXML
    void onAddPartsSave() {
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

    /**
     * This class gets the stage reference, and closes it.
     * Errors/Issues:
     *      * In its first iteration, this method cleared out each text field. This was unnecessary as this class is
     *      * not holding any persistent data, therefore, it was removed.
     */
    @FXML
    void onAddPartsCancel() {
        Stage stage = (Stage) addPartWindow.getScene().getWindow();
        stage.close();
    }

    /**
     * This class validates each text field to ensure it meets the programs requirements using .matches() with regex.
     * Errors/Issues:
     *      * At first, I was utilizing built in java methods such as parseInt inside try-catch but couldn't get it to
     *      * work properly. This led me to utilize .matches() with regex specifications.
     *
     * @return true - all text fields meet requirements, false - one or more text fields does not meet requirements.
     */
    private boolean ValidateTextFields() {
        if (partNameTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Name cannot be blank.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

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

        if (partCompNameTxt.isVisible()) {
            if (partCompNameTxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(new JFrame(), "Company name cannot be blank.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    /**
     * This class verifies that the inventory levels logic are not outside of program guidelines.
     * @param min - the minimum inventory
     * @param lvl - the current inventory
     * @param max - the maximum inventory
     * @return - true if there are no logical inconsistencies - false, if there are one or more.
     */
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