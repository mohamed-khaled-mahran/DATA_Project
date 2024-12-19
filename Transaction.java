/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankforall;

import java.util.*;

/**
 *
 * @author mahran
 */
public class Transaction {
    private static int idCounter = 1;
    private int transactionId;
    private String type;
    private double amount;
    private Date date;
    private int customerId;
    private Integer receiverId;// Nullable for non-transfer transactions
    public static Stack<Transaction>allTransactions ; 

    public Transaction(String type, double amount, int customerId) {
        this.transactionId = idCounter++;
        this.type = type;
        this.amount = amount;
        this.date = new Date();
        this.customerId = customerId;
        this.allTransactions = new Stack<>();
        
    }

    public  Transaction(String type, double amount, int customerId, int receiverId) {
        this(type, amount, customerId);
        this.receiverId = receiverId;
    }

    public void setTransactionId(int id) {
        this.transactionId = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
    // Display all transactions
    public static void displayAllTransactions() {
    if (Transaction.allTransactions.isEmpty()) {
        System.out.println("No transactions to display.");
        
    }
    System.out.println("All Transactions:");
    for (Transaction transaction : Transaction.allTransactions) {
        System.out.println(transaction); // Ensure toString() is properly implemented in Transaction
    }
    }
    
    
    public static void addTransaction(Transaction transaction) {
        allTransactions.push(transaction);
        Bank.saveTransactionToFile(transaction);  // Save each new transaction
    }

    public String toFileString() {
        return transactionId + "," + type + "," + amount + "," + date + "," + customerId + (receiverId != null ? "," + receiverId : "");
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + ", Type: " + type + ", Amount: " + amount + ", Date: " + date + ", Customer ID: " + customerId + (receiverId != null ? ", Receiver ID: " + receiverId : "");
    }
}
