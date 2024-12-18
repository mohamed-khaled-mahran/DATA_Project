/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bank;

import java.io.*;
import java.util.*;

/**
 *
 * @author mahran
 */
public class main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("Hello World!");
        Bank MAS= new Bank("MAS","Elfaioum",12);
        MAS.Customers = File_Handling.loadFromFile("Bank.txt");
        Customer c1 = new Customer("MOHAMED","Elfaioum", 5000 ,Customer.AccountType.CURRENT);
        Customer c2 = new Customer("MAHRAN","Elfaioum", 1000,Customer.AccountType.SAVINGS ); 
        MAS.addCustomer(c1);
        MAS.addCustomer(c2);
        MAS.DisplayCustomers();
        c1.Deposit(2000);
        System.out.println(c1.balance);
        c1.Withdraw(1000);
        System.out.println(c1.balance);
        c1.Transfer(c2,2000);
        c2.Deposit(5000);
        System.out.println(c2.balance);
       c1.Trans_history();
       c2.Trans_history();
        System.out.println("------------------\n-----------------\n-----------------");
       
        c1.request(c2, 2000);
        c1.Trans_history();
        c2.Trans_history();
        System.out.println(c1.balance);

        // Before saving
        System.out.println("Saving data to file...");
        MAS.DisplayCustomers();  // Check if customers are correctly added

        // Save to file
        File_Handling.saveToFile(MAS.Customers, "Banki.txt");

    }
}
