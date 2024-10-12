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

public class Store implements InventoryManager {
    public ArrayList<Product> inventory = new ArrayList<>();

    @Override
    public boolean addProduct(Product product) {
        for (Product p : inventory) {
            if (p.getName().equalsIgnoreCase(product.getName())) {
                System.out.println("Product already exists.");
                return false;
            }
        }
        inventory.add(product);
        return true;
    }

    @Override
    public boolean removeProduct(String productName) {
        return inventory.removeIf(p -> p.getName().equalsIgnoreCase(productName));
    }

    @Override
    public void showAllProducts() {
        inventory.forEach(System.out::println);
    }

    @Override
    public boolean updateProductStock(String productName, int quantity) {
        for (Product p : inventory) {
            if (p.getName().equalsIgnoreCase(productName)) {
                p.setQuantity(quantity);
                return true;
            }
        }
        return false;
    }
}
