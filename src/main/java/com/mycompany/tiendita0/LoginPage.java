/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tiendita0;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A login page for authenticating users before accessing the store system.
 */
public class LoginPage extends JFrame implements ActionListener {
    private ArrayList<User> users;  // List of registered users
    private JTextField usernameField;  // Field to enter username
    private JPasswordField passwordField;  // Field to enter password
    private Store store; // Store object reference

    public LoginPage(ArrayList<User> users, Store store) {
        this.users = users;
        this.store = store;
        
        // Set up the login window
        setTitle("Login to Store System");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        
        // Add username and password fields
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);
        
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        
        // Add Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);
        
        // Display the login window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        // Authenticate user
        if (authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Close the login window
            this.dispose();
            // Launch the main store GUI
            new StoreGUI(store, users);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Check if the entered credentials match any registered user
    private boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();  // Empty user list for demo
        Store store = new Store(); // Create a store instance
        new LoginPage(users, store); // Launch the login page
    }
}

