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
            
            //Read CustomerAcc.txt
            try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                
                while ((line = reader.readLine()) != null) {
                String[] words = line.split(";"); // Assuming the file format is: name;email;password;phone;address

                if (words[1].equals(this.email) && words[2].equals(this.password)) { // FIXED: Use .equals()
                    System.out.println("Error: User already registered. Invalid registration.");
                    break;
                }
            }  
        }  
        catch(FileNotFoundException e){
            System.out.println("Error: Cannot locate the file");
            
        }
        catch(IOException e){
            System.out.println("Error: Cannot read the file!");
        }
    }
    
  public static boolean usernameValidate(String username){
      //Validate the length of username, length from 1 to 20
      if(username.length() >0 && username.length() <= 20){
          return true; 
      }
      //Error message if the username does not fit the required length
      else{
          System.out.println("Error: Your Username should be between 1 - 20 !! ");
          return false;   
      }   
  }
  
  public static boolean emailValidate(String email){
      //Validate the email format, whether including both of @ and .com
      if((email.contains("@")) && (email.contains(".com"))){
          return true;
      }
      //Error message if does not align the format
      else{
          System.out.println("Error: Your email should include @ and .com !!");
          return false;
      }
  }
  
  public static boolean phoneNumValidate(String phoneNum ){
      //Validate the phone number, starts with 01, and at most 10 to 11 number
      if(phoneNum.matches("^01\\d{8,9}$")){
          return true;
      }
      //Error message if not meet the format
      else{
          System.out.println("Error: Your phone number should start be in 01xxxxxxxx and with 10 or 11 number only !!");
          return false;
      }
  }
  
  
  public static boolean passwordValidate(String password){
      boolean hasUpper = false;
      boolean hasLower = false;
      boolean hasDigit = false;
      boolean hasSpecial = false;
      boolean sufficientLength = false;
      char[] specialCharacters = {'!', '@', '#', '%', '*', '&', '$'};
      
      //Validate the length of password, from 8 to 15
      if(password.length()>=8 && password.length() <=15){
          sufficientLength = true;   
      }
      else{
          System.out.println("Error: Your password should within 8 to 15 characters");
          sufficientLength = false;
      }
      
     //Loop the password,and validate
      for(char ch: password.toCharArray()){
          //Validate whether has at least one Uppercase Letter
          if(Character.isUpperCase(ch)){
              hasUpper = true;  
          }
          //Validate whether has at least one Lowerrcase Letter
          else if(Character.isLowerCase(ch)){
              hasLower = true;
          }
          //Validate whether has at least one Digit
          else if(Character.isDigit(ch)){
              hasDigit = true;
          }
          //Validate whether has at least one Special Character
          else{
                  for(char special : specialCharacters){
                      if(ch == special){
                          hasSpecial = true;
                          break;
                      }   
                  }   
          }   
      }
      
      //Error message if does not meet the password format
      if(!hasUpper){
          System.out.println("Error: Password must contain at least one uppercase letter!");
      }
      if(!hasLower){
          System.out.println("Error: Password must contain at least one lowercase letter!");
      }
      if(!hasDigit){
          System.out.println("Error: Password must contain at least one digit!");
      }
      if(!hasSpecial){
          System.out.println("Error: Password must contain at least one special character (@, #, $, %, &, *, !, ?)");
      }
      
      
      return hasUpper && hasLower && hasDigit && hasSpecial && sufficientLength;
  
  }
  
  public static boolean addressCheck(String deliveryAddress){
      //Trim white space
      deliveryAddress = deliveryAddress.trim();
    
      //Validate Length of Delivery Address, from length of 1 to 50
      if(deliveryAddress.length() > 0 && deliveryAddress.length()<=50){
          return true; 
      }
      else{
          System.out.println("Error: Your Delivery Address should be in length of between 1 to 50");
          return false;
      }
  }
}


