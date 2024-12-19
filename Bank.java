/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankforall;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author mahran
 */
public class Bank {
    private String name;
    private String address;
    private String IFSC_code;
    private static TreeMap<Integer, Customer> customers; // Customer storage (sorted by ID)
    

    public Bank(String name, String address, String IFSC_code) {
        this.name = name;
        this.address = address;
        this.IFSC_code = IFSC_code;
        this.customers = new TreeMap<>();
        
        loadCustomersFromFile();
        loadTransactionsFromFile();
    }
    

    // Create a new customer account
    public void createCustomer(Customer customer) {
    
    for (Customer C : customers.values()) {
        if (C.getName().equals(customer.getName()) && C.getPassword().equals(customer.getPassword())) {
            System.out.println("Customer with the same name and password already exists.");
            return; // Stop further execution if a duplicate is found
        }
    } 
    customers.put(customer.getCustomerId(), customer);
    saveNewCustomerToFile(customer);  
    System.out.println("Customer created successfully.");
    }

    
    
    // Search for a customer account (O(log n))
    public static Customer searchCustomer(int customerId) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            System.out.println("Customer found: " + customer);
            return customer;
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
            return null;
        }
    }

    // Add a transaction to the global list
    
    
    public void DisplayCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Customer Accounts:");
            for (Customer customer : customers.values()) {
                System.out.println(customer);
            }
        }
    }
    public void displayBankDetails() {
        System.out.println("Bank Details:");
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("IFSC Code: " + IFSC_code);
    }

    
        public Customer login(String name, String password) {
        for (Customer customer : customers.values()) {
            // Check if the name and password match
            if (customer.getName().equals(name) && customer.getPassword().equals(password)) {
                System.out.println("Login successful for customer: " + customer.getName());
                return customer;  // Return the matching customer object
            }
        }
        System.out.println("Invalid username or password.");
        return null;  // Return null if login fails
    }
        
        
    // Save customer to file
    public void saveNewCustomerToFile(Customer newCustomer) {
    File file = new File("customers.txt");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
        // Write customer data including the password field
        writer.write(newCustomer.getCustomerId() + "," +
                     newCustomer.getName() + "," +
                     newCustomer.getAddress() + "," +
                     newCustomer.getBalance() + "," +
                     newCustomer.getAccountType() + "," +
                     newCustomer.getPassword() + "\n"); // Password included here
        System.out.println("New customer added to the file.");
    } catch (IOException e) {
        System.out.println("Error writing new customer to file: " + e.getMessage());
    }
    }

    public void saveCustomerToFile(Customer updatedCustomer) {
    File file = new File("customers.txt");
    List<String> customerLines = new ArrayList<>();
    
    // Read the file and store all lines in memory
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int customerId = Integer.parseInt(parts[0]);

            // Check if this is the customer we want to update
            if (customerId == updatedCustomer.getCustomerId()) {
                // Update the line for the customer
                customerLines.add(updatedCustomer.toFileString()); // Save the updated data
            } else {
                // Add the existing line if not the customer we are updating
                customerLines.add(line);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading customer data from file: " + e.getMessage());
    }

    // Write all the lines back to the file, including the updated customer
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String line : customerLines) {
            writer.write(line + "\n");
        }
    } catch (IOException e) {
        System.out.println("Error writing customer data to file: " + e.getMessage());
    }
}//C:\\Users\\windows 1\\Desktop\\BankManagementSystem\\src\\main\\java\\com\\mycompany\\bankmanagementsystem\\customers.txt
    // Load customers from file
    
    
    public void loadCustomersFromFile() {
        File file = new File("customers.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 6) { // Ensure that we have the right number of fields
                        int customerId = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String address = parts[2];
                        double balance = Double.parseDouble(parts[3]);
                        String accountType = parts[4];
                        String password = parts[5]; // Read the password field

                        Customer customer = new Customer(customerId, password, name, address, balance, accountType);
                        customers.put(customerId, customer); // Store in memory (TreeMap)
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading customers from file: " + e.getMessage());
            }
        } else {
            System.out.println("Customer file does not exist.");
        }
    }


    // Load transactions from file
    private void loadTransactionsFromFile() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    File file = new File("transactions.txt");
    
    if (!file.exists()) {
        System.out.println("Transaction file does not exist.");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Reading transaction line: " + line);
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                int transactionId = Integer.parseInt(parts[0]);
                String type = parts[1];
                double amount = Double.parseDouble(parts[2]);
                Date date = null;
                try {
                    date = dateFormat.parse(parts[3]);
                } catch (ParseException e) {
                    System.out.println("Invalid date format for line: " + line);
                    continue; // Skip invalid entries
                }
                int customerId = Integer.parseInt(parts[4]);
                Integer receiverId = parts.length > 5 ? Integer.parseInt(parts[5]) : null;

                Transaction transaction = receiverId == null
                        ? new Transaction(type, amount, customerId)
                        : new Transaction(type, amount, customerId, receiverId);

                transaction.setTransactionId(transactionId);
                transaction.setDate(date);

                // Add transaction to stack
                Transaction.allTransactions.push(transaction);
            }
        }
    } catch (IOException e) {
        System.out.println("Error loading transactions from file: " + e.getMessage());
    }
    }
    
    
     public static void saveTransactionToFile(Transaction transaction) {
        File file = new File("transactions.txt");  // Adjust the file path if necessary
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(transaction.toFileString() + "\n");  // Append the transaction to the file
            System.out.println("Transaction saved: " + transaction.toFileString());
        } catch (IOException e) {
            System.out.println("Error saving transaction to file: " + e.getMessage());
        }
    }
}
