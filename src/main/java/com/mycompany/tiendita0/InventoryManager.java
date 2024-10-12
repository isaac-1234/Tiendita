package com.mycompany.tiendita0;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author caleb
 */
/**
 * Interface for managing a store's inventory.
 */
public interface InventoryManager {
    
    /**
     * Adds a new product to the store inventory.
     * 
     * @param product The product to be added.
     * @return True if the product was successfully added, false otherwise.
     */
    boolean addProduct(Product product);

    /**
     * Removes a product from the inventory by its name.
     * 
     * @param productName The name of the product to be removed.
     * @return True if the product was successfully removed, false otherwise.
     */
    boolean removeProduct(String productName);

    /**
     * Displays the list of all available products in the inventory.
     */
    void showAllProducts();

    /**
     * Updates the quantity of a specific product in the inventory.
     * 
     * @param productName The name of the product to update.
     * @param newQuantity The new quantity of the product.
     * @return True if the quantity was successfully updated, false otherwise.
     */
    boolean updateProductStock(String productName, int newQuantity);
}

