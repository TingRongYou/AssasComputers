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
public class Customer extends User {
    private static final String filePath = "CustomerAcc.txt"; // Keep as static constant
    private final String deliveryAddress;
    
    public Customer(String name, String email, String password, String phoneNum, String deliveryAddress) {
        super(name, email, password, phoneNum);
        this.deliveryAddress = deliveryAddress;
    }
    
    
    @Override
     public void registration() {
        Scanner scanner = new Scanner(System.in);
        String username, email, password, phoneNum, deliveryAddress;
        
        do{
            System.out.print("Please enter customer username: ");
            username = scanner.nextLine();
            if(!Customer.usernameValidate(username)){
                System.out.println("Error: Your Username should be between 1 - 20 characters!");
                System.out.println("Error: Please enter customer username again !!");
                System.out.println();
            }
        }while(!Customer.usernameValidate(username));
        
        do{
            System.out.print("Please enter customer email: ");
            email = scanner.nextLine();
            if(!Customer.emailValidate(email)){
                System.out.println("Error: Your email should include '@' and '.com'!");
                System.out.println("Error: Please enter customer email again !!");
                System.out.println();
            }
            else if(isEmailRegistered(email)){
                System.out.println("Error: Customer account already registered. Invalid registration!");
                System.out.println();
            }
        }while(!Customer.emailValidate(email) || isEmailRegistered(email));
        
        boolean validPassword = true;
        do{
            System.out.print("Please enter customer password: ");
            password = scanner.nextLine();
            validPassword = Customer.passwordValidate(password);
            if(!validPassword){
                System.out.println("Error: Please enter customer password again !!");
                System.out.println();
            }
        }while(!validPassword);
       
       
        do{
            System.out.print("Please enter customer phone number: ");
            phoneNum = scanner.nextLine();
            if(!phoneNumValidate(phoneNum)){
                System.out.println("Error: Your phone number should start with '01' and be 10 or 11 digits long!");
                System.out.println("Error: Please enter customer email again !!");
                System.out.println();
            }
        }while(!phoneNumValidate(phoneNum));
        
        do{
            System.out.print("Please enter customer delivery address: ");
            deliveryAddress = scanner.nextLine();
            if(!addressCheck(deliveryAddress)){
                System.out.println("Error: Your Delivery Address should be between 1 to 50 characters.");
                System.out.println();
            }
        }while(!addressCheck(deliveryAddress));
        

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { 
            writer.write(getUsername() + ";" + getEmail() + ";" + getPassword() + ";" + getPhoneNum() + ";" + getDeliveryAddress());
            writer.newLine();
            System.out.println("Registration successful! Customer account data saved.");
        } catch (IOException e) {
            System.out.println("Error: Unable to save customer account data.");
        }
     }
     
     @Override
    public boolean isEmailRegistered(String email) {
    try {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(";");
            if (words.length > 1 && words[1].equals(email)) {
                reader.close();
                return true; // Email found
            }
        }
        reader.close();
    } catch (IOException e) {
        System.out.println("Error: Cannot read the file!");
    }
    return false; // Email not found
}

     
     public String getDeliveryAddress(){
         return this.deliveryAddress;
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
            System.out.print("Please Enter Customer account Email:");
            email = scanner.nextLine();
            System.out.print("Please Enter Customer account Password: ");
            password = scanner.nextLine();
            
            while((line = reader.readLine())!=null){
                String[] words = line.split(";");
                if (words[1].equals(email) && words[2].equals(password)) {
                    isAuthenticated = true;
                    System.out.println("Login Successfully! Welcome, " + words[0]);
                    break;
                }
                else{
                    System.out.println("Error: Incorrect email or password. Please try again.");
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
          System.out.println("blablabla");
        }    
    }
}
     
