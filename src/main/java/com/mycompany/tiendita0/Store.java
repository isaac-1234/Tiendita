/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tiendita0;

/**
 *
 * @author caleb
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Store {
    private ArrayList<Product> inventory = new ArrayList<>();

    // Add a new product
    public void addProduct(Product product) {
        inventory.add(product);
        System.out.println("Product added successfully.");
    }

    // Remove a product by name
    public void removeProduct(String productName) {
        boolean found = false;
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(productName)) {
                inventory.remove(product);
                System.out.println("Product removed successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    // Display all products
    public void displayProducts() {
        if (inventory.isEmpty()) {
            System.out.println("No products in the inventory.");
        } else {
            for (Product product : inventory) {
                System.out.println(product);
            }
        }
    }

    // Update product quantity
    public void updateProductQuantity(String productName, int newQuantity) {
        boolean found = false;
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(productName)) {
                product.setQuantity(newQuantity);
                System.out.println("Product quantity updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }
}

