package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.model.InHouse;
import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Outsourced;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;


/**
 * This class controls the logic/behavior of the mod part window.
 * It is largely the same as the add part class, with some minor changes.
 * Such as, replacing a part rather than adding a new one and populating the text-fields with initial data (to match the
 * part that is being modified.)
 * Improvement/Extension Ideas:
    * Implementing separate classes for methods reused across multiple classes.
    * The populatePartInfo method is a bit cumbersome and the IDE seems to believe there may be null pointer exceptions.
 * @author James Dunaway
 */
public class ControllerModPart {
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

    /**
     * This class handles the creation of the part object and utilizes it to replace the one provided by controller main.
     * First, it validates the required text fields meet the specified guidelines (using the ValidateTextFields() method).
     * Second, it converts the text field data into the appropriate types (e.g., int, double).
     * Third, it creates the appropriate object based on the current scene (InHouse vs. Outsourced) and used the
        * .updatePart() method to replace the old part in the inventory.
     * Lastly, it closes out the window.
     */
    @FXML
    void onModPartsSave() {
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

    /**
     * This class gets the stage reference, and closes it.
     * Errors/Issues:
     *      * In its first iteration, this method cleared out each text field. This was unnecessary as this class is
     *      * not holding any persistent data, therefore, it was removed.
     */
    @FXML
    void onModPartsCancel() {
        Stage stage = (Stage) modPartWindow.getScene().getWindow();
        stage.close();
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
     * This method utilizes the given partId to get the existing part data and use that data to populate the text-fields.
     * First, blank reference objects are created for both Outsourced and InHouse.
     * Second, Inventory.lookupPart() is used to get the class type (either InHouse or Outsourced).
     * Third, that part is then assigned to the proper reference object and the corresponding radio button is activated.
     * Fourth, all the text-fields are populated with data pulled directly from the part object, utilizing the reference.
     * @param partId - The partID is provided by the ControllerMain class. It is used to find the part and get the data.
     */
    public void PopulatePartInfo(int partId) {
        Outsourced outsourced;
        InHouse inHouse;

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

    /**
     * This class validates each text field to ensure it meets the programs requirements.
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