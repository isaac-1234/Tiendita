/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tiendita0;

/**
 *
 * @author caleb
 */
import javax.swing.*; // Swing components
import java.awt.*; // Layout managers and container components
import java.awt.event.ActionEvent; // For handling button click events
import java.awt.event.ActionListener;

/**
 * GUI for managing a small store's inventory.
 */
import javax.swing.*; // Swing components
import java.awt.*; // Layout managers and container components
import java.awt.event.ActionEvent; // For handling button click events
import java.awt.event.ActionListener;
import java.util.ArrayList; // For storing users

/**
 * GUI for managing a small store's inventory and users.
 */
public class StoreGUI extends JFrame implements ActionListener {
    private Store store;  // Reference to the store object
    private JTextArea productDisplay; // Text area to display products
    private ArrayList<Usuarios> users; // List to store added users

    public StoreGUI(Store store) {
        this.store = store;
        this.users = new ArrayList<>(); // Initialize the user list

        // Show welcome message
        showWelcomeMessage();
        
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
        
        // Add buttons
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
        
        // Add the Admin Menu
        createAdminMenu();
        
        // Display the window
        setVisible(true);
    }

    // Method to display a welcome message when the program starts
    private void showWelcomeMessage() {
        String welcomeMessage = "Welcome to the Small Store Management System!\n" +
                                "You can add, remove, and view products in the store.";
        JOptionPane.showMessageDialog(this, welcomeMessage, "Welcome", JOptionPane.INFORMATION_MESSAGE);
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

    // Admin Menu creation
    private void createAdminMenu() {
        JMenuBar menuBar = new JMenuBar();  // Create menu bar
        JMenu adminMenu = new JMenu("Admin Menu");  // Create "Admin Menu"

        // Add User menu item
        JMenuItem addUserItem = new JMenuItem("Add User");
        addUserItem.addActionListener(this);
        adminMenu.add(addUserItem);

        // View Users menu item
        JMenuItem viewUsersItem = new JMenuItem("View Users");
        viewUsersItem.addActionListener(this);
        adminMenu.add(viewUsersItem);
        
        // Add Admin menu to the menu bar
        menuBar.add(adminMenu);
        setJMenuBar(menuBar);
    }

    // Pop-up dialog to add a user
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
            Usuarios newUser = new Usuarios(username, password);
            users.add(newUser);
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
            for (Usuarios user : users) {
                sb.append(user).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Users", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        Store store = new Store();  // Create store instance
        new StoreGUI(store); // Launch GUI
    }
}
