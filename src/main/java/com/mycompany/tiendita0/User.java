/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tiendita0;

/**
 *
 * @author caleb
 */
import java.io.Serializable;

/**
 * User class to store username and password.
 * Implements Serializable to allow saving to a file.
 */
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String role; // New field for user role

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role; // Set user role
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; } // New getter for role
}

