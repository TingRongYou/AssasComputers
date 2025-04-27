/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import static assas.computers.Staff.Role.ADMIN;
import static assas.computers.Staff.Role.MANAGER;
import static assas.computers.Staff.Role.NORMALSTAFF;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class StaffController {
    
    // Allows staff login
    public static void staffLogin() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n\n#" + "=".repeat(27) + " Staff Account Login " + "=".repeat(28) + "#");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        if (!StaffFileHandler.isEmailRegistered(email)) {
            System.out.println(">>> Error: Email is not registered.");
            System.out.println("");
            AssasComputers.main(new String[0]);
            return;
        }

        Staff staff = StaffFileHandler.loadStaffByEmail(email);

        if (staff == null) {
            System.out.println(">>> Error: Unable to load staff details.");
            System.out.println("");
            return;
        }

        // === Step 1: Verify Password ===
        int pwdAttempts = 3; // Allow password entering for only 3 times
        boolean correctPassword = false;

        while (pwdAttempts-- > 0) {
            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            if (StaffFileHandler.verifyPassword(email, enteredPassword)) {
                correctPassword = true;
                break;
            } else {
                System.out.println(">>> Incorrect password. Try again.");
                System.out.println("");
            }
        }

        if (!correctPassword) {
            System.out.println(">>> Too many failed password attempts. Access denied.");
            System.out.println("");
            AssasComputers.main(new String[0]);
            return;
        }

        // === Step 2: Setup MFA if no key === (Account created by admin does not contain key)
        if (staff.getSetupKey() == null || staff.getSetupKey().isEmpty()) {
            MultiFactorAuthentication mfa = new MultiFactorAuthentication();
            String secretKey = mfa.setupMFA(email);
            staff.setSetupKey(secretKey);
            StaffFileHandler.updateStaff(staff);
            System.out.println(">>> MFA setup key saved. Please scan the QR code and enter the OTP.");
            System.out.println("");
        }

        // === Step 3: Verify OTP ===
        MultiFactorAuthentication mfa = new MultiFactorAuthentication();
        boolean validOTP = false;
        int otpAttempts = 3;
        while (otpAttempts-- > 0 && !validOTP) {
            System.out.print("Enter 6-digit OTP from google Authenticator: ");
            String otpInput = scanner.nextLine();
            validOTP = mfa.verifyOTP(staff.getSetupKey(), otpInput);

            if (!validOTP) {
                System.out.println(">>> Invalid OTP. Please try again.");
                System.out.println("");
            }
        }

        if (!validOTP) {
            System.out.println(">>> MFA failed. Access denied.");
            System.out.println("");
            AssasComputers.main(new String[0]);
            return;
        }

        System.out.println(">>> MFA verified successfully!");
        System.out.println("");

        // === Step 4: Redirect based on role === (Each staff has their own interface)
        switch (staff.getRole()) {
            case ADMIN -> ((Admin) staff).postLoginAction();
            case MANAGER -> ((Manager) staff).postLoginAction();
            case NORMALSTAFF -> ((NormalStaff) staff).postLoginAction();
        }
    }
}
