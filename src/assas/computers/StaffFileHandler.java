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

// A class that stored operation regarding StaffAcc.txt file
public class StaffFileHandler {
    
   private static final String FILE_PATH = "src/textFile/StaffAcc.txt";

   // Save staff account into file
   public static boolean saveStaff(String staffID, String email, String password, String role, String phoneNum) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(staffID + ";" + email + ";" + password + ";" + role + ";" + phoneNum);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

   // Check if email entered is registerd
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
    
    // Display staff acccount through email
    public static Staff loadStaffByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[1].equalsIgnoreCase(email)) {
                    String id = parts[0];
                    String roleStr = parts[3].toUpperCase();
                    Staff.Role role = Staff.Role.valueOf(roleStr.replaceAll("\\s+", "").toUpperCase());
                    String phone = parts[4];
                    String setupKey = parts.length > 5 ? parts[5] : "";

                    return switch (role) {
                        case ADMIN -> new Admin(id, email, role, setupKey);
                        case MANAGER -> new Manager(id, email, role, setupKey);
                        case NORMALSTAFF -> new NormalStaff(id, email, role, setupKey);
                    };
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error loading staff.");
        }
        return null;
    }
    
    // Ensure password entered is valid by finding email in the file
    public static boolean verifyPassword(String email, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3 && parts[1].equals(email)) {
                    String storedPassword = parts[2];
                    return storedPassword.equals(enteredPassword);
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error reading staff file.");
        }
        return false;
    }
    
    // Updating staff data
    public static void updateStaff(Staff updatedStaff) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(updatedStaff.getStaffID())) {
                    // Only update the setupKey at the end, keeping existing data
                    if (parts.length == 5) {
                        // No setup key previously, so we add it
                        line = line + ";" + updatedStaff.getSetupKey();
                    } else if (parts.length == 6) {
                        // Setup key exists, so we replace the last value
                        parts[5] = updatedStaff.getSetupKey();
                        line = String.join(";", parts);
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println(">>> Error reading staff file for update.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(">>> Error writing updated staff.");
        }
    }
    
    // Check if staff id is in the file
    public static boolean isStaffIDExists(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Staff.getStaffPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] staffDetails = line.split(";");
                // Check if the first element in the array (staff ID) matches the input ID
                if (staffDetails.length > 0 && staffDetails[0].equalsIgnoreCase(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error reading staff file.");
        }
        return false;
    }
    
}
