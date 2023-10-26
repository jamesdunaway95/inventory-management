package com.jd.inventorymanagement.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author James Dunaway
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart the newPart to add to inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct the newProduct to add to inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId the partId to lookup in inventory
     * @return Part, if exists.
     */
    public static Part lookupPart(Integer partId) {
        /**
         * TODO: Add search function to find matching partId in allParts list. BST?
         */
        return null;
    }

    /**
     * @param partName the partName to lookup in inventory.
     * @return ObservableList with all parts matching partName.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        /**
         * TODO: Add search function to find matching partName(s) in allParts list. BST?
         */
        return null;
    }

    /**
     * @param productId the productId to lookup in inventory.
     * @return Product, if exists.
     */
    public static Product lookupProduct(Integer productId) {
        /**
         * TODO: Add search function to find matching productId in allProducts list. BST?
         */
        return null;
    }

    /**
     * @param productName the productName to lookup in inventory.
     * @return ObservableList with all Product(s) matching productName.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        /**
         * TODO: Add search function to find matching productName(s) in allProducts list. BST?
         */
        return null;
    }

    /**
     * @param index the index of the selectedPart to be updated.
     * @param selectedPart the selectedPart to replace the current one in inventory.
     */
    public static void updatePart(Integer index, Part selectedPart) {
        /**
         * TODO: Add update functionality, e.g. find the selected part at the provided index and replace it with the selectedPart.
         */
    }

    /**
     * @param index the index of the selectedProduct to be updated.
     * @param selectedProduct the selectedProduct to replace the current one in inventory.
     */
    public static void updateProduct(Integer index, Product selectedProduct) {
        /**
         * TODO: Add update functionality, e.g. find the selected product at the provided index and replace it with the selectedProduct.
         */
    }

    /**
     * TODO: Need to test
     */
    /**
     * @param selectedPart the part to delete from current inventory.
     * @return true if deleted, else, false.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * TODO: Need to test
     */
    /**
     * @param selectedProduct the product to delete from current inventory.
     * @return true if deleted, else, false.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * TODO: Need to test
     */
    /**
     * @return ObservableList of all parts in inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * TODO: Need to test
     */
    /**
     * @return ObservableList of all products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
