/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tiendita0;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class StoreGUI extends JFrame {
    private Store store;
    private ArrayList<User> users;
    private JTextArea productDisplay;
    private User currentUser; // Track the logged-in user

    private static final String PRODUCT_FILE = "src/main/java/com/mycompany/tiendita0/products.txt";
    private static final String USER_FILE = "src/main/java/com/mycompany/tiendita0/users.txt";

    public StoreGUI(Store store, ArrayList<User> users) {
        this.store = store;
        this.users = users;

        // GUI Setup
        setLayout(new BorderLayout());
        productDisplay = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(productDisplay);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JButton showProductsButton = new JButton("Show Products");
        buttonPanel.add(loginButton);
        buttonPanel.add(showProductsButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        loginButton.addActionListener(e -> showLoginDialog());
        showProductsButton.addActionListener(e -> showProducts());

        loadProducts();  // Load products from file
        loadUsers();     // Load users from file

        setTitle("Store GUI");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();

        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validate the user credentials
            currentUser = users.stream()
                    .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);

            if (currentUser != null) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + currentUser.getUsername());
                setupAccessBasedOnRole(currentUser.getRole()); // Set access based on role
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void setupAccessBasedOnRole(String role) {
        if (role.equals("Admin")) {
            JButton addUserButton = new JButton("Add User");
            addUserButton.addActionListener(e -> showAddUserDialog());
            ((JPanel) getContentPane().getComponent(1)).add(addUserButton);
            revalidate(); // Refresh the layout
        } else {
            // Hide admin features for normal users if needed
        }
    }

    private void showAddUserDialog() {
        if (currentUser == null || !currentUser.getRole().equals("Admin")) {
            JOptionPane.showMessageDialog(this, "Access denied! Only admins can add users.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();
        String[] roles = {"Normal", "Admin"}; // User roles
        JComboBox<String> roleField = new JComboBox<>(roles); // Dropdown for selecting role

        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField,
            "Role:", roleField // Dropdown for role selection
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add User", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = (String) roleField.getSelectedItem(); // Get selected role

            if (users.stream().anyMatch(user -> user.getUsername().equals(username))) {
                JOptionPane.showMessageDialog(this, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                users.add(new User(username, password, role)); // Add user with role
                saveUsers();  // Save new user to file
                JOptionPane.showMessageDialog(this, "User added successfully!");
            }
        }
    }

    private void showProducts() {
        StringBuilder display = new StringBuilder();
        for (Product product : store.inventory) {
            display.append(product.toString()).append("\n");
        }
        productDisplay.setText(display.toString());
    }

    private void loadProducts() {
        File file = new File(PRODUCT_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] productData = line.split(",");
                    if (productData.length == 5) {
                        String name = productData[0];
                        double price = Double.parseDouble(productData[1]);
                        int quantity = Integer.parseInt(productData[2]);
                        String dateAdded = productData[3];
                        String concept = productData[4];  // Load concept
                        store.addProduct(new Product(name, price, quantity, dateAdded, concept));
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
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
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
                    if (userData.length == 3) {
                        String username = userData[0];
                        String password = userData[1];
                        String role = userData[2]; // Load user role
                        users.add(new User(username, password, role));
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
