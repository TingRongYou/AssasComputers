/*
    Team Member: Ting Rong You, 
                 Yong Chong Xin,
                 Tan Hong Yu,
                 Wan Zi Kang

    Project overview: 
                    - Title: Assas Computers
                    - Types of user: Staff(Normal Staff, Manager, Admin) and Customer
                    - Type of product: Keyboard, Laptop, Monitor
                    - Process to order a product: Register(optional) -> Login -> Add product to cart -> Proceed checkout -> Make payment -> Wait for staff to change order status -> Request refund(optional)
                    - Function for customers: Register, login, search&filer product, add product to cart view cart, delete product from cart, view order history, view payment history, request refund
                    - Function for admin: Register new staff, edit existing staff, view all staff
                    - Function for manager: Add product, remove product, update product, search product, view all product, view all orders, update order status
                    - Fucntion for normal staff: View all orders, update order status

    Additional features included: 
                    - Multi Factor Authentication (Using Google Authenticator, Please download)
                    - Text file

    Text file included:
                    - Total of (8) file
                    - CustomerAcc.txt
                    - StaffAcc.txt
                    - Cart.txt
                    - LastOrderId.txt
                    - Order.txt
                    - Product.txt
                    - Receipt.txt
                    - Refunds.txt
                            
*/

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

    public static void displayLogo() {
        System.out.println("""
                            .----------------.  .----------------.  .----------------.  .----------------.  .----------------. 
                           | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |
                           | |      __      | || |    _______   | || |    _______   | || |      __      | || |    _______   | |
                           | |     /  \\     | || |   /  ___  |  | || |   /  ___  |  | || |     /  \\     | || |   /  ___  |  | |
                           | |    / /\\ \\    | || |  |  (__ \\_|  | || |  |  (__ \\_|  | || |    / /\\ \\    | || |  |  (__ \\_|  | |
                           | |   / ____ \\   | || |   '.___`-.   | || |   '.___`-.   | || |   / ____ \\   | || |   '.___`-.   | |
                           | | _/ /    \\ \\_ | || |  |`\\____) |  | || |  |`\\____) |  | || | _/ /    \\ \\_ | || |  |`\\____) |  | |
                           | ||____|  |____|| || |  |_______.'  | || |  |_______.'  | || ||____|  |____|| || |  |_______.'  | |
                           | |              | || |              | || |              | || |              | || |              | |
                           | '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |
                            '----------------'  '----------------'  '----------------'  '----------------'  '----------------' """);
    }
    
    public static void main(String[] args) {

        // Display Assas computers logo
        displayLogo();
        System.out.println("\n\nWelcome to Assas Computer!");

        Scanner scanner = new Scanner(System.in);
        int identity;

        while (true) {
            System.out.println("\n\n#" + "=".repeat(25) + " Main Menu " + "=".repeat(26) + "#");
            System.out.println("1. Customer \n2. Staff \n3. Exit");
            System.out.println("#" + "=".repeat(62) + "#");

            // Prompt the users to choose their identity (Customer/Staff)
            System.out.print("Please enter your option (1-3): ");

            if (scanner.hasNextInt()) {
                identity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (identity) {
                    case 1:
                        // Call CustomerController from customerPage
                        CustomerController.customerPage();
                        break;
                    case 2:
                        // Call StaffController from staffLogin
                        StaffController.staffLogin();
                        break;
                    case 3:
                        System.out.println("\nThanks for visiting Assas Computers. Hope you have a nice day!!\n");
                        System.exit(0);
                        break;
                    default:
                        System.out.println(">>> Please enter a valid option (1-3)!!");
                }

            } else {
                System.out.println(">>> Invalid input. Please enter a number between 1 and 3.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
}
