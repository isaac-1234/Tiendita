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

public class StoreGUI extends JFrame implements ActionListener {
    private Store store;  // Reference to the store object
    private JTextArea productDisplay; // Text area to display products
    private ArrayList<User> users; // List of registered users
    private static final String USER_FILE = "src/main/java/com/mycompany/tiendita0/users.txt";  // Text file to save user data
    private static final String PRODUCT_FILE = "src/main/java/com/mycompany/tiendita0/products.txt";  // Text file to save product data
    private User loggedInUser;  // The current logged-in user

    public StoreGUI(Store store, ArrayList<User> users) {
        this.store = store;
        this.users = users;

        // Attempt login before showing the main interface
        if (!showLoginDialog()) {
            // If login fails, exit the program
            System.exit(0);
        }

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

        add(buttonPanel, BorderLayout.SOUTH);

        createAdminMenu(); // Add admin menu

        // Load users and products from text files when the application starts
        loadUsers();
        loadProducts();

        setVisible(true);
    }

    private void createAdminMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu adminMenu = new JMenu("Admin Menu");

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

    private boolean showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField,
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validate the username and password
            if (validateLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username);
                return true; // Login successful
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return showLoginDialog(); // Retry login
            }
        }
        return false; // Cancelled login
    }

    private boolean validateLogin(String username, String password) {
        // Check if the username and password match any user in the list
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;  // Set the current logged-in user
                return true;
            }
        }
        return false;
    }

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
            saveProducts();  // Save products to file
            JOptionPane.showMessageDialog(this, "Product added successfully!");
        }
    }

    private void showRemoveProductDialog() {
        String productName = JOptionPane.showInputDialog(this, "Enter product name to remove:");
        if (productName != null && !productName.isEmpty()) {
            if (store.removeProduct(productName)) {
                saveProducts();  // Save products after removing one
                JOptionPane.showMessageDialog(this, "Product removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Product not found.");
            }
        }
    }

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
            saveUsers();  // Save users to file
            JOptionPane.showMessageDialog(this, "User added successfully!");
        }
    }

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

    // Save users to a text file
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
            System.out.println("Users saved successfully to " + USER_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load users from a text file
    private void loadUsers() {
        File file = new File(USER_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(",");
                    if (userData.length == 2) {
                        users.add(new User(userData[0], userData[1]));
                    }
                }
                System.out.println("Users loaded successfully from " + USER_FILE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("No user file found. Starting fresh.");
            users = new ArrayList<>();
        }
    }

    private void loadProducts() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void saveProducts() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

    // Save products to a text
