/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Acer
 */
public class User {
    private String username;
    private String email;
    private String password;
    private String phoneNum;
    private String deliveryAddress;
    private String filePath = "CustomerAcc.txt";
    
     User(String name, String email, String password, String phoneNum, String deliveryAddress) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.deliveryAddress = deliveryAddress;
    }
   
    
    public void registration(String email, String password) {
        
            this.email = email;
            this.password = password;
            
            try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                
                while ((line = reader.readLine()) != null) {
                String[] words = line.split(";"); // Assuming the file format is: name;email;password;phone;address

                if (words[1].equals(this.email) && words[2].equals(this.password)) { // FIXED: Use .equals()
                    System.out.println("User already registered. Invalid registration.");
                    break;
                }
            }
            
        }  
        catch(FileNotFoundException e){
            System.out.println("Cannot locate the file");
            
        }
        catch(IOException e){
            System.out.println("Cannot read the file!");
        }
    
}
  public static boolean usernameValidate(String username){
      boolean validity = true;
      if(username.length() <= 20){
          validity = true; 
      }
      else{
          validity = false;
      }
      return validity;   
      
  }
  
  public static boolean emailValidate(String email){
      boolean validity = true;
      if((email.contains("@")) && (email.contains(".com"))){
          validity = true;
      }
      else{
          validity =false;
      }
      return validity;
  }
  
  public static boolean phoneNumValidate(String phoneNum ){
      boolean validity = true;
      if(phoneNum.matches("^01\\d{8,9}$")){
          validity = true;
      }
      else{
          validity = false;
      }
      return validity; 
  }
  public static boolean passwordValidate(String password){
      boolean hasUpper = false;
      boolean hasLower = false;
      boolean hasDigit = false;
      boolean hasSpecial = false;
      boolean sufficientLength = true;
      boolean validity = true;
      char[] specialCharacters = {'!', '@', '#', '%', '*', '&', '$'};
      
      if(password.length()<8 || password.length() >15){
          sufficientLength = false;   
      }
      else{
          sufficientLength = true;
      }
      
      for(char ch: password.toCharArray()){
          if(Character.isUpperCase(ch)){
              hasUpper = true;  
          }
          else if(Character.isLowerCase(ch)){
              hasLower = true;
          }
          else if(Character.isDigit(ch)){
              hasDigit = true;
          }
          else{
                  for(char special : specialCharacters){
                      if(ch == special){
                          hasSpecial = true;
                          break;
                      }   
                  }   
          }   
      }
      return hasUpper && hasLower && hasDigit && hasSpecial && sufficientLength;
  
  }
  
  public static boolean addressCheck(String deliveryAddress){
      boolean validity = true;
      if(deliveryAddress.length() > 0 && deliveryAddress.length()<=50){
          validity = true; 
      }
      return validity;
  }
}


