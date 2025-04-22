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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */

// A class that store operation regarding staff account
public class StaffAccountManager {
    
    // Assist admin to view all of the staff account in assas computers
     public static void viewAllStaff() {
        System.out.println("\n#" + "=".repeat(42) + " Staff Details " + "=".repeat(41) + "#");

        // Print header
        System.out.printf("%-10s | %-25s | %-15s | %-12s | %-13s | %-10s\n",
                "Staff ID", "Email", "Password", "Role", "Phone", "MFA");
        System.out.println("-".repeat(100));

        try (BufferedReader reader = new BufferedReader(new FileReader(Staff.getStaffPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] staffDetails = line.split(";");
                if (staffDetails.length < 5) {
                    System.out.println(">>> Warning: Incomplete staff record. Skipping.");
                    continue;
                }

                String staffID = staffDetails[0];
                String email = staffDetails[1];
                String password = staffDetails[2];
                String role = staffDetails[3];
                String phone = staffDetails[4];
                String mfaStatus = (staffDetails.length == 6 && !staffDetails[5].isEmpty()) ? "Enabled" : "Not Set";

                // Print each row
                System.out.printf("%-10s | %-25s | %-15s | %-12s | %-13s | %-10s\n",
                        staffID, email, password, role, phone, mfaStatus);
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Unable to read staff data");
        }

        System.out.println("#" + "=".repeat(98) + "#\n");
    }

    // Assist admin in editing details for a staff
    public static boolean editStaffDetails(String targetStaffID, int fieldChoice, String newValue) {
        ArrayList<String> fileContent = new ArrayList<>();
        String[] staffDetails = null;
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(Staff.getStaffPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(";");
                if (details[0].equals(targetStaffID)) {
                    found = true;
                    staffDetails = details;
                }
                fileContent.add(line);
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Unable to read staff file.");
            return false;
        }

        if (!found || staffDetails == null) return false;

        switch (fieldChoice) {
            case 1 -> staffDetails[1] = newValue;  // Email
            case 2 -> staffDetails[4] = newValue;  // Phone
            case 3 -> staffDetails[3] = newValue;  // Role
        }

        return writeFile(fileContent, targetStaffID, staffDetails);
    }

    // Help to write information into file during editStaffDetails
    private static boolean writeFile(List<String> content, String targetID, String[] updatedDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Staff.getStaffPath()))) {
            for (String line : content) {
                String[] parts = line.split(";");
                if (parts[0].equals(targetID)) {
                    writer.write(String.join(";", updatedDetails));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println(">>> Error: Failed to update file.");
            return false;
        }
    }
}
