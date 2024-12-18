/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bankmanagementsystem;

import java.util.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BankManagementSystem {

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
            System.out.println("1. Login as Admin\n2. Add Customer\n3. Perform Customer Actions\n4. View All Transactions\n5. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    
                    System.out.println("Enter Admin ID:");
                    String adminId = scanner.next();
                    System.out.println("Enter Password:");
                    String password = scanner.next();
                    if (admin.login(adminId, password)) {
                        System.out.println("Admin logged in successfully.");
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 2:
                    System.out.println("Enter Customer Details: ");                   
                    System.out.println("Name:");
                    String name = scanner.next();
                    System.out.println("Address:");
                    String address = scanner.next();
                    System.out.println("Initial Balance:");
                    double balance = scanner.nextDouble();
                    System.out.println("Account Type (Savings/Current):");
                    String accountType = scanner.next();
                    Customer newCustomer = new Customer(ID,name, address, balance, accountType);
                    admin.addCustomer(bank, newCustomer);
                    break;
                case 3:
                    System.out.println("Enter Customer ID to Perform Actions:");
                    int custId = scanner.nextInt();
                    Customer customer = bank.searchCustomer(custId);
                    if (customer == null) {
                        System.out.println("Customer not found.");
                        break;
                    }
                    boolean customerActions = true;
                    while (customerActions) {
                        System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. View Transaction History\n5. Exit");
                        int action = scanner.nextInt();
                        switch (action) {
                            case 1:
                                System.out.println("Enter Amount to Deposit:");
                                double depositAmount = scanner.nextDouble();
                                customer.deposit(depositAmount, bank);
                                break;
                            case 2:
                                System.out.println("Enter Amount to Withdraw:");
                                double withdrawAmount = scanner.nextDouble();
                                customer.withdraw(withdrawAmount, bank);
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
                                customer.transfer(receiver, transferAmount, bank);
                                break;
                            case 4:
                                customer.displayTransactionHistory();
                                break;
                            case 5:
                                customerActions = false;
                                break;
                            default:
                                System.out.println("Invalid action.");
                        }
                    }
                    break;
                case 4:
                    admin.viewAllTransactions(bank);
                    break;
                case 5:
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





// Bank Class
 class Bank {
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
        if (Bank.searchCustomer(customer.getCustomerId())==null) {
            customers.put(customer.getCustomerId(), customer);
            saveNewCustomerToFile(customer);  // Save each new customer
            System.out.println("Customer created successfully.");
        } else {
            System.out.println("Customer with ID " + customer.getCustomerId() + " already exists.");
           
        
    }}

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

    // Save customer to file
    public void saveNewCustomerToFile(Customer newCustomer) {
    File file = new File("C:\\Users\\windows 1\\Desktop\\BankManagementSystem\\src\\main\\java\\com\\mycompany\\bankmanagementsystem\\customers.txt");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // 'true' for appending
        writer.write(newCustomer.toFileString() + "\n"); // Add the new customer's data in the correct format
        System.out.println("New customer added to the file.");
    } catch (IOException e) {
        System.out.println("Error writing new customer to file: " + e.getMessage());
    }
}
    public void saveCustomerToFile(Customer updatedCustomer) {
    File file = new File("C:\\Users\\windows 1\\Desktop\\BankManagementSystem\\src\\main\\java\\com\\mycompany\\bankmanagementsystem\\customers.txt");
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
    File file = new File("C:\\Users\\windows 1\\Desktop\\BankManagementSystem\\src\\main\\java\\com\\mycompany\\bankmanagementsystem\\customers.txt");
    if (file.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // Ensure the data is correct
                    int customerId = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String address = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    String accountType = parts[4];
                    Customer customer = new Customer(customerId,name, address, balance, accountType);
                    customers.put(customerId, customer); // Store in memory (HashMap)
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers from file: " + e.getMessage());
        }
    } else {
        System.out.println("Customer file does not exist.");
    }
}
    // Save transaction to file
    public static void saveTransactionToFile(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\windows 1\\Desktop\\BankManagementSystem\\src\\main\\java\\com\\mycompany\\bankmanagementsystem\\transactions.txt", true))) {
            writer.write(transaction.toFileString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving transaction to file: " + e.getMessage());
        }
    }

    // Load transactions from file
    private void loadTransactionsFromFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"); // Adjust to your date format
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\windows 1\\Desktop\\BankManagementSystem\\src\\main\\java\\com\\mycompany\\bankmanagementsystem\\transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) { // Ensure the format is correct
                    int transactionId = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    Date date = null;
                    try {
                        date = dateFormat.parse(parts[3]); // Use SimpleDateFormat for parsing
                    } catch (ParseException e) {
                        System.out.println("Invalid date format: " + parts[3]);
                    }
                    int customerId = Integer.parseInt(parts[4]);
                    Integer receiverId = parts.length > 5 ? Integer.parseInt(parts[5]) : null;
                    Transaction transaction = receiverId == null
                            ? new Transaction(type, amount, customerId)
                            : new Transaction(type, amount, customerId, receiverId);
                    transaction.setTransactionId(transactionId);
                    transaction.setDate(date);
                    Transaction.allTransactions.push(transaction);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions from file: " + e.getMessage());
        }
    }
}

// Customer Class
class Customer {
    
    private static int count= 0;
    private int customerId;
    private String name;
    private String address;
    private double balance;
    private String accountType; // Savings or Current
    private Stack<Transaction> transactionHistory; // Last-first transaction order
    public ArrayList<String> requests ;
    public HashMap<Customer, Float> money_requested ;

    public Customer(int customerID,String name, String address, double balance, String accountType) {
        this.customerId = count++;       
        this.name = name;
        this.address = address;
        this.balance = balance;
        this.accountType = accountType;
        this.transactionHistory = new Stack<>();
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
        Transaction.addTransaction(transaction); // Add the transaction to the bank

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
        Transaction.addTransaction(t);
        bank.saveCustomerToFile(this);  // Save updated customer data to the file
        System.out.println("Withdrawal successful. New balance: " + balance);
    } else {
        System.out.println("Invalid or insufficient balance.");
    }
}

    // Transfer money to another account
    public void transfer(Customer receiver, double amount, Bank bank) {
    if (amount > 0 && amount <= balance) {
        this.withdraw(amount, bank);  // Withdraw from the sender
        receiver.deposit(amount, bank);  // Deposit into the receiver's account
        Transaction t = new Transaction("Transfer", amount, customerId, receiver.getCustomerId());
        transactionHistory.push(t);
        Transaction.addTransaction(t);
        bank.saveCustomerToFile(this);  // Save updated customer data to the file
        bank.saveCustomerToFile(receiver);  // Save the receiver's updated balance
        System.out.println("Transfer successful.");
    } else {
        System.out.println("Invalid or insufficient balance.");
    }
}
     
    
    
 
    public void request(Customer C, float money) {
            Date date= new Date();
            Transaction t = new Transaction("Request", money, customerId, C.getCustomerId());  
            if (money>0) {
                C.requests.add("Account : "+ C.getCustomerId()+ " have made a request of : "+ money+ " to you at :"+ date.toString());
                C.money_requested.put(this, money);
                transactionHistory.push(t);
                C.transactionHistory.push(t);
                Transaction.addTransaction(t);
        }   
            else
                System.out.println("Invalid amount.");
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
        float req_money = money_requested.get(RC);
        
        System.out.println("write Yes if you want to approve and no if you want to refuse");
        String y =in.next();
        Date date = new Date();
        if(y.equalsIgnoreCase("yes")) {
         if (balance >= req_money) { 
            Transaction t = new Transaction("approval : Granted", req_money, customerId, RC.getCustomerId()); 
            transfer(RC, req_money,bank);
            transactionHistory.push(t);
            RC.transactionHistory.push(t);
            System.out.println("Request approved successfully.");
        } else {
            System.out.println("Insufficient balance to approve the request.");
            return;
        }
    } else if (y.equalsIgnoreCase("no")) {
        // Refuse the request
        Transaction t = new Transaction("approval : Denied", req_money, customerId, RC.getCustomerId()); 
        transactionHistory.push(t);
        RC.transactionHistory.push(t);
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
        Stack<Transaction> tempStack = new Stack<>();
        while (!transactionHistory.isEmpty()) {
            Transaction t = transactionHistory.pop();
            System.out.println(t);
            tempStack.push(t);
        }
        while (!tempStack.isEmpty()) {
            transactionHistory.push(tempStack.pop());
        }
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
    return customerId + "," + name + "," + address + "," + balance + "," + accountType;
    }
}

// Admin Class
class Admin {
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
    System.out.println("Customer added successfully.");
    }

    // Delete a customer account
    public void deleteCustomer(Bank bank, int customerId) {
        Customer customer = bank.searchCustomer(customerId);
        if (customer != null) {
            bank.searchCustomer(customerId).displayDetails();
            bank.searchCustomer(customerId).displayTransactionHistory();
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
        } else {
            System.out.println("Customer not found.");
        }
    }

    // View all transactions
    public void viewAllTransactions(Bank bank) {
        Transaction.displayAllTransactions();
    }

    // Password encryption
    private static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

// Transaction Class
class Transaction {
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
        for (Transaction t : allTransactions) {
            System.out.println(t);
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

