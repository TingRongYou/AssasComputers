/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Acer
 */


public class OrderManagement {
    
    // A file use to store latest order ID
     private static final String ORDER_ID_FILE_PATH = "src/textFile/LastOrderId.txt";

     // Generate new order id
    public static String generateOrderID() {
        int lastId = readLastOrderID();
        int newId = lastId + 1;
        saveLastOrderID(newId);
        return String.format("ORD%04d", newId); // e.g., ORD0001
    }

    // Read last used order ID from text file
    private static int readLastOrderID() {
        File file = new File(ORDER_ID_FILE_PATH);
        if (!file.exists()) return 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return (line != null && !line.isEmpty()) ? Integer.parseInt(line.trim()) : 0;
        } catch (IOException | NumberFormatException e) {
            System.out.println(">>> Error reading last order ID. Starting from 0.");
            return 0;
        }
    }

    // Saves the updated ID into the file
    // Ovewrite the file with new value
    private static void saveLastOrderID(int id) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_ID_FILE_PATH))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            System.out.println(">>> Error saving new order ID.");
        }
    }
    
    
}
