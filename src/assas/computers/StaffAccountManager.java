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
public class StaffAccountManager {
     public void viewAllStaff() {
        System.out.println("\n\n#" + "=".repeat(27) + " Staff Details " + "=".repeat(28) + "#");

        try (BufferedReader reader = new BufferedReader(new FileReader(Staff.getStaffPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] staffDetails = line.split(";");
                System.out.println("Staff ID: " + staffDetails[0] + "\nEmail: " + staffDetails[1] +
                        "\nPassword: " + staffDetails[2] + "\nRole: " + staffDetails[3] +
                        "\nPhone Number: " + staffDetails[4] + "\n");
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Unable to read staff data");
        }
    }

    public boolean updateStaff(String targetStaffID, int fieldChoice, String newValue) {
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

    private boolean writeFile(List<String> content, String targetID, String[] updatedDetails) {
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
