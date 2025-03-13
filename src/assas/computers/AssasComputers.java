/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
import java.io.IOException;
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
    
    public static void registration() {
    
    
        
    }
    
    public static void customerPage() {
        
                Scanner scanner = new Scanner(System.in);
                
                int customerOption;
                
                do {
                    
                    System.out.println("\n\n#" + "=".repeat(26) + "Customer" + "=".repeat(26) + "#");
                    System.out.println("1. Customer registration\n2. Customer login");
                    System.out.println("#" + "=".repeat(60) + "#");
                    System.out.print("Please enter your option(1/2): ");
                    customerOption = scanner.nextInt();
                    
                    switch (customerOption)
                    {
                        case 1:
                            //login()
                            break;

                        case 2:
                            //registration
                            break;
                        default: System.out.println("\nPlease enter valid option(1/2)!!\n");

                    }

                } while (customerOption != 1 && customerOption != 2);
                
                scanner.close();
                
    }
    
    public static void staffPage() {
        
                Scanner scanner = new Scanner(System.in);
                
                int staffOption;
                
                do {
                    
                    System.out.println("\n\n#" + "=".repeat(27) + "Staff" + "=".repeat(28) + "#");
                    System.out.println("1. Staff registration\n2. Staff login");
                    System.out.println("#" + "=".repeat(60) + "#");
                    System.out.print("Please enter your option(1/2): ");
                    staffOption = scanner.nextInt();
                    
                    switch (staffOption)
                    {
                        case 1:
                            //login()
                            break;

                        case 2:
                            //registration
                            break;
                        default: System.out.println("\nPlease enter valid option(1/2)!!\n");

                    }

                } while (staffOption != 1 && staffOption != 2);
                
                scanner.close();
                
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        displayLogo();
        System.out.println("\n\nWelcome to Assas Computer!");
        
        int identity;
        
        do {
            
        System.out.println("\n\n#" + "=".repeat(25) + "Main Menu" + "=".repeat(26) + "#");
        System.out.println("1. Customer \n2. Staff");
        System.out.println("#" + "=".repeat(60) + "#");
        System.out.print("Please enter your option(1/2): ");
        identity = scanner.nextInt();
        
        
        switch (identity)
        {
            case 1:
                customerPage();
                break;
               
            case 2:
                staffPage();
                break;
            default: System.out.println("\nPlease enter valid option(1/2)!!\n");

        }
        
        } while (identity != 1 && identity != 2);
        
        scanner.close();
        
    }
    
}
