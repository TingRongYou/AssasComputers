/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Acer
 */

// A class that is use to handle operation regarding file CustomerAcc.txt
public class CustomerFileHandler {
            
    private static final String CUSTOMER_FILE_PATH = "src/textFile/CustomerAcc.txt";

    // Check if the email is stored in CustomerAcc.txt
    public static boolean isEmailRegistered(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(";");
                if (words.length > 1 && words[1].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Cannot read the file!");
        }
        return false;
    }

    // Save customer account information into Customer.txt
    public static boolean saveCustomerData(String username, String email, String password, String phoneNum, String deliveryAddress, String mfaSecret) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH, true))) {
            String customerLine = String.join(";", username, email, password, phoneNum, deliveryAddress, mfaSecret); // Joining multiple string together witha semicolon placed between each value
            writer.write(customerLine);
            writer.newLine();
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println(">>> Error: Unable to save customer account data.");
            return false;
        }
    }
    
    // Return customer detail matches email entered
    public static String[] getCustomerDataByEmail(String email) {
    try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE_PATH))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(";");
            if (fields.length == 6 && fields[1].equalsIgnoreCase(email)) {
                return fields;
            }
        }
    } catch (IOException e) {
        System.out.println(">>> Error: Unable to read customer file.");
    }
    return null;
}

}
