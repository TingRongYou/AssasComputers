/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
import java.util.Scanner;

public class AssasComputers {

    /**
     * @param args the command line arguments
     */
    
    public static void displayLogo() {
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .----------------. \n" +
                            "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                            "| |      __      | || |    _______   | || |    _______   | || |      __      | || |    _______   | |\n" +
                            "| |     /  \\     | || |   /  ___  |  | || |   /  ___  |  | || |     /  \\     | || |   /  ___  |  | |\n" +
                            "| |    / /\\ \\    | || |  |  (__ \\_|  | || |  |  (__ \\_|  | || |    / /\\ \\    | || |  |  (__ \\_|  | |\n" +
                            "| |   / ____ \\   | || |   '.___`-.   | || |   '.___`-.   | || |   / ____ \\   | || |   '.___`-.   | |\n" +
                            "| | _/ /    \\ \\_ | || |  |`\\____) |  | || |  |`\\____) |  | || | _/ /    \\ \\_ | || |  |`\\____) |  | |\n" +
                            "| ||____|  |____|| || |  |_______.'  | || |  |_______.'  | || ||____|  |____|| || |  |_______.'  | |\n" +
                            "| |              | || |              | || |              | || |              | || |              | |\n" +
                            "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                            " '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");
    }
    
    public static void main(String[] args) {
        
        
        //Display Assas Computers logo and welcome message
        displayLogo();
        System.out.println("\n\nWelcome to Assas Computer!");
        
        int identity;
        
        // Prompt user to choose their identity (Customer/Staff)
        do {
        Scanner scanner = new Scanner(System.in);
            
        System.out.println("\n\n#" + "=".repeat(25) + " Main Menu " + "=".repeat(26) + "#");
        System.out.println("1. Customer \n2. Staff \n3. Exit");
        System.out.println("#" + "=".repeat(62) + "#");
        System.out.print("Please enter your option(1-3): ");
        identity = scanner.nextInt();
        
        
        switch (identity)
        {
            case 1:
                // Call customerPage method from CustomerController Class
                CustomerController.customerPage();
                break;
               
            case 2:
                // Call staffLogin method from StaffController Class
                StaffController.staffLogin();
                break;
            case 3:
                System.out.println("");
                System.out.println("Thanks for visiting Assas Computers. Hope you have a nice day!!");
                System.out.println("");
                System.exit(0);
            default: System.out.println("\n>>> Please enter valid option(1-3)!!\n");

        }
        scanner.close();

        } while (identity != 1 && identity != 2);
                
    }
    
}
