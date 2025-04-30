package assas.computers;

import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Acer
 */
public class NormalStaffController {
    
    // Display menu for normal staff
    public void normalStaffMenu(NormalStaff staff) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        boolean exit = true;
        while (exit) {
            System.out.println("\n\n#" + "=".repeat(30) + " Normal Staff Menu " + "=".repeat(30) + "#");
            System.out.println("1. View All Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Logout");
            System.out.println("#" + "=".repeat(79) + "#");
            System.out.print("Please enter your option(1-3): ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(">>> Invalid input. Please enter a number.");
                System.out.println("");
                continue;
            }
            
            System.out.println("");

            switch (choice) {
                case 1:
                    OrderFileHandler.viewAllOrders();
                    break;
                case 2:
                    OrderFileHandler.updateOrderStatus();
                    break;
                case 3:
                    exit = false;
                    System.out.println(">>> Log out successfully\n\n");
                    AssasComputers.main(new String[0]);
                    return;
                default:
                    System.out.println(">>> Invalid option. Please try again.");
                    System.out.println("");
            }
        }
    }
}
