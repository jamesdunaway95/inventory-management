package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Part;
import com.jd.inventorymanagement.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This class controls the behavior/logic of the Add Product menu.
 * It merely creates a new product based on the data entered by the user, assigns the associated parts, if applicable,
 * and adds it to the Inventory.
 * Improvement/Extension Ideas:
    * Implementing separate classes for methods reused across multiple classes.
    * The current error delivery method is a bit messy. Could be improved with a dedicated class or method and cleaner methods.
    * The IDE doesn't seem to like my local observable list. I don't know if this is an IDE thing or a Java thing but this made the most sense to me. Maybe there is a better way handle it though.
    * A more complex system could benefit from a more complex ID generator.
 * @author James Dunaway
 */
public class ControllerAddProduct implements Initializable {
    @FXML
    private TextField partsSearchField;

    @FXML
    private AnchorPane addProdWindow;

    @FXML
    private TableColumn<Part, Integer> availPartsLvlCol;

    @FXML
    private TableColumn<Part, Integer> availPartsPartIdCol;

    @FXML
    private TableColumn<Part, String> availPartsPartNameCol;

    @FXML
    private TableColumn<Part, Double> availPartsPriceCol;

    @FXML
    private TableView<Part> availPartsTableView;

    @FXML
    private TableColumn<Part, Integer> currPartsLvlCol;

    @FXML
    private TableColumn<Part, Integer> currPartsPartIdCol;

    @FXML
    private TableColumn<Part, String> currPartsPartNameCol;

    @FXML
    private TableColumn<Part, Double> currPartsPriceCol;

    @FXML
    private TableView<Part> currPartsTableView;

    @FXML
    private TextField prodIdTxt;

    @FXML
    private TextField prodLvlTxt;

    @FXML
    private TextField prodMaxTxt;

    @FXML
    private TextField prodMinTxt;

    @FXML
    private TextField prodNameTxt;

    @FXML
    private TextField prodPriceTxt;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This public method was created so the main controller can pass the ID, since the main controller is persistent.
     * @param newId - The generated product id that will be used for the new product.
     */
    public void SetProductId(int newId) {
        this.prodIdTxt.setText(String.valueOf(newId));
    }

    /**
     * This method utilizes the parts search text field to manipulate and filter the parts table.
     * Errors/Issues:
     *      * The search by string function was implemented first. When implementing the search by ID functionality, I ran
     *      * into null reference issues. To resolve this, I utilized the try - catch methods to see if the search field
     *      * contents contained a valid integer, if not, the function searches via string.
     */
    @FXML
    void onPartSearchClicked() {
        if (partsSearchField.getText().isEmpty()) {
            availPartsTableView.setItems(Inventory.getAllParts());
            return;
        }

        try {
            Integer.parseInt(partsSearchField.getText());

            Part searchedPart = Inventory.lookupPart(Integer.parseInt(partsSearchField.getText()) - 1);

            if (searchedPart == null) {
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }

            availPartsTableView.getSelectionModel().select(searchedPart);

        } catch (NumberFormatException e) {
            ObservableList<Part> searchedParts = Inventory.lookupPart(partsSearchField.getText());

            if(searchedParts.size() == 1) {
                availPartsTableView.getSelectionModel().select(searchedParts.get(0));
                return;
            }

            if (!searchedParts.isEmpty()) {
                availPartsTableView.setItems(searchedParts);
            } else {
                availPartsTableView.setItems(searchedParts);
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method checks if a part is selected, if so, it adds that part to the local associate parts list, otherwise,
     * it will generate an error dialogue.
     */
    @FXML
    void onAddPartClicked() {
        Part selectedPart = availPartsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must select a valid part in order to add it.", "No Part Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        associatedParts.add(selectedPart);
    }

    /**
     * This class gets the stage reference, and closes it.
     * Errors/Issues:
     *      * In its first iteration, this method cleared out each text field. This was unnecessary as this class is
     *      * not holding any persistent data, therefore, it was removed.
     */
    @FXML
    void onCancelProdClicked() {
        Stage stage = (Stage) addProdWindow.getScene().getWindow();
        stage.close();
    }

    /**
     * This class checks if a part is selected on the associated parts table, if not, it creates an error dialogue.
     * If there is, a confirmation dialogue is created; based on the users response, it will remove the part from the
     * table, or it will leave the method, cancelling the removal.
     * Errors/Issues:
        * Originally, I misread the prompt and did not see there should be a confirmation dialogue here, so it was
        * not included.
     */
    @FXML
    void onRemoveAssociatedPartClicked() {
        Part selectedPart = currPartsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must select a valid part in order to remove it.", "No Part Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!CreateRemoveAlert("Are you sure you want to remove " + selectedPart.getName() + " from this product?")) {
            return;
        }

        associatedParts.remove(selectedPart);
    }

    /**
     * This class handles the creation of the product object and adds said object to the inventory class.
     * First, it validates the required text fields meet the specified guidelines (using the ValidateTextFields() method).
     * Second, it converts the text field data into the appropriate types (e.g., int, double).
     * Third, it creates the new product object with the initial data, then adds any parts found in the local
        * associated parts table.
     * Lastly, it adds the product to the inventory, then closes out the window.
     */
    @FXML
    void onSaveProdClicked() {
        if (!ValidateTextFields()) {
            return;
        }

        int id = Integer.parseInt(prodIdTxt.getText());
        String name = prodNameTxt.getText();
        double price = Double.parseDouble(prodPriceTxt.getText());
        int lvl = Integer.parseInt(prodLvlTxt.getText());
        int min = Integer.parseInt(prodMinTxt.getText());
        int max = Integer.parseInt(prodMaxTxt.getText());

        if (!ValidateInventoryLvl(min, lvl, max)) {
            return;
        }

        Product newProduct = new Product(id, name, price, lvl, min, max);

        for (Part part : associatedParts) {
            newProduct.addAssociatedPart(part);
        }

        Inventory.addProduct(newProduct);

        Stage stage = (Stage) addProdWindow.getScene().getWindow();
        stage.close();
    }

    /**
     * This method is used to set up and populate the parts and products tables.
     * The associated parts table is populated using a locally owned parts list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availPartsTableView.setItems(Inventory.getAllParts());

        availPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availPartsLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        currPartsTableView.setItems(associatedParts);

        currPartsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        currPartsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        currPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        currPartsLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * This class validates each text field to ensure it meets the programs requirements.
     * Errors/Issues:
     *      * At first, I was utilizing built in java methods such as parseInt inside try-catch but couldn't get it to
     *      * work properly. This led me to utilize .matches() with regex specifications.
     * @return true - all text fields meet requirements, false - one or more text fields does not meet requirements.
     */
    private boolean ValidateTextFields() {
        if (prodNameTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Name cannot be blank.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!prodLvlTxt.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Inventory level must be a whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            prodLvlTxt.setText("");
            return false;
        }

        if (!prodPriceTxt.getText().matches("\\d{1,6}\\.\\d{1,2}")) {
            JOptionPane.showMessageDialog(new JFrame(), "Price must be a number with up to two decimal places, i.e., $$.$ or $$.$$", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            prodPriceTxt.setText("");
            return false;
        }

        if (!prodMinTxt.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Min must be a whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            prodMinTxt.setText("");
            return false;
        }

        if (!prodMaxTxt.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Max must be a whole number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            prodMaxTxt.setText("");
            return false;
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

    /**
     * This method is a copy/paste of the CreateDeleteAlert() method from the main controller with small changes.
     * @param message - The message that will display when the alert is created.
     * @return - true, user clicked OK - false, user clicked CANCEL.
     */
    private boolean CreateRemoveAlert(String message) {
        Stage stage = (Stage) addProdWindow.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;

        Alert deleteAlert = new Alert(type, "");

        deleteAlert.initModality(Modality.APPLICATION_MODAL);
        deleteAlert.initOwner(stage);

        deleteAlert.getDialogPane().setHeaderText("Remove Part");
        deleteAlert.getDialogPane().setContentText(message);

        Optional<ButtonType> result = deleteAlert.showAndWait();

        if(result.get() == ButtonType.OK)
        {
            return true;
        }
        else if (result.get() == ButtonType.CANCEL)
        {
            return false;
        }
        return false;
    }
}