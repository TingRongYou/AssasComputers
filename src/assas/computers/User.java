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
    String username;
    String email;
    String password;
    String phoneNum;
    String deliveryAddress;
    String filePath = "C:\\Users\\yongc\\Desktop\\AssasComputers\\src\\assas\\computers\\CustomerAcc.txt";
   
    
    public void registration(String email, String password) {
        
            this.email = email;
            this.password = password;
            
            try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                
                while ((line = reader.readLine()) != null) {
                    System.out.println("Niama");
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
    User(String name, String email, String password, String phoneNum, String deliveryAddress) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.deliveryAddress = deliveryAddress;
    }
    
}
