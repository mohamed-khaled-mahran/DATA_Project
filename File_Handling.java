/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bank;

import java.io.*;
import java.util.*;

// File_Handling class for serialization
public class File_Handling {

    public static void saveToFile(List<Customer> objects, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(objects);  // Serialize the list of objects to the file
            System.out.println("Objects saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving objects to file: " + e.getMessage());
        }
    }

    // Method to load objects from a file
    public static List<Customer> loadFromFile(String fileName) {
        List<Customer> objects = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            objects = (List<Customer>) ois.readObject();  // Deserialize the list of objects from the file
            System.out.println("Objects loaded from " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found, returning empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading objects from file: " + e.getMessage());
        }
        return objects;
    }
}

