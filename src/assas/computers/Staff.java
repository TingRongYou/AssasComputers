/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class Staff extends User {
    private static final String filePath = "StaffAcc.txt"; // Keep as static constant
    

    public Staff(String name, String email, String password, String phoneNum) {
        super(name, email, password, phoneNum);
    }
    
    
    @Override
     public void registration() {
        if (isEmailRegistered(getEmail())) {
            System.out.println("Error: Staff account already registered. Invalid registration!");
            return; // Stop execution if email exists
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) { 
            writer.write(getUsername() + ";" + getEmail() + ";" + getPassword() + ";" + getPhoneNum());
            writer.newLine();
            System.out.println("Registration successful! Staff account data saved.");
        } catch (IOException e) {
            System.out.println("Error: Unable to save staff account data.");
        }
    
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
     public void login(){
        boolean isValidEmail = true;
        boolean isValidPassword = true;
        boolean isAuthenticated = false;
        String password;
        String email;
        String line;
        Scanner scanner = new Scanner(System.in);
        try(BufferedReader reader = new BufferedReader (new FileReader(filePath))){
            do{
            System.out.print("Please Enter Customer account Email");
            email = scanner.nextLine();
            System.out.print("Please Enter Customer account Password:");
            password = scanner.nextLine();
            
            while((line = reader.readLine())!=null){
                String[] words = line.split(";");
                if (words[1].equals(email) && words[2].equals(password)) {
                    isAuthenticated = true;
                    System.out.println("Login Successfully! Welcome, " + words[0]);
                    break;
                }   
                else if(!words[1].equals(email)){
                    System.out.println("Error: Email not found. Please try again.");
                    isValidEmail = false;
                    
                }
                
                else{
                    System.out.println("Error: Incorrect password. Please try again.");
                    isValidPassword = false;
                }
                
           }
            
           }while(!isAuthenticated);
        }
        
        catch(FileNotFoundException e){
            System.out.println("Error: Cannot locate the file");
        }
        
        catch(IOException e){
            System.out.println("Error: Cannot read the file");
        }
        
        finally{
            scanner.close();
        }    
    }
}
