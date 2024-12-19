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
public class Customer {
       
    private static int count= 0;
    private String password;
    private int customerId;
    private String name;
    private String address;
    private double balance;
    private String accountType; // Savings or Current
    private Stack<Transaction> transactionHistory; // Last-first transaction order
    public ArrayList<String> requests ;
    public HashMap<Customer, Double> money_requested ;

    public Customer(int customerID, String password, String name, String address, double balance, String accountType) {
        this.customerId = count++; 
        this.password=password;
        this.name = name;
        this.address = address;
        this.balance = balance;
        this.accountType = accountType;
        this.transactionHistory = new Stack<>();
        this.requests = new ArrayList<>();
        this.money_requested = new HashMap<>();

    }
    
    public int compareTo(Customer other) {
        return Integer.compare(this.customerId, other.customerId);
    }

    // Deposit money
    public void deposit(double amount, Bank bank) {
    if (amount > 0) {
        // Update the balance
        balance += amount;

        // Create a transaction object and add it to the transaction history
        Transaction transaction = new Transaction("Deposit", amount, customerId);
        transactionHistory.push(transaction);
        Transaction.addTransaction(transaction ,bank); // Add the transaction to the bank

        // Save the updated customer data to the file
        bank.saveCustomerToFile(this);  // Save the updated customer
        System.out.println("Deposit successful. New balance: " + balance);
    } else {
        System.out.println("Invalid amount.");
    }
}

    // Withdraw money
    public void withdraw(double amount, Bank bank) {
    if (amount > 0 && amount <= balance) {
        balance -= amount;
        Transaction t = new Transaction("Withdrawal", amount, customerId);
        transactionHistory.push(t);
        Transaction.addTransaction(t ,bank);
        bank.saveCustomerToFile(this);  // Save updated customer data to the file
        System.out.println("Withdrawal successful. New balance: " + balance);
    } else {
        System.out.println("Invalid or insufficient balance.");
    }
}

    // Transfer money to another account
    public void transfer(Customer receiver, double amount, Bank bank) {
    if (amount > 0 && amount <= balance) {
        this.balance-=amount;  // Withdraw from the sender
        receiver.balance += amount;  // Deposit into the receiver's account
        Transaction t = new Transaction("Transfer", amount, customerId, receiver.getCustomerId());
        transactionHistory.push(t);
        Transaction.addTransaction(t, bank);
        bank.saveCustomerToFile(this);  // Save updated customer data to the file
        bank.saveCustomerToFile(receiver);  // Save the receiver's updated balance
        System.out.println("Transfer successful.");
    } else {
        System.out.println("Invalid or insufficient balance.");
    }
}
     
    
    
 
    public void request(Customer C, double money, Bank bank) {
        if (money <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        if (C == null) {
            System.out.println("Target customer not found.");
            return;
        }
        Date date= new Date();
        Transaction t = new Transaction("Request", money, customerId, C.getCustomerId());  
        transactionHistory.push(t);
        C.requests.add("Account : "+ C.getCustomerId()+ " have made a request of : "+ money+ " to you at :"+ date.toString());
        C.money_requested.put(this, money);
        
        C.transactionHistory.push(t);
        Transaction.addTransaction(t, bank);

    }
    
    
    
    public void approve(Bank bank) {
        Scanner in =new Scanner(System.in) ;
        if (requests.isEmpty()) {
            System.out.println("No requests to approve.");
            return;
        }
        
        System.out.println("The requests sent to you are: ");
        for (int i = 0; i < requests.size(); i++) {
            System.out.println((i + 1) + " - " + requests.get(i));
        }
        
        //i want him to choose the operation then approve of it or refuse it
        System.out.println("write the number of the request you want to choose");
        int x =in.nextInt();
        
        if (x < 1 || x > requests.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        // since a hash map (mish mtratebah)<--araby (no index) i have to copy its contents to some thing that i can get its content by index
        //(.keyset)means i well but it in set<> and (.toArray)mean that that list well be treated like an array with an index
        //{x-1}is the index that i want in the map after the {(tahweel)<--araby alashan mish fakr ma3naha} to the array
        Customer RC = (Customer) money_requested.keySet().toArray()[x - 1];
        double req_money = money_requested.get(RC);
        
        System.out.println("write Yes if you want to approve and no if you want to refuse");
        String y =in.next();
        Date date = new Date();
        
        if (y.equalsIgnoreCase("yes")) {
            if (balance >= req_money) {
                // Approve and perform the transfer
                Transaction approvalTransaction = new Transaction("Approval: Granted", req_money, customerId, RC.getCustomerId());
                transfer(RC, req_money, bank); // Transfer the amount
                transactionHistory.push(approvalTransaction);
                RC.transactionHistory.push(approvalTransaction);
                Transaction.addTransaction(approvalTransaction,bank);

                // Save the approval transaction to the file
                bank.saveTransactionToFile(approvalTransaction);

                System.out.println("Request approved successfully.");
            } else {
                System.out.println("Insufficient balance to approve the request.");
                return;
            }
        } else if (y.equalsIgnoreCase("no")) {
            // Deny the request
            Transaction denialTransaction = new Transaction("Approval: Denied", req_money, customerId, RC.getCustomerId());
            transactionHistory.push(denialTransaction);
            RC.transactionHistory.push(denialTransaction);
            Transaction.addTransaction(denialTransaction,bank);

            // Save the denial transaction to the file
            bank.saveTransactionToFile(denialTransaction);

            System.out.println("Request refused.");
        } else {
            System.out.println("Invalid input. Please enter 'Yes' or 'No'.");
            return;
        }
        requests.remove(x - 1);
        money_requested.remove(RC);
    }


    // Display account details
    public void displayDetails() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Balance: " + balance);
        System.out.println("Account Type: " + accountType);
    }

    // Display transaction history (last-first order)
       public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }

        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public static void setCount(int count) {
        Customer.count = count;
    }

    public void setTransactionHistory(Stack<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }

    public void setMoney_requested(HashMap<Customer, Double> money_requested) {
        this.money_requested = money_requested;
    }
    
    
    public static int getCount() {
        return count;
    }

    public Stack<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public HashMap<Customer, Double> getMoney_requested() {
        return money_requested;
    }
    
    

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void modifyDetails(String newName, String newAddress) {
        this.name = newName;
        this.address = newAddress;
        System.out.println("Details updated successfully.");
    }
    public String toFileString() {
    if (name == null || address == null || accountType == null || password == null) {
        return ""; // Handle invalid data
    }
    return customerId + "," + name + "," + address + "," + balance + "," + accountType + "," + password;
    }

}
