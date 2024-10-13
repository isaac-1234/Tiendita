/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tiendita0;

/**
 *
 * @author IsaacdeJesús
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Tiendita0 {
       public static void main(String[] args) {
        Store store = new Store();  // Create store instance
        ArrayList<User> users = new ArrayList<>();  // Initialize user list
        new StoreGUI(store, users); // Launch GUI
       }
}
        