/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank;

import java.util.*;

/**
 *
 * @author mahran
 */
public class Bank {
    
    public String name;
    public String address;
    public int IFSC_code;
    public List<Customer> Customers;
    
    public Bank(String name, String address, int IFSC_code) {
        this.name = name;
        this.address = address;
        this.IFSC_code = IFSC_code;
        this.Customers = new ArrayList<>();
    }
    
    public void addCustomer(Customer c1) {
        Customers.add(c1);
        System.out.println("The customer has been added successfully");
    }
    
    public void DisplayCustomers() {
        System.out.println("List of customers :");
        System.out.println("-------------------------------");
        for(Customer C: Customers) {
            System.out.println("Name:"+ C.name+"\nbalance :"+ C.balance+"\nID :"+ C.ID);
            System.out.println("-------------------------------");
        }
    }
    
    public void Bankdetails() {
        System.out.println("Bank name"+ name);
        System.out.println("Bank Address"+ address);
        System.out.println("Bank IFSC_code"+ IFSC_code);
    }
    
        // Method to search for a customer account (Binary Search for O(log n))
       public Customer searchCustomer(int customerID) {
        Collections.sort(Customers); // Sorting customers by ID
        int left = 0, right = Customers.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (Customers.get(mid).ID == customerID) {
                return Customers.get(mid);
            }
            if (Customers.get(mid).ID < customerID)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return null; // Not found
    }
}
