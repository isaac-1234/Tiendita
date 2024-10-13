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
public class User implements Serializable {
    private static final long serialVersionUID = 1L;  // Version control for Serializable
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Username: " + username;
    }
}
