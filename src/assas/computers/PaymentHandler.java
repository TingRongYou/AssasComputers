/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 *
 * @author Acer
 */

// A class that handle tasks related to payment processing 
public class PaymentHandler {
    
    // Handle payment logic where user pays for an order
    public Payment processPayment(Payment.PaymentMethod method, double amountPaid, double totalAmount,
        String orderId, Map<String, Integer> validItems, String dateTime) {
        
        // Check if payment is sufficient
        if (amountPaid < totalAmount) {
            System.out.println(">>> Insufficient payment.");
            return null;
        }

        // Generate unique payment ID
        String paymentID = UUID.randomUUID().toString();
        String userEmail = AuthService.getCurrentUserEmail();

        // Create Payment object (updated constructor)
        Payment payment = new Payment(paymentID, method, userEmail, dateTime);


        // Save payment details to file
        savePaymentToFile(payment, amountPaid, totalAmount, userEmail, orderId, validItems, dateTime);

        System.out.printf(">>> Payment of RM%.2f received. Change: RM%.2f\n", amountPaid, amountPaid - totalAmount);
        return payment;
    }

    // Save payment to file with receipt-like format
    private static void savePaymentToFile(Payment payment, double amountPaid, double totalAmount, String email,
        String orderId, Map<String, Integer> validItems, String dateTime) {
        try (FileWriter writer = new FileWriter("src/textFile/Receipt.txt", true)) {
            writer.write("========================================\n");
            writer.write("Order ID : " + orderId + "\n");
            writer.write("Email    : " + email + "\n");
            writer.write("PaymentID: " + payment.getTransactionID() + "\n");
            writer.write("Method   : " + payment.getPaymentMethod() + "\n");
            writer.write(String.format("Total    : RM%.2f\n", totalAmount));
            writer.write(String.format("Paid     : RM%.2f\n", amountPaid));
            writer.write(String.format("Change   : RM%.2f\n", amountPaid - totalAmount));
            writer.write("Date     : " + dateTime + "\n");
            writer.write("Items    : \n");
            for (Map.Entry<String, Integer> entry : validItems.entrySet()) {
                writer.write(" - " + entry.getKey() + " x" + entry.getValue() + "\n");
            }
            writer.write("========================================\n");
        } catch (IOException e) {
            System.out.println(">>> Failed to save receipt: " + e.getMessage());
        }
    }

    // Print receipt
    public static void printReceipt(Payment payment, double amountPaid, double totalAmount, 
        String email, String orderId, Map<String, Integer> validItems, String dateTime) {
        // Print receipt on console
        System.out.println("\n\n#" + "=".repeat(20) + " Receipt " + "=".repeat(20) + "#");
        System.out.println("");
        System.out.println("Order ID : " + orderId);
        System.out.println("Email    : " + email);
        System.out.println("PaymentID: " + payment.getTransactionID());
        System.out.println("Method   : " + payment.getPaymentMethod());
        System.out.printf("Total    : RM%.2f\n", totalAmount);
        System.out.printf("Paid     : RM%.2f\n", amountPaid);
        System.out.printf("Change   : RM%.2f\n", amountPaid - totalAmount);
        System.out.println("Date     : " + dateTime);
        System.out.println("Items    : ");
        for (Map.Entry<String, Integer> entry : validItems.entrySet()) {
            System.out.println(" - " + entry.getKey() + " x" + entry.getValue());
        }
        System.out.println("\n#" + "=".repeat(49) + "#");
        
    }
    
    // View past payment history for specific customer
    public static void viewPaymentHistory(String email) {
        System.out.println("\n\n+--------------------------------------------------------------------------------------+");
        System.out.println("|                                  PAYMENT HISTORY                                     |");
        System.out.println("+--------------------------------------------------------------------------------------+");

        boolean found = false;

        try (Scanner scanner = new Scanner(new File("src/textFile/Receipt.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Check if this block contains the email
                if (line.contains("Order ID :")) {
                    StringBuilder receipt = new StringBuilder();
                    boolean isTargetEmail = false;

                    receipt.append("----------------------------------------------------------------------------------------\n");

                    // Start reading this receipt block
                    while (!line.contains("========================================")) {
                        if (line.contains("Email") && line.contains(email)) {
                            isTargetEmail = true;
                        }

                        if (line.contains(":")) {
                            String[] parts = line.split(":", 2);
                            String key = parts[0].trim();
                            String value = parts.length > 1 ? parts[1].trim() : "N/A";

                            switch (key) {
                                case "Order ID":
                                case "Email":
                                case "PaymentID":
                                case "Method":
                                case "Date":
                                    receipt.append(String.format("%-11s: %s\n", key, value));
                                    break;
                                case "Total":
                                case "Paid":
                                case "Change":
                                    receipt.append(String.format("%-11s: RM %.2f\n", key, Double.parseDouble(value.replace("RM", "").trim())));
                                    break;
                                case "Items":
                                    receipt.append("Items      :\n");
                                    break;
                                default:
                                    break;
                            }
                        } else if (line.trim().startsWith("-")) {
                            receipt.append("    ").append(line.trim()).append("");
                        }

                        if (!scanner.hasNextLine()) break;
                        line = scanner.nextLine();
                    }

                    // If this block was the correct email, print it
                    if (isTargetEmail) {
                        System.out.println(receipt);
                        found = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error reading payment history: " + e.getMessage());
            return;
        }

        if (!found) {
            System.out.println(">>> No payment history found for email: " + email);
        }

        System.out.println("+--------------------------------------------------------------------------------------+");
    }

}
