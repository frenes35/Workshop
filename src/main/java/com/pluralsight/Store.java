package com.pluralsight;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online com.pluralsight.Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String sku = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                inventory.add(new Product(sku, name, price));


            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file!");
            e.printStackTrace();

        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        System.out.println("Here are the products: ");
        for (Product product : inventory) {
            System.out.println(product);
        }
        System.out.println("Enter the sku of the product you want to add: ");
        String sku = scanner.nextLine();

        Product namedProduct = findProductById(sku, inventory);
        if (namedProduct != null) {
            cart.add(namedProduct);
            System.out.println("Added product: " + namedProduct);


        } else {
            System.out.println("Product not found!");
        }


    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {

        if (cart.isEmpty()) {
            System.out.println("Your cart is currently empty.");
            return;
        }

        System.out.println("Here are the items in your cart:");
        totalAmount = 0.0;

        for (Product product : cart) {
            System.out.println(product);
            totalAmount += product.getPrice();
        }
        System.out.println("Total Amount: " + totalAmount);

        System.out.println("To proceed, please type 'checkout':");
        String input = scanner.nextLine();


        if (input.equalsIgnoreCase("checkout")) {
            checkOut(cart, totalAmount);
        } else if (!input.equalsIgnoreCase("back")) {
            Product productToRemove = findProductById(input, cart);
            if (productToRemove != null) {
                cart.remove(productToRemove);
                System.out.println(productToRemove.getName() + "has been removed from your cart");
            } else {
                System.out.println("This product is not in your cart.");
            }

        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {

    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (Product product : inventory) {
            if (product.getSku().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }
}