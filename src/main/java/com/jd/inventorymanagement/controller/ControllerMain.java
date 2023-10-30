package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.Main;
import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Part;
import com.jd.inventorymanagement.model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the functionality and logic for the main menu. This class creates new stages for the other windows,
 * temporarily granting modality while they are open. The main menu will not close unless the program itself is closed,
 * therefore, when any other stage is closed modality is given back to the main menu.
 * Improvement/Extension Ideas:
    * Implementing separate classes for methods reused across multiple classes.
    * The current error delivery method is a bit messy. Could be improved with a dedicated class or method and cleaner methods.
    * The search fields could be extended/improved to query in realtime as the user types.
    * The stage creation logic should be a separate method as it is repeated multiple times.
    * The table logic could be extended to allow filtering options. This can be useful for tables with many items.
 * @author James Dunaway
 */
public class ControllerMain implements Initializable {
    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TextField partsSearchField;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partLvlCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> prodTableView;

    @FXML
    private TextField productsSearchField;

    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    @FXML
    private TableColumn<Product, Integer> prodLvlCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCol;

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
            partsTableView.setItems(Inventory.getAllParts());
            return;
        }

        try {
            Integer.parseInt(partsSearchField.getText());

            Part searchedPart = Inventory.lookupPart(Integer.parseInt(partsSearchField.getText()) - 1);

            if (searchedPart == null) {
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }

            partsTableView.getSelectionModel().select(searchedPart);
        } catch (NumberFormatException e) {
            ObservableList<Part> searchedParts = Inventory.lookupPart(partsSearchField.getText());

            if(searchedParts.size() == 1) {
                partsTableView.getSelectionModel().select(searchedParts.get(0));
                return;
            }

            if (!searchedParts.isEmpty()) {
                partsTableView.setItems(searchedParts);
            } else {
                partsTableView.setItems(searchedParts);
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method utilizes the products search text field to manipulate and filter the products table.
     * Errors/Issues:
     *      * This method was copied over and adjusted as needed after the parts method was complete. This allowed
     *      * me to avoid the previously mentioned errors.
     */
    @FXML
    void onProductSearchClicked() {
        if (productsSearchField.getText().isEmpty()) {
            prodTableView.setItems(Inventory.getAllProducts());
            return;
        }

        try {
            Integer.parseInt(productsSearchField.getText());

            Product searchedProduct = Inventory.lookupProduct(Integer.parseInt(productsSearchField.getText()) - 1);

            if (searchedProduct == null) {
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }

            prodTableView.getSelectionModel().select(searchedProduct);

        } catch (NumberFormatException e) {
            ObservableList<Product> searchedProducts = Inventory.lookupProduct(productsSearchField.getText());

            if(searchedProducts.size() == 1) {
                prodTableView.getSelectionModel().select(searchedProducts.get(0));
                return;
            }

            if (!searchedProducts.isEmpty()) {
                prodTableView.setItems(searchedProducts);
            } else {
                prodTableView.setItems(searchedProducts);
                JOptionPane.showMessageDialog(new JFrame(), "No matches found. Please double-check your spelling and try again.", "No Matches Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This class locates/loads the add part menu, sends the class the new part ID, and sets the stage.
     */
    @FXML
    void onAddPartsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-add-part.fxml"));
        Parent root  = loader.load();

        ControllerAddPart addPart = loader.getController();
        addPart.SetPartId(Inventory.getAllParts().size() + 1);

        Stage stage = new Stage();
        stage.setTitle("Add New Part");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * This class locates/loads the add product menu, sends the class the new product ID, and sets the stage.
     */
    @FXML
    void onAddProductsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-add-product.fxml"));
        Parent root = loader.load();

        ControllerAddProduct addProduct = loader.getController();
        addProduct.SetProductId(Inventory.getAllProducts().size() + 1);

        Stage stage = new Stage();
        stage.setTitle("Add New Product");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * This class verifies a valid part is selected, if so, it locates/loads the modify parts menu, feeds it the part ID, and sets the stage.
     * Errors/Issues:
     *      * At first, I received null reference errors, to fix this I implemented a try-catch method. This worked
     *      * but it included more logic than I thought was necessary and that was how I landed on this implementation.
     */
    @FXML
    void onModifyPartsButton() throws IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must first select a valid part.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-mod-part.fxml"));
        Parent root  = loader.load();

        ControllerModPart modPart = loader.getController();
        modPart.PopulatePartInfo(partsTableView.getSelectionModel().getSelectedItem().getId() - 1);

        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * This class verifies a valid product is selected, if so, it locates/loads the modify products menu, feeds it the product ID, and sets the stage.
     * Errors/Issues:
     *      * This was copied over from the above parts method, therefore the same errors were avoided.
     */
    @FXML
    void onModifyProductsButton() throws IOException {
        Product selectedProduct = prodTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must first select a valid product.", "No Product Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view-mod-product.fxml"));
        Parent root = loader.load();

        ControllerModProduct modProduct = loader.getController();
        modProduct.PopulateProductInfo(selectedProduct.getId() - 1);

        Stage stage = new Stage();
        stage.setTitle("Modify Product");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * This class verifies a valid part is selected, verifies the user wants to the delete the object, then deletes it.
     * Errors/Issues:
     *      * Originally, I misread the prompt and did not allow the user to delete a part if an existing product was
     *      * using it. Once I reread the prompt, fixing the logic error was quick.
     */
    @FXML
    void onDeletePartsButton() {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must first select a valid part.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (CreateDeleteAlert("Are you sure you want to delete " + selectedPart.getName() + "?")) {
            if (Inventory.deletePart(selectedPart)) {
                partsTableView.getItems().remove(selectedPart);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Sorry, " + selectedPart.getName() + " cannot be deleted at this time.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.lookupProduct(i).getAllAssociatedParts().contains(selectedPart)) {
                Inventory.lookupProduct(i).deleteAssociatedPart(selectedPart);
            }
        }
    }

    /**
     * This class verifies a valid product is selected, verifies the user wants to the delete the object, then deletes it.
     * Errors/Issues:
     *      * As stated above, I misread the prompt and implemented the wrong deletion prevention logic.
     */
    @FXML
    void onDeleteProductsButton() {
        Product selectedProduct = prodTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must first select a valid product.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), selectedProduct.getName() + " cannot be deleted as there is still a part assigned to it.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (CreateDeleteAlert("Are you sure you want to delete " + selectedProduct.getName() + "?")) {
            if (Inventory.deleteProduct(selectedProduct)) {
                prodTableView.getItems().remove(selectedProduct);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Sorry, " + selectedProduct.getName() + " cannot be deleted at this time.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method closes the program.
     */
    @FXML
    void onExitButton() {
        System.exit(0);
    }

    /**
     * This method is used to set up and populate the parts and products tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        prodTableView.setItems(Inventory.getAllProducts());

        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * This class creates a delete confirmation dialogue menu.
     * This is implemented by the deleteProduct and deletePart buttons/functions.
     * @param message - The message that displays when the dialogue box opens.
     * @return - true, the user clicked OK. false, the user clicked CANCEL.
     */
    private boolean CreateDeleteAlert(String message) {
        Stage stage = (Stage) prodTableView.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.CONFIRMATION;

        Alert deleteAlert = new Alert(type, "");

        deleteAlert.initModality(Modality.APPLICATION_MODAL);
        deleteAlert.initOwner(stage);

        deleteAlert.getDialogPane().setHeaderText("Delete");
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