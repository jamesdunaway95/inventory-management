package com.jd.inventorymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author James Dunaway
 */
public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public Part lookupPart(Integer partId) {
        /**
         * TODO: Add search function to find matching partId in allParts list. BST?
         */
        return null;
    }

    public ObservableList<Part> lookupPart(String partName) {
        /**
         * TODO: Add search function to find matching partName(s) in allParts list. BST?
         */
        return null;
    }

    public Product lookupProduct(Integer productId) {
        /**
         * TODO: Add search function to find matching productId in allProducts list. BST?
         */
        return null;
    }

    public ObservableList<Product> lookupProduct(String productName) {
        /**
         * TODO: Add search function to find matching productName(s) in allProducts list. BST?
         */
        return null;
    }

    public void updatePart(Integer index, Part selectedPart) {
        /**
         * TODO: Add update functionality, e.g. find the selected part at the provided index and replace it with the selectedPart.
         */
    }

    public void updateProduct(Integer index, Product selectedProduct) {
        /**
         * TODO: Add update functionality, e.g. find the selected product at the provided index and replace it with the selectedProduct.
         */
    }

    /**
     * TODO: Need to test
     */
    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * TODO: Need to test
     */
    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * TODO: Need to test
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * TODO: Need to test
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
