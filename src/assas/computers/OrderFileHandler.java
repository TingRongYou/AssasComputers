/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package assas.computers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
*
* @author Acer
*/

// A class that manages everthing related to storing and reading order information
public class OrderFileHandler {

    private static final String ORDER_FILE_PATH = "src/textFile/Order.txt";

    // Save new order into file
    public static void saveOrder(Order order, List<String> itemIds) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE_PATH, true))) {
            String orderLine = String.join(",",
                    order.getOrderID(),
                    order.getCustomer().getEmail(),
                    String.format("%.2f", order.getTotalAmount()),
                    order.getOrderDate(),
                    order.getOrderStatus().name(),
                    String.join("|", itemIds)
            );
            writer.write(orderLine);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(">>> Error saving order: " + e.getMessage());
        }
    }

    // Display customer's past orders based on email
    public static void viewOrderHistory(String email) {
        System.out.println("\n\n+" + "-".repeat(100) + "+");
        System.out.printf("| %-10s | %-12s | %-15s | %-20s | %-29s |\n",
                "Order ID", "Amount (RM)", "Status", "Date", "Items");
        System.out.println("+" + "-".repeat(100) + "+");

        boolean found = false;

        try (Scanner scanner = new Scanner(new File(ORDER_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 6 && parts[1].equalsIgnoreCase(email)) {
                    found = true;
                    String items = parts[5].replace("|", ", ");
                    System.out.printf("| %-10s | %-12s | %-15s | %-20s | %-29s |\n",
                            parts[0], parts[2], parts[4], parts[3], items);
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error reading order history: " + e.getMessage());
            return;
        }

        if (!found) {
            System.out.printf("| %-98s |\n", ">>> No order history found.");
        }
        System.out.println("+" + "-".repeat(100) + "+");
    }

    // Allow customer to track shipping progress (Based on email)
    public static void trackOrderStatus(String email) {
        System.out.println("\n+" + "-".repeat(68) + "+");
        System.out.printf("| %-10s | %-20s | %-15s | %-15s |\n",
                "Order ID", "Date", "Status", "Amount (RM)");
        System.out.println("+" + "-".repeat(68) + "+");

        boolean found = false;

        try (Scanner scanner = new Scanner(new File(ORDER_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 5 && parts[1].equalsIgnoreCase(email)) {
                    found = true;
                    System.out.printf("| %-10s | %-20s | %-15s | %-15s |\n",
                            parts[0], parts[3], parts[4], parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error reading order status: " + e.getMessage());
            return;
        }

        if (!found) {
            System.out.printf("| %-66s |\n", ">>> No order records found.");
        }
        System.out.println("+" + "-".repeat(68) + "+");
    }

    // Display every single order in the file for staff
    public static void viewAllOrders() {
        List<String> lines = readLines(ORDER_FILE_PATH); // Use your actual file path

        if (lines.isEmpty()) {
            System.out.println(">>> No orders found.");
            return;
        }

        System.out.println("\n\n#=== All Orders ===#");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String orderId = parts[0];
                String email = parts[1];
                String total = parts[2];
                String dateTime = parts[3];
                String status = parts[4];
                String products = parts[5].replace("|", ", "); // Replace | with , for readability
                System.out.printf("OrderID: %s\nEmail: %s\nTotal: RM%s\nDateTime: %s\nStatus: %s\nProducts: %s\n\n",
                                  orderId, email, total, dateTime, status, products);
            }
        }
    }
        
   // Change order's status by staff
   public static void updateOrderStatus() {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = readLines(ORDER_FILE_PATH);
        boolean updated = false;

        System.out.print("Enter Order ID to update status: ");
        String orderId = scanner.nextLine().trim();

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 6 && parts[0].equalsIgnoreCase(orderId)) {
                System.out.println("Current status: " + parts[4]);
                System.out.print("Enter new status (ORDERACCEPTED / ORDERSHIPPED / ORDERDELIVERED / ORDERCANCELLED): ");
                String newStatus = scanner.nextLine().trim().toUpperCase();

                if (!newStatus.matches("ORDERACCEPTED|ORDERSHIPPED|ORDERDELIVERED|ORDERCANCELLED")) {
                    System.out.println(">>> Invalid status entered.");
                    return;
                }

                parts[4] = newStatus;
                lines.set(i, String.join(",", parts));
                updated = true;
                break;
            }
        }

        if (updated) {
            writeLines(ORDER_FILE_PATH, lines);
            System.out.println(">>> Order status updated successfully.");
        } else {
            System.out.println(">>> Order ID not found.");
        }
    }
   
   // Overloaded method (for customer)
   public static boolean updateOrderStatus(String orderId, String newStatus) {
        List<String> lines = readLines(ORDER_FILE_PATH);
        boolean updated = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 6 && parts[0].equalsIgnoreCase(orderId)) {
                parts[4] = newStatus;
                lines.set(i, String.join(",", parts));
                updated = true;
                break;
            }
        }

        if (updated) {
            writeLines(ORDER_FILE_PATH, lines);
        }
        return updated;
    }
   
    // Helper method
    // Read all lines from a file and return as List<String>
    public static List<String> readLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.out.println(">>> Error reading file: " + filename);
            return new ArrayList<>();
        }
    }

    // Overwrite file with updated lines
    public static void writeLines(String filename, List<String> lines) {
        try {
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            System.out.println(">>> Error writing to file: " + filename);
        }
    }
    
    public static Order getEligibleOrder(String orderId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines

                String[] parts = line.split(",");
                if (parts.length < 6) {
                    // Skip lines with missing fields
                    System.out.println(">>> Invalid line (too few fields): " + line);
                    continue;
                }

                // Remove leading/trailing spaces from each field
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }

                // Check if this line matches the requested orderId
                if (parts[0].equals(orderId)) {
                    // Create a Customer object and set the email
                    Customer customer = new Customer();
                    customer.setEmail(parts[1]);

                    // Create an Order object using the parsed data
                    Order order = new Order(
                        parts[0], // orderId
                        customer,
                        Double.parseDouble(parts[2]), // totalAmount
                        parts[3] // orderDate
                    );

                    // Attempt to parse and set the order status (with error handling)
                    try {
                        order.setOrderStatus(Order.OrderStatus.valueOf(parts[4]));
                    } catch (IllegalArgumentException e) {
                        System.out.println(">>> Invalid order status in file: " + parts[4]);
                    }

                    // Return the successfully constructed Order object
                    return order;
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Handle file reading and number format errors
            System.out.println(">>> Error reading order file: " + e.getMessage());
        }

        // Return null if no matching order is found or an error occurred
        return null;
    }

    // An order is eligible only if its status is ORDERACCEPTED.
    public static boolean isRefundEligible(Order order) {
        return order.getOrderStatus() == Order.OrderStatus.ORDERACCEPTED;
    }
    
}

    
