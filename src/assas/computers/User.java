/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.*;
import java.util.Scanner;

public abstract class User {
    /*static is not needed since static variable shares resources among all instances of the class,
    every time a new user registers, their details will overwrite the previous user's data
    */
    
    private String username;
    private String email;
    private String password;
    private String phoneNum;

    // Constructor
    public User(String name, String email, String password, String phoneNum) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    // Registration method
    public void registration() {
        System.out.println("Congratulations to be a user");
    }

    // Method to check if an email is already registered
    public boolean isEmailRegistered(String email) {
        System.out.println("You had registered successfully");
        return true;
    }

    // Username validation (1-20 characters)
    public static boolean usernameValidate(String username) {
        if (username.length() > 0 && username.length() <= 20) {
            return true;
        }
        else{
        return false;
        }
    }

    // Email validation
    public static boolean emailValidate(String email) {
        if (email.contains("@") && email.contains(".com")) {
            return true;
        }
        else{
        return false;
        }
    }

    // Phone number validation (Starts with 01 and 10-11 digits)
    public static boolean phoneNumValidate(String phoneNum) {
        if (phoneNum.matches("^01\\d{8,9}$")) {
            return true;
        }
        else{
        return false;
        }
    }

    // Password validation
    public static boolean passwordValidate(String password) {
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false, sufficientLength = false;
        boolean validPassword = true;
        char[] specialCharacters = {'!', '@', '#', '%', '*', '&', '$'};

        if (password.length() >= 8 && password.length() <= 15) {
            sufficientLength = true;
        } 
        else{
            System.out.println("Error: Password must be between 8 and 15 characters!");
        }


        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)){
                hasUpper = true;
            }
            else if (Character.isLowerCase(ch)){
                hasLower = true;
            }
            else if (Character.isDigit(ch)){
                hasDigit = true;
            }
            else {
                for (char special : specialCharacters) {
                    if (ch == special) {
                        hasSpecial = true;
                        break;
                    }
                }
            }
        }
        
       
        if (!hasUpper) {
            System.out.println("Error: Password must contain at least one uppercase letter!");
                
        }
        if (!hasLower){
            System.out.println("Error: Password must contain at least one lowercase letter!");
        }
        if (!hasDigit){
            System.out.println("Error: Password must contain at least one digit!");
        }
        if (!hasSpecial){
            System.out.println("Error: Password must contain at least one special character (@, #, $, %, &, *, !, ?)");
        }
    
        return hasUpper && hasLower && hasDigit && hasSpecial && sufficientLength;

  }
    

    // Delivery address validation (1-50 characters)
    public static boolean addressCheck(String deliveryAddress) {
        deliveryAddress = deliveryAddress.trim();
        if (deliveryAddress.length() > 0 && deliveryAddress.length() <= 50) {
            return true;
        }
        
        else{
        return false;
        }
    }
    
    public String getUsername(){
        return username;  
    }
    
    public String getEmail(){
       return email;
    }
    
    public String getPassword(){
        return password;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    
    public void login(){
        boolean isValidEmail = true;
        boolean isValidPassword = true;
        boolean isAuthenticated = false;
        String password;
        String email;
        String line;
        final String filePath = "";
        Scanner scanner = new Scanner(System.in);
        try(BufferedReader reader = new BufferedReader (new FileReader(filePath))){
            do{
            System.out.print("Please Enter User Email");
            email = scanner.nextLine();
            System.out.print("Please Enter User Password:");
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


