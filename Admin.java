/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankforall;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author mahran
 */
public class Admin {
      private String adminId;
    private String password;

    public Admin(String adminId, String password) {
        this.adminId = adminId;
        this.password = password;
    }

    // Validate admin credentials
    public boolean login(String adminId, String password) {
        return this.adminId.equals(adminId) && this.password.equals(password);
    }

    // Add a new customer account
    public void addCustomer(Bank bank, Customer newCustomer) {
    // Add the customer to the bank's internal data (e.g., a HashMap)
    bank.createCustomer(newCustomer);
    }

    // Delete a customer account
    public void deleteCustomer(Bank bank, int customerId) {
        Customer customer = bank.searchCustomer(customerId);
        if (customer != null) {
            Bank.getCustomers().remove(customerId) ;// Assuming getCustomers() returns the TreeMap
            bank.saveAllCustomersToFile(); // Save updated customers to the file
            System.out.println("Account deletion complete for customer ID: " + customerId);
        } else {
            System.out.println("Customer not found.");
        }
    }

    // Modify customer details
    public void modifyCustomerDetails(Bank bank, int customerId, String newName, String newAddress) {
        Customer customer = bank.searchCustomer(customerId);
        if (customer != null) {
            customer.modifyDetails(newName, newAddress);
            bank.saveCustomerToFile(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }

    // View all transactions
    public void viewAllTransactions(Bank bank) {
        Transaction.displayAllTransactions();
    }

    // Password encryption

}
