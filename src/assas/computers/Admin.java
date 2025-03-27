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
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Admin extends Staff {
    
    public Admin(String staffID, String email, String role) {
        super(staffID, email, "Admin");
    }
    
    @Override 
    public void roleAllocation(){
        this.role = "Admin";
    }
    
    @Override
    public void registration(){
        System.out.println("===== Register Staff Account =====");
        super.registration();
    } 
    
    public void viewAllStaff(){
        System.out.println("\n\n#" + "=".repeat(27) + " Staff Details " + "=".repeat(28) + "#");
        try(BufferedReader reader = new BufferedReader(new FileReader (getStaffPath()))){
            String line;
            while((line = reader.readLine()) != null){
                String[] staffDetails = line.split(";");
                System.out.println("Staff ID: " + staffDetails[0] +"\n" + "Email: " + staffDetails[1] + "\n"
                + "Password: " + staffDetails[2] + "\n" + "Role: " + staffDetails[3] + "\n" 
                + "Phone Number: " + staffDetails[4] + "\n");
            
            }  
        }
        catch(IOException e){
            System.out.println(">>> Error: Unable to read staff data");
        }
    }
    
    public void editStaffDetails(){
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Staff ID to edit: ");
        String targetStaffID = scanner.nextLine();
        
        ArrayList <String> fileContent = new ArrayList<>();
        boolean found = false;
        String[] staffDetails = null; // put the required StaffID details
        
        try(BufferedReader reader = new BufferedReader (new FileReader(getStaffPath()))){
            String line;
            while((line = reader.readLine()) != null){
                String[] words = line.split(";");
                if(words[0].equals(targetStaffID)){
                    found = true;
                    staffDetails = words;
                }
                fileContent.add(line);
            }   
        }
        catch(IOException e){
            System.out.println(">>> Error: Unable to read staff datail!");
            return;
        }
        
        if(!found){
            System.out.println(">>> Error: Staff ID cannot be found!");
            return;
        }
        
       // Display staff details in table format
    System.out.println("\n+--------------+----------------------------+");
    System.out.printf("| %-12s | %-26s |\n", "Field", "Value");
    System.out.println("+--------------+----------------------------+");
    System.out.printf("| %-12s | %-26s |\n", "Staff ID", staffDetails[0]);
    System.out.printf("| %-12s | %-26s |\n", "Email", staffDetails[1]);
    System.out.printf("| %-12s | %-26s |\n", "Role", staffDetails[3]);
    System.out.printf("| %-12s | %-26s |\n", "Phone Number", staffDetails[4]);
    System.out.println("+--------------+----------------------------+\n");

        
        System.out.println("Choose what to edit: ");
        System.out.println("1. Email");
        System.out.println("2. Phone Number");
        System.out.println("3. Role");
        System.out.println();
        System.out.print("Enter your choice (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        String newValue = "";
        switch(choice){
            case 1:
                System.out.print("Enter new email: ");
                newValue = scanner.nextLine();
                staffDetails[1] = newValue;
                break;
            
            case 2:
                System.out.print("Enter new phone number: ");
                newValue = scanner.nextLine();
                staffDetails[4] = newValue;
                break;
            
            case 3: 
                System.out.print("Enter new role: ");
                newValue = scanner.nextLine();
                staffDetails[3] = newValue;
                break;
                
            default: 
                System.out.println(">>>Error: Inavalid choice! Returning to the menu....");
                return;
        }
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(getStaffPath()))){
            for(String line : fileContent) {
                String[] words = line.split(";");
                if(words[0].equals(targetStaffID)){
                    writer.write(String.join(";", staffDetails));
                }
                else{
                    writer.write(line);
                }
                writer.newLine();
            }
            System.out.println(">>> Staff details updated successfully!");
        }
        catch(IOException e){
            System.out.println(">>> Error: Unable to update staff data.");
        }
    }
}
