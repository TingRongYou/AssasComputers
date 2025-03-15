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
    private static final String filePath = "src/CustomerAcc.txt"; // Keep as static constant
    private final String deliveryAddress;
    
    public Customer(String name, String email, String password, String phoneNum, String deliveryAddress) {
        super(name, email, password, phoneNum);
        this.deliveryAddress = deliveryAddress;
    }
    
    public Customer() {
        this.deliveryAddress = null;
    }
    
    @Override
     public void registration() {
        Scanner scanner = new Scanner(System.in);
        String username, email, password, phoneNum, deliveryAddress;
        
        System.out.println("\n\n#" + "=".repeat(27) + "Customer Account Registration" + "=".repeat(28) + "#");
                    
        do{
            System.out.print("Please enter an username: ");
            username = scanner.nextLine();
            if(!Customer.usernameValidate(username)){
                System.out.println(">>> Error: Your Username Should Be Between 1 - 20 Characters!");
                System.out.println(">>> Error: Please Enter Username Again !!");
                System.out.println();
            }
        }while(!Customer.usernameValidate(username));
        
        do{
            System.out.print("Please enter your email: ");
            email = scanner.nextLine();
            if(!Customer.emailValidate(email)){
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
            validPassword = Customer.passwordValidate(password);
            if(!validPassword){
                System.out.println(">>> Error: Please Enter Customer Password Again !!");
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
        
        do{
            System.out.print("Please enter your delivery address: ");
            deliveryAddress = scanner.nextLine();
            if(!addressCheck(deliveryAddress)){
                System.out.println(">>> Error: Your Delivery Address Should Be Between 1 to 50 Characters.");
                System.out.println();
            }
        }while(!addressCheck(deliveryAddress));
        

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { 
            writer.write(username + ";" + email + ";" + password + ";" + phoneNum + ";" + deliveryAddress);
            writer.newLine();
            System.out.println(">>> Registration Successful! Customer Account Data Saved.");
        } catch (IOException e) {
            System.out.println(">>> Error: Unable To Save Customer Account Data.");
        }
        
        login();
        
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
        System.out.println(">>> Error: Cannot Read The File!");
    }
    return false; // Email not found
}

     
     public String getDeliveryAddress(){
         return this.deliveryAddress;
     }
     
     
     @Override
     public void login(){
         
        boolean emailExists = false, isAuthenticated = false;
        String email, password,line;
 
        Scanner scanner = new Scanner(System.in);

        do{
            
            System.out.println("\n\n#" + "=".repeat(27) + "Customer Account Login" + "=".repeat(28) + "#");
            
            System.out.print("Please enter your email: ");
            email = scanner.nextLine();
            
            System.out.print("Please enter your password: ");
            password = scanner.nextLine();
            
            try(BufferedReader reader = new BufferedReader (new FileReader(filePath))){
                while((line = reader.readLine())!=null){
                    String[] words = line.split(";");
                    
                    if (words[1].equals(email)) {
                        emailExists = true;
                        if (words[2].equals(password)) {
                            isAuthenticated = true;
                            System.out.println(">>> Login Successfully! Welcome, " + words[0]);
                            break;
                        }
                        else {
                            System.out.println(">>> Error: Incorrect Password");
                            break;
                        }
                    }

               }
                
                if (!emailExists) {
                         System.out.println(">>> Error: Email Not Exists. Please Try Again.");
                    }
            
           }
            catch(FileNotFoundException e){
            System.out.println("Error: Cannot locate the file");
            }

            catch(IOException e){
                System.out.println("Error: Cannot read the file");
            }  
        }while(!isAuthenticated);
            
        
    }
}
     
