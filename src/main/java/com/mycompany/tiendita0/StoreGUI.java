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
import java.text.SimpleDateFormat;
import java.util.Date;

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
        File file = new File(USER_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Each line in the file has format: "username,password"
                    String[] userData = line.split(",");
                    if (userData.length == 2) {
                        String storedUsername = userData[0];
                        String storedPassword = userData[1];
                        
                        // Check if the input username and password match the stored values
                        if (storedUsername.equals(username) && storedPassword.equals(password)) {
                            loggedInUser = new User(username, password); // Set the current logged-in user
                            return true; // Login is successful
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading user data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "User file not found. Please add users first.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false; // Login failed
    }

    private void showAddProductDialog() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // Default to today's date

        Object[] message = {
            "Product Name:", nameField,
            "Product Price:", priceField,  // Only relevant for new products
            "Product Quantity:", quantityField,
            "Date Added (yyyy-MM-dd):", dateField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add or Update Product", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double price;
            int quantity = Integer.parseInt(quantityField.getText());
            String dateAdded = dateField.getText();

            // Check if the product already exists in the store
            Product existingProduct = store.getProductByName(name);

            if (existingProduct != null) {
                // If the product exists, update its quantity
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                JOptionPane.showMessageDialog(this, "Quantity updated successfully! New quantity: " + existingProduct.getQuantity());
            } else {
                // If it's a new product, add it to the store
                price = Double.parseDouble(priceField.getText());
                Product newProduct = new Product(name, price, quantity, dateAdded);
                store.addProduct(newProduct);
                JOptionPane.showMessageDialog(this, "Product added successfully!");
            }

            // Save the updated inventory to the file
            saveProducts();
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
            saveUsers(); // Save the users to file
            JOptionPane.showMessageDialog(this, "User added successfully!");
        }
    }

    private void showUsers() {
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(user.getUsername()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Registered Users", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveProducts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
            for (Product product : store.inventory) {
                writer.write(product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "," + product.getDateAdded());
                writer.newLine();
            }
            System.out.println("Products saved successfully to " + PRODUCT_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving products: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadProducts() {
        File file = new File(PRODUCT_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] productData = line.split(",");
                    if (productData.length == 4) {
                        String name = productData[0];
                        double price = Double.parseDouble(productData[1]);
                        int quantity = Integer.parseInt(productData[2]);
                        String dateAdded = productData[3];
                        store.addProduct(new Product(name, price, quantity, dateAdded));
                    }
                }
                System.out.println("Products loaded successfully from " + PRODUCT_FILE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("No product file found. Starting fresh.");
            store.inventory = new ArrayList<>();
        }
    }

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

    private void loadUsers() {
        File file = new File(USER_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(",");
                    if (userData.length == 2) {
                        String username = userData[0];
                        String password = userData[1];
                        users.add(new User(username, password));
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

    public static void main(String[] args) {
        Store store = new Store();
        ArrayList<User> users = new ArrayList<>();
        new StoreGUI(store, users);
    }
}
