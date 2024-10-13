/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tiendita0;

import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for managing a small store's inventory and users.
 */
public class StoreGUI extends JFrame implements ActionListener {
    private Store store;  // Reference to the store object
    private JTextArea productDisplay; // Text area to display products
    private ArrayList<User> users; // List of registered users
    private static final String USER_FILE = "users.dat";  // File to save user data

    public StoreGUI(Store store, ArrayList<User> users) {
        this.store = store;
        this.users = users;

        // Set up the main window
        setTitle("Small Store Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Product display area (center of the window)
        productDisplay = new JTextArea();
        productDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productDisplay); // Allow scrolling
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel (bottom of the window)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3)); // Layout for three buttons

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Remove Product");
        removeButton.addActionListener(this);
        buttonPanel.add(removeButton);

        JButton viewButton = new JButton("View Products");
        viewButton.addActionListener(this);
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom of the window

        createAdminMenu(); // Add admin menu

        // Load users from file when the application starts
        loadUsers();

        // Display the window
        setVisible(true);
    }

    // Method to create Admin Menu with options for managing users
    private void createAdminMenu() {
        JMenuBar menuBar = new JMenuBar();  // Create menu bar
        JMenu adminMenu = new JMenu("Admin Menu");  // Create "Admin Menu"

        JMenuItem addUserItem = new JMenuItem("Add User");
        addUserItem.addActionListener(this);
        adminMenu.add(addUserItem);

        JMenuItem viewUsersItem = new JMenuItem("View Users");
        viewUsersItem.addActionListener(this);
        adminMenu.add(viewUsersItem);

        menuBar.add(adminMenu);
        setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Add Product":
                showAddProductDialog();
                break;

            case "Remove Product":
                showRemoveProductDialog();
                break;

            case "View Products":
                showProducts();
                break;

            case "Add User":
                showAddUserDialog();
                break;

            case "View Users":
                showUsers();
                break;
        }
    }

    // Pop-up dialog for adding a product
    private void showAddProductDialog() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        Object[] message = {
            "Product Name:", nameField,
            "Product Price:", priceField,
            "Product Quantity:", quantityField,
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Product", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            Product newProduct = new Product(name, price, quantity);
            store.addProduct(newProduct);
            JOptionPane.showMessageDialog(this, "Product added successfully!");
        }
    }

    // Pop-up dialog for removing a product
    private void showRemoveProductDialog() {
        String productName = JOptionPane.showInputDialog(this, "Enter product name to remove:");
        if (productName != null && !productName.isEmpty()) {
            if (store.removeProduct(productName)) {
                JOptionPane.showMessageDialog(this, "Product removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Product not found.");
            }
        }
    }

    // Display all products in the text area
    private void showProducts() {
        productDisplay.setText("");
        StringBuilder sb = new StringBuilder();

        if (store.inventory.isEmpty()) {
            sb.append("No products in inventory.\n");
        } else {
            for (Product product : store.inventory) {
                sb.append(product).append("\n");
            }
        }

        productDisplay.setText(sb.toString());
    }

    // Pop-up dialog to add a user and save to a file
    private void showAddUserDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField,
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New User", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User newUser = new User(username, password);
            users.add(newUser);
            saveUsers();  // Save users to file when a new user is added
            JOptionPane.showMessageDialog(this, "User added successfully!");
        }
    }

    // Display all users in a dialog
    private void showUsers() {
        StringBuilder sb = new StringBuilder();
        if (users.isEmpty()) {
            sb.append("No users have been added.");
        } else {
            sb.append("Current Users:\n");
            for (User user : users) {
                sb.append(user).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Users", JOptionPane.INFORMATION_MESSAGE);
    }

    // Save users to a file
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully to " + USER_FILE);  // Debug message
    }   catch (IOException e) {
            e.printStackTrace();  // Print full exception trace for debugging
            JOptionPane.showMessageDialog(this, "Error saving users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    // Load users from a file
    // Load users from a file
    private void loadUsers() {
        File file = new File(USER_FILE);
        if (file.exists()) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            users = (ArrayList<User>) ois.readObject();
            System.out.println("Users loaded successfully from " + USER_FILE);  // Debug message
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();  // Print full exception trace for debugging
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        System.out.println("No user file found. Starting fresh.");  // Debug message
        users = new ArrayList<>();  // Initialize an empty user list
    }
}


    public static void main(String[] args) {
        Store store = new Store();  // Create store instance
        ArrayList<User> users = new ArrayList<>();  // Initialize user list
        new StoreGUI(store, users); // Launch GUI
    }
}
