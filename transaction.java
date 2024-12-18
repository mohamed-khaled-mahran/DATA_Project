/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank;

import java.io.*;
import java.util.*;

/**
 *
 * @author mahran
 */
public class transaction implements Serializable{
    private static int count= 0;
    public int ID ;
    public String type;
    public float amount;
    public Date date;
    public int customer_id;
    public int sender_id;
    public int receiver_id;

    public transaction(String type, float amount,  int sender_id, int receiver_id) {
        this.type = type;
        this.ID = count++;
        this.amount = amount;
        this.date = new Date();
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public transaction(String type, float amount,  int customer_id) {
        this.type = type;
        this.ID = count++;
        this.amount = amount;
        this.date = new Date();
        this.customer_id = customer_id;
    }
    
    
    public static void recordTransaction(Stack<transaction> transactions, String transactionType, float amount, int senderID, int receiverID) {
      // Create a new transaction
      if(transactionType.equalsIgnoreCase("Transfer")||transactionType.equalsIgnoreCase("request")||transactionType.equalsIgnoreCase("approve")) {
        transaction t = new transaction(transactionType ,amount ,senderID ,receiverID); 
        transactions.push(t);
      }
      
      // Add the new transaction to the customer's transaction history stack
      else {
        transaction w = new transaction(transactionType ,amount ,senderID ); 
        transactions.push(w);


      }
    
          }
}
