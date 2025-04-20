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
public class StaffFileHandler {
    private static final String FILE_PATH = "src/textFile/StaffAcc.txt";

    public static boolean saveStaff(String staffID, String email, String password, String phoneNum) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(staffID + ";" + email + ";" + password + ";" + phoneNum);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isEmailRegistered(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(";");
                if (words[1].equalsIgnoreCase(email)) return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
