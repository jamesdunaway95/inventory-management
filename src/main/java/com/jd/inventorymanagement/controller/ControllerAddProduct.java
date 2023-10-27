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
    private TextField addProdPartsSearchField;

    @FXML
    private AnchorPane addProdTFields;

    @FXML
    private AnchorPane addProductWindow;

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
    private TextField prodInvTxt;

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

        Stage stage = (Stage) addProductWindow.getScene().getWindow();
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
}