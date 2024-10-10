/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tiendita0;

/**
 *
 * @author IsaacdeJesús
 */
import java.util.Scanner;

public class Tiendita0 {
    public static void main(String[] args) {
        Store store = new Store();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== TIENDITA0 ===");
            System.out.println("1. Aniadir producto");
            System.out.println("2. Remover Producto");
            System.out.println("3. Ver Productos");
            System.out.println("4. Actualizar cantidad de producto");
            System.out.println("5. Salir");
            System.out.print("Elije una opcion");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter product quantity: ");
                    int quantity = scanner.nextInt();
                    store.addProduct(new Product(name, price, quantity));
                    break;
                case 2:
                    System.out.print("Enter the product name to remove: ");
                    String removeName = scanner.nextLine();
                    store.removeProduct(removeName);
                    break;
                case 3:
                    store.displayProducts();
                    break;
                case 4:
                    System.out.print("Enter product name to update: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    store.updateProductQuantity(updateName, newQuantity);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

