/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import static assas.computers.User.phoneNumValidate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Acer
 */

public abstract class Staff extends User {
    private static final String filePath = "src/textFile/StaffAcc.txt"; // Keep as static constant
    protected String staffID;
    protected String email;
    protected Role role;
    
    
    
    public Staff(String staffID, String email, Role role){
        this.staffID = staffID;
        this.email = email;        
        this.role = role;
    }
    
    
    @Override
     public void registration() {
        Scanner scanner = new Scanner(System.in);
        String staffID, email, password,phoneNum;
        String role = "";
        
        System.out.println("\n\n#" + "=".repeat(27) + "Staff Account Registration" + "=".repeat(28) + "#");
        
        boolean validID = false;
        do{
            System.out.print("Please enter Staff ID: ");
            staffID = scanner.nextLine();
            validID = Staff.staffIdValidate(staffID);
            
            if(!validID){
                System.out.println(">>> Error: Please Enter Staff ID Again !!");
                System.out.println();
            }
        }while(!validID);
        
        do{
            System.out.print("Please enter your email: ");
            email = scanner.nextLine();
            if(!Staff.emailValidate(email)){
                System.out.println(">>> Error: Your Email Should Include '@' And '.com'!");
                System.out.println(">>> Error: Please Enter Your Email Again !!");
                System.out.println();
            }
            else if(isEmailRegistered(email)){
                System.out.println(">>> Error: Customer Account Already Registered. Invalid Registration!");
                System.out.println();
            }
        }while(!Customer.emailValidate(email) || isEmailRegistered(email));
        
        boolean validPassword = true;
        do{
            System.out.print("Please enter a password: ");
            password = scanner.nextLine();
            validPassword = Staff.passwordValidate(password);
            if(!validPassword){
                System.out.println(">>> Error: Please Enter Staff Password Again !!");
                System.out.println();
            }
        }while(!validPassword);
       
       
        do{
            System.out.print("Please enter your phone number: ");
            phoneNum = scanner.nextLine();
            if(!phoneNumValidate(phoneNum)){
                System.out.println(">>> Error: Your Phone Number Should Start With '01' And Be 10 Or 11 Digits Long!");
                System.out.println(">>> Error: Please Enter Staff Phone Number Again !!");
                System.out.println();
            }
        }while(!phoneNumValidate(phoneNum));        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { 
            writer.write(staffID + ";" + email + ";" + password + ";" + phoneNum);
            writer.newLine();
            writer.flush();
            System.out.println(">>> Registration Successful! Customer Account Data Saved.");
        } catch (IOException e) {
            System.out.println(">>> Error: Unable To Save Customer Account Data.");
        }
        
        System.out.println();
        
        login();
        
     }
     
     @Override
     public boolean isEmailRegistered(String email){
          try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(";");
                if (words[1].equals(email)) {
                    return true; // Email found
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(">>> Error: Cannot locate the file.");
        } catch (IOException e) {
            System.out.println(">>> Error: Cannot read the file!");
        }
        return false; // Email not found
         
     }
     
     @Override
    public void login() {
    boolean isAuthenticated = false;
    String email, password, line;
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\n\n#" + "=".repeat(27) + "Staff Account Login" + "=".repeat(28) + "#");

    do {
        System.out.print("Please enter staff account email: ");
        email = scanner.nextLine();
        System.out.print("Please enter staff account password: ");
        password = scanner.nextLine();

        boolean emailFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(";");

                if (words[1].equals(email)) {  // Check if email exists
                    emailFound = true;
                    if (words[2].equals(password)) { // Check password
                        isAuthenticated = true;
                        System.out.println(">>> Login Successfully! Welcome, " + words[0] + "[" + words[3] + "]");
                        System.out.println("");
                        break;
                    } else {
                        System.out.println(">>> Error: Incorrect Password. Please Try Again.");
                        System.out.println("");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Unable to read account data.");
            System.out.println("");
            return;  // Exit the method if file reading fails
        }

        if (!emailFound) {
            System.out.println(">>> Error: Email Not Found. Please Try Again.");
            System.out.println("");
        }

    } while (!isAuthenticated);
    
}
    public boolean roleValidate(String role){
        boolean found = false;
        String [] position = {"Normal Staff", "Manager", "Admin"};
        for (int i = 0; i<position.length ; i++){
            if(role.equalsIgnoreCase(position[i])){
                found = true;
            }   
            if(!found){
                System.out.println("Please enter a valid role !! (Normal Staff / Manager / Admin)");
            }
        }
        return found;   
    }
    
    public static String getStaffPath(){
        return filePath;
    }
    
    public static boolean staffIdValidate(String staffID){
        boolean isValid = true;
        if(staffID.length()!=5){
            System.out.println(">>> Error: Staff ID Should Be In 5 Character !!");
            isValid = false;
        }
        if(staffID.charAt(0)!=('S')){
            System.out.println(">>> Error: Staff ID Should Start With S !!");
            isValid = false;
        }
        for(int i=1; i<staffID.length(); i++){
            if(!Character.isDigit(staffID.charAt(i))){
                System.out.println(">>> Error: The Second Until Fifth Character Should Be Digit");
                isValid = false;
                break;
            }
        }
        return isValid; 
    }
    
    public abstract void postLoginAction();
    
    public enum Role {
        NORMALSTAFF, ADMIN, MANAGER
    }
    
    @Override
    public String toString() {
        return staffID + " (" + email + ") - Role: " + role;
    }
}
