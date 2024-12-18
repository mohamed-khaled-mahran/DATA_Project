/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank;
import java.io.*;
import java.util.*;


public class Customer implements Comparable<Customer> , Serializable {
    
    //3alashan AccountType has two options only
    public enum AccountType {
    SAVINGS, CURRENT
}
    
    private static int count= 0;
    public int ID ;
    public String name ; 
    public String address ;
    public float balance ;
    public AccountType accountType;
    public Stack<transaction> Transactions;
    public ArrayList<String> requests ;
    public HashMap<Customer, Float> money_requested ;
    
    
    
    public Customer( String name, String address, float balance, AccountType accountType) {
        this.ID = count++;
        this.name = name;
        this.address = address;
        this.balance = balance;
        this.accountType = accountType;
        this.Transactions = new Stack<>();
        this.requests = new ArrayList<>();
        this.money_requested = new HashMap<>();
    }
    
        @Override
    public int compareTo(Customer other) {
        return Integer.compare(this.ID, other.ID);
    }
    
    public void Deposit(float money) {
        Date date= new Date();
        if (money>0) {
            balance += money;
            System.out.println("Deposit successful");
            transaction.recordTransaction(Transactions, "Deposit", money, ID, ID);
        }
        else
            System.out.println("Invalid amount..");
    }
    
    
    
    public void Withdraw(float money) {
        Date date= new Date();
        if (balance>=money && money>0) {
            balance -= money;
            System.out.println("Withdraw successful");
            transaction.recordTransaction(Transactions, "Withdraw", money, ID, ID);

        }
        else
            System.out.println("Insufficient balance or invalid amount.");
    }
    
    
    
    public void Transfer(Customer C, float money) {
        Date date= new Date();
        if (balance>=money && money>0) {
            balance -= money;
            C.balance += money;
            System.out.println("Transfer successful");
            transaction.recordTransaction(Transactions, "Transfer", money, ID, C.ID);
            transaction.recordTransaction(C.Transactions, "Transfer", money, ID, C.ID);
        }
        else
            System.out.println("Insufficient balance or invalid amount.");
    }
    
    
    
    public void details() {
        System.out.println("Name : "+ name);
        System.out.println("Address : "+ address);
        System.out.println("ID : "+ ID);
        System.out.println("Balance : "+ balance);
    }
    
    // make a temporary stack so i keep the old data (bedoon hazf)<--araby
    
    public void Trans_history() {
        
        if ( Transactions.isEmpty()) {
            System.out.println("No transactions to display.");
            return;
        }

        Stack<transaction> temp = new Stack<>();
        for (transaction t : Transactions) {
            temp.push(t);
        }
        while (!temp.isEmpty()) {
            transaction t = temp.pop();
            System.out.println("Transaction ID: " + t.ID + ", Type: " + t.type + ", Amount: " + t.amount + ", Date: " + t.date);
        }
    }


    //the ( requests array ) is unique for every customers ,
    //it has the requests sent to the customer in string so i can show it easily in the compiler 
    //(money_requested) is a HashMap that works like a dictionary you can put to instanxes and link them to
    // each other so i can put the Customer that sent the request and link the amount of money needed to the request to that customer
    
    public void request(Customer C, float money) {
            Date date= new Date();
            if (money>0) {
                C.requests.add("Account : "+ C.ID+ " have made a request of : "+ money+ " to you at :"+ date.toString());
                C.money_requested.put(this, money);
                transaction.recordTransaction(Transactions, "request", money, ID, C.ID);
                transaction.recordTransaction(C.Transactions, "request", money, ID, C.ID);
        }   
            else
                System.out.println("Invalid amount.");
    }
    
    
    
    public void approve() {
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
        float req_money = money_requested.get(RC);
        
        System.out.println("write Yes if you want to approve and no if you want to refuse");
        String y =in.next();
        Date date = new Date();
        if(y.equalsIgnoreCase("yes")) {
         if (balance >= req_money) {
            Transfer(RC, req_money);
            transaction.recordTransaction(Transactions, "approval : Granted", req_money, ID, RC.ID);
            transaction.recordTransaction(RC.Transactions, "approval : Granted", req_money, ID, RC.ID);
            System.out.println("Request approved successfully.");
        } else {
            System.out.println("Insufficient balance to approve the request.");
            return;
        }
    } else if (y.equalsIgnoreCase("no")) {
        // Refuse the request
        transaction.recordTransaction(Transactions, "approval : Denied", req_money, ID, RC.ID);
        transaction.recordTransaction(RC.Transactions, "approval : Denied", req_money, ID, RC.ID);
        System.out.println("Request refused.");
    } else {
        System.out.println("Invalid input. Please enter 'Yes' or 'No'.");
        return;
    }
        requests.remove(x - 1);
        money_requested.remove(RC);
    }



}
