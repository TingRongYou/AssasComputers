/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.util.Scanner;

/**
 *
 * @author Acer
 */

// A class that store action that can be perform by admin after login
public class AdminController {
    private static final Scanner scanner = new Scanner(System.in);

    private static boolean validOption = true;
   
    // Display menu for an admin
   public static void adminMenu(Admin admin) {
        Scanner scanner = new Scanner(System.in);  // Ensure this is initialized
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n\n#" + "=".repeat(27) + " Admin Menu " + "=".repeat(28) + "#");
            System.out.println("1. Register New Staff");
            System.out.println("2. Edit Existing Staff");
            System.out.println("3. View All Staff");
            System.out.println("4. Logout");
            System.out.println("#" + "=".repeat(94) + "#");
            System.out.print("Please enter your option (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerStaff();
                    break;
                case "2":
                    editStaff();
                    break;
                case "3":
                    viewAllStaff();
                    break;
                case "4":
                    System.out.println(">>> Logged out successfully.\n\n");
                    AssasComputers.main(new String[0]);
                    keepRunning = false;
                    break;
                default:
                    System.out.println(">>> Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    // Admin register an account for a new staff
    private static void registerStaff() {
        System.out.println("\n\n#" + "=".repeat(27) + " Register New Staff " + "=".repeat(28) + "#");

        // Validation are done before the data are stored, the validation are predefined in UserAccountValidation class
        String id;
        do {
            System.out.print("Enter staff ID (Format: Sxxxx): ");
            id = scanner.nextLine();
            if (!UserAccountValidation.isValidStaffID(id)) {
                System.out.println(">>> Invalid Staff ID. It must start with 'S' followed by 4 digits (e.g., S0001).\n");
            }
        } while (!UserAccountValidation.isValidStaffID(id));

        String email;
        do {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (!UserAccountValidation.emailValidate(email)) {
                System.out.println(">>> Invalid email format.\n");
            }
        } while (!UserAccountValidation.emailValidate(email));

        String password;
        do {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if (!UserAccountValidation.passwordValidate(password)) {
                System.out.println(">>> Password must be 8-15 characters long, contain at least one uppercase, one lowercase, one digit, and one special character (!@#%*&$).\n");
            }
        } while (!UserAccountValidation.passwordValidate(password));

        String phone;
        do {
            System.out.print("Enter phone number: ");
            phone = scanner.nextLine();
            if (!UserAccountValidation.phoneNumValidate(phone)) {
                System.out.println(">>> Invalid phone number format. It must start with '01' and contain 10 or 11 digits total.\n");
            }
        } while (!UserAccountValidation.phoneNumValidate(phone));

        // Ensures that the role entered are correct
        String role;
        do {
            System.out.print("Enter role (ADMIN / MANAGER / NORMALSTAFF): ");
            role = scanner.nextLine().toUpperCase();
            if (!role.equals("ADMIN") && !role.equals("MANAGER") && !role.equals("NORMALSTAFF")) {
                System.out.println(">>> Invalid role. Please enter ADMIN, MANAGER, or NORMALSTAFF.\n");
            }
        } while (!role.equals("ADMIN") && !role.equals("MANAGER") && !role.equals("NORMALSTAFF"));

        // From StaffFileHandler class, the staff information are staff through the saveStaff method by passing required information
        boolean success = StaffFileHandler.saveStaff(id, email, password, role, phone);
        
        // Based on the value of success, if true, then display successful message while if saving failed, display unsuccessful message
        System.out.println(success ? ">>> Staff registered successfully." : ">>> Registration failed.");
    }
    
    // Admin modify information of a staff account
    private static void editStaff() {
        System.out.println("\n\n#" + "=".repeat(27) + " Edit Staff Details " + "=".repeat(28) + "#");

        String id;
        while (true) {
            System.out.print("Enter staff ID to edit: ");
            id = scanner.nextLine();
            if (!UserAccountValidation.isValidStaffID(id)) {
                System.out.println(">>> Error: Invalid staff ID format. It must be in format Sxxxx.");
                System.out.println("");
                continue;
            }

            if (!StaffFileHandler.isStaffIDExists(id)) {
                System.out.println(">>> Error: Staff ID not found in records.");
                System.out.println("");
            } else {
                break;
            }
        }

        // There are only a few field that are available for changes, the admin need to choose which information to modify
        int fieldChoice;
        // Use infinite loop to enhance code readability, reduce the amount of boolean variable
        while (true) {
            System.out.println("1. Edit Email");
            System.out.println("2. Edit Phone Number");
            System.out.println("3. Edit Role");
            System.out.println("#" + "=".repeat(75) + "#");
            System.out.print("Please enter your option(1-3): ");
            try {
                // Convert String to integer
                fieldChoice = Integer.parseInt(scanner.nextLine());
                if (fieldChoice >= 1 && fieldChoice <= 3) break; // If the fieldChoice is within range of 1-3, then break
                else System.out.println(">>> Error: Please enter a number between 1 and 3.\n");
                System.out.println("");
                // NumberFormatException when Integer.parseInt failed due to invalid input such as "abc" or 1.5
            } catch (NumberFormatException e) {
                System.out.println(">>> Error: Invalid input. Please enter a number.\n");
                System.out.println("");
            }
        }

        // Capturing new data 
        String newValue;
        while (true) {
            System.out.print("Enter new value: ");
            newValue = scanner.nextLine();

            if (fieldChoice == 1) {
                if (UserAccountValidation.emailValidate(newValue)) break;
                System.out.println(">>> Error: Invalid email format.");
                System.out.println("");
            } else if (fieldChoice == 2) {
                if (UserAccountValidation.phoneNumValidate(newValue)) break;
                System.out.println(">>> Error: Invalid phone number. Must start with 01 and contain 10 or 11 digits.");
                System.out.println("");
            } else if (fieldChoice == 3) {
                newValue = newValue.toUpperCase();
                if (newValue.equals("ADMIN") || newValue.equals("MANAGER") || newValue.equals("NORMALSTAFF")) break;
                System.out.println(">>> Error: Role must be ADMIN, MANAGER, or NORMALSTAFF.");
                System.out.println("");
            }
        }

        boolean success = StaffAccountManager.editStaffDetails(id, fieldChoice, newValue);
        System.out.println(success ? ">>> Staff details updated." : ">>> Failed to update staff details.");
    }

    // Admin view all of the staff account created
    private static void viewAllStaff() {
        StaffAccountManager.viewAllStaff();
    }
}
