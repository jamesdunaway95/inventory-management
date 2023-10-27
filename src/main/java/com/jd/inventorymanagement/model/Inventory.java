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
        try {
            return allParts.get(partId);
        } catch(IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * @param partName the partName to lookup in inventory.
     * @return ObservableList with all parts matching partName.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> result = FXCollections.observableArrayList();
        partName = partName.toLowerCase();

        for (Part part : getAllParts()) {
            if (part.getName().toLowerCase().contains(partName)) {
                result.add(part);
            }
        }

        return result;
    }

    /**
     * @param productId the productId to lookup in inventory.
     * @return Product, if exists.
     */
    public static Product lookupProduct(Integer productId) {
        try {
            return allProducts.get(productId);
        } catch(IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * @param productName the productName to lookup in inventory.
     * @return ObservableList with all Product(s) matching productName.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> result = FXCollections.observableArrayList();
        productName = productName.toLowerCase();

        for (Product product : getAllProducts()) {
            if (product.getName().toLowerCase().contains(productName)) {
                result.add(product);
            }
        }

        return result;
    }

    /**
     * @param index the index of the selectedPart to be updated.
     * @param selectedPart the selectedPart to replace the current one in inventory.
     */
    public static void updatePart(Integer index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * @param index the index of the selectedProduct to be updated.
     * @param selectedProduct the selectedProduct to replace the current one in inventory.
     */
    public static void updateProduct(Integer index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * @param selectedPart the part to delete from current inventory.
     * @return true if deleted, else, false.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * @param selectedProduct the product to delete from current inventory.
     * @return true if deleted, else, false.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return ObservableList of all parts in inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return ObservableList of all products in inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
