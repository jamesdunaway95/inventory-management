package com.jd.inventorymanagement.controller;

import com.jd.inventorymanagement.model.Inventory;
import com.jd.inventorymanagement.model.Part;
import com.jd.inventorymanagement.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * @author James Dunaway
 */
public class ControllerAddProduct implements Initializable {
    @FXML
    private TextField partsSearchField;

    @FXML
    private AnchorPane addProdTFields;

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

    public void SetProductId(int newId) {
        this.prodIdTxt.setText(String.valueOf(newId));
    }

    @FXML
    void onPartSearchClicked(ActionEvent event) {
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

    @FXML
    void onAddPartClicked(ActionEvent event) {
        Part selectedPart = availPartsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must select a valid part in order to add it.", "No Part Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        associatedParts.add(selectedPart);
    }

    @FXML
    void onCancelProdClicked(ActionEvent event) {
        for (Node node : addProdTFields.getChildren()) {
            if (node instanceof TextField) {
                ((TextField)node).setText(""); // Clear text
            }
        }

        Stage stage = (Stage) addProdWindow.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onRemoveAssociatedPartClicked(ActionEvent event) {
        Part selectedPart = currPartsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You must select a valid part in order to remove it.", "No Part Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        associatedParts.remove(selectedPart);
    }

    @FXML
    void onSaveProdClicked(ActionEvent event) {
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

    private boolean ValidateTextFields() {
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

        if (associatedParts.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "A product must have at least one associated part.", "Missing Part(s)", JOptionPane.ERROR_MESSAGE);
            return false;
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