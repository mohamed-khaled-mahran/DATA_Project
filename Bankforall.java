/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bankforall;
import java.util.*;

/**
 *
 * @author mahran
 */
public class Bankforall {


    public static void main(String[] args) {
        
    
        Scanner scanner = new Scanner(System.in);
        int ID=0;
        // Create a bank and admin
        Bank bank = new Bank("MyBank", "123 Bank St", "MYBK123");
        Admin admin = new Admin("admin", "password");
       

        System.out.println("Welcome to the Bank Management System!");
        // Main loop
        boolean running = true;
        while (running) {
            System.out.println("1. Login as Admin\n2. Login as Customer\n3. Sign up as Customer\n4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    
                    System.out.println("Enter Admin ID:");
                    String AID = scanner.next();
                    System.out.println("Enter Password:");
                    String password = scanner.next();
                    if (admin.login(AID, password)) {
                    System.out.println("Admin logged in successfully.");
                    boolean adminActions = true;
                    while (adminActions) {
                        System.out.println("1. Add a customer\n2. Delete a customer\n3. Modify customer details\n4. View all transactions\n5. Exit");
                        int action = scanner.nextInt();
                        switch (action) {
                            case 1:
                                System.out.println("Enter Customer Details: ");                   
                                System.out.println("Name:");
                                String name = scanner.next();
                                System.out.println("Address:");
                                String address = scanner.next();
                                System.out.println("Initial Balance:");
                                double balance = scanner.nextDouble();
                                System.out.println("Account Type (Savings/Current):");
                                String accountType = scanner.next();
                                System.out.println("Enter Password:");
                                String Cpassw = scanner.next();
                                Customer newCustomer = new Customer(ID, Cpassw, name, address, balance, accountType);
                                admin.addCustomer(bank, newCustomer);
                                break;
                            case 2:
                                System.out.println("Enter the id of the customer you want to delete:");
                                int Did = scanner.nextInt();
                                admin.deleteCustomer(bank, Did);
                                break;
                            case 3:
                                System.out.println("Enter the id of the customer you want to change:");
                                int chid = scanner.nextInt();
                                System.out.println("Name:");
                                String chname = scanner.next();
                                System.out.println("Address:");
                                String chaddress = scanner.next();
                                admin.modifyCustomerDetails(bank, chid, chname, chaddress);
                                break;
                            case 4:
                                admin.viewAllTransactions(bank);
                                break;
                            case 5:
                                adminActions = false;
                                break;
                            default:
                                System.out.println("Invalid action.");
                        }
                    }
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 2:
                    
                    System.out.println("Enter Name:");
                    String CName = scanner.next();
                    System.out.println("Enter Password:");
                    String Cpass = scanner.next();
                    Customer cc = bank.login(CName, Cpass);
                    if (cc!=null) {
                        System.out.println(cc.getName()+" logged in successfully.");
                    
                    boolean customerActions = true;
                    while (customerActions) {
                        System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. Request\n5. approve\n6. View Transaction History\n7. Exit");
                        int action = scanner.nextInt();
                        switch (action) {
                            case 1:
                                System.out.println("Enter Amount to Deposit:");
                                double depositAmount = scanner.nextDouble();
                                cc.deposit(depositAmount, bank);
                                break;
                            case 2:
                                System.out.println("Enter Amount to Withdraw:");
                                double withdrawAmount = scanner.nextDouble();
                                cc.withdraw(withdrawAmount, bank);
                                break;
                            case 3:
                                System.out.println("Enter Receiver ID:");
                                int receiverId = scanner.nextInt();
                                Customer receiver = bank.searchCustomer(receiverId);
                                if (receiver == null) {
                                    System.out.println("Receiver not found.");
                                    break;
                                    }
                                System.out.println("Enter Amount to Transfer:");
                                double transferAmount = scanner.nextDouble();
                                cc.transfer(receiver, transferAmount, bank);
                                break;
                            case 4:
                                System.out.println("Enter the account ID you want to request money from:");
                                int requestId = scanner.nextInt();
                                Customer target = bank.searchCustomer(requestId);

                                if (target == null) {
                                    System.out.println("Customer not found.");
                                    break;
                                }

                                System.out.println("Enter the amount to request:");
                                double requestAmount = scanner.nextDouble();
                                cc.request(target, requestAmount, bank);
                                break;
                            case 5:
                                cc.approve(bank);
                                break;
                            case 6:
                                cc.displayTransactionHistory();
                                break;
                            case 7:
                                customerActions = false;
                                break;
                            default:
                                System.out.println("Invalid action.");
                        }
                    }}
                    break;
                    
                case 3:
                    System.out.println("Enter Customer Details: ");                   
                    System.out.println("Name:");
                    String name = scanner.next();
                    System.out.println("Address:");
                    String address = scanner.next();
                    System.out.println("Initial Balance:");
                    double balance = scanner.nextDouble();
                    System.out.println("Account Type (Savings/Current):");
                    String accountType = scanner.next();
                    System.out.println("Enter Password:");
                    String Cpassw = scanner.next();
                    Customer newCustomer = new Customer(ID, Cpassw, name, address, balance, accountType);
                    admin.addCustomer(bank, newCustomer);
                    break;
                case 4:
//                    System.out.println("Enter Customer ID to Perform Actions:");
//                    int custId = scanner.nextInt();
//                    Customer customer = bank.searchCustomer(custId);
//                    if (customer == null) {
//                        System.out.println("Customer not found.");
//                       

                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
        
        
    }





