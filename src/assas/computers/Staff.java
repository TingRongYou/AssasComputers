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

public class Staff extends User {
    private static final String filePath = "src/StaffAcc.txt"; // Keep as static constant
    private String role;
    
    public Staff(String name, String email, String password, String phoneNum) {
        super(name, email, password, phoneNum);
    }
    
    public Staff(){
        
    }
    
    
    @Override
     public void registration() {
        Scanner scanner = new Scanner(System.in);
        String staffID, email, password,phoneNum;
        String role = "";
        
        System.out.println("\n\n#" + "=".repeat(27) + "Customer Account Registration" + "=".repeat(28) + "#");
        
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
                System.out.println(">>> Error: Please Enter Your Email Again !!");
                System.out.println();
            }
        }while(!phoneNumValidate(phoneNum));        
        
        Staff staff = new Staff();
        staff.roleAllocation();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { 
            writer.write(staffID + ";" + email + ";" + password + ";" + staff.getRole() + ";" + phoneNum);
            writer.newLine();
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
            System.out.println("Error: Cannot locate the file.");
        } catch (IOException e) {
            System.out.println("Error: Cannot read the file!");
        }
        return false; // Email not found
         
     }
     
     @Override
    public void login() {
    boolean isAuthenticated = false;
    String email, password, line;
    Scanner scanner = new Scanner(System.in);

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
                        System.out.println(">>> Login Successfully! Welcome, " + words[0]);
                        break;
                    } else {
                        System.out.println(">>> Error: Incorrect Password. Please Try Again.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Unable to read account data.");
            return;  // Exit the method if file reading fails
        }

        if (!emailFound) {
            System.out.println(">>> Error: Email Not Found. Please Try Again.");
        }

    } while (!isAuthenticated);
    
}
    public void roleAllocation(){
        String [] position = {"Manager", "Supervisor", "Inventory Manager", "Package Manager"};
        Random random = new Random();
        int randomIndex = random.nextInt(position.length);
        
        this.role = position[randomIndex];
    }
    
    public String getRole(){
        return role;
    }
    
    public static boolean staffIdValidate(String staffID){
        boolean isValid = true;
        int idLength = 5;
        if(staffID.length()!=5){
            System.out.println(">>> Error: Staff ID Should Be In 5 Character !!");
            isValid = false;
        }
        if(staffID.charAt(0)!=('S')){
            System.out.println(">>> Error: Staff ID Should Start With S !!");
            isValid = false;
        }
        for(int i=1; i<idLength; i++){
            if(!Character.isDigit(staffID.charAt(i))){
                System.out.println(">>> Error: The Second Until Fifth Character Should Be Digit");
                isValid = false;
                break;
            }
        }
        return isValid; 
    }
}
