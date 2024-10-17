package com.mycompany.tiendita0;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author caleb
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product implements Serializable {
    private String name;
    private double price;
    private int quantity;
    private String dateAdded;

    // Constructor with date
    public Product(String name, double price, int quantity, String dateAdded) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
    }

    // Setters and Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getDateAdded() { return dateAdded; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return name + " (Price: " + price + ", Quantity: " + quantity + ", Date Added: " + dateAdded + ")";
    }
}

