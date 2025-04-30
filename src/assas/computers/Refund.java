/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Refund extends Transaction {
    private String orderId;
    private double refundAmount;
    private String refundReason;

    /**
    * constructors
    */
    public Refund(String email, String orderId, double refundAmount, String refundReason) {
        super(UUID.randomUUID().toString(), email, getCurrentDateTime()); // Generate ID and dateTime
        this.orderId = orderId;
        this.refundAmount = refundAmount;
        this.refundReason = refundReason;
    }

    // Helper method to get current date and time
    private static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
    * getter and setter
    */
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    // Save refund to file
    public void saveToFile() {
        try (FileWriter writer = new FileWriter("src/textFile/Refunds.txt", true)) {
            writer.write("========================================\n");
            writer.write("Refund ID : " + getTransactionID() + "\n");
            writer.write("Email     : " + getEmail() + "\n");
            writer.write("Order ID  : " + orderId + "\n");
            writer.write(String.format("Amount    : RM%.2f\n", refundAmount));
            writer.write("Reason    : " + refundReason + "\n");
            writer.write("Date      : " + getDateTime() + "\n");
            writer.write("========================================\n");
        } catch (IOException e) {
            System.out.println(">>> Failed to save refund: " + e.getMessage());
        }
    }

    // Implement abstract method from Transaction
    @Override
    public void printTransactionDetails() {
        System.out.println("=== Refund Transaction Details ===");
        System.out.println("Refund ID : " + getTransactionID());
        System.out.println("Email     : " + getEmail());
        System.out.println("Order ID  : " + orderId);
        System.out.printf("Amount    : RM%.2f\n", refundAmount);
        System.out.println("Reason    : " + refundReason);
        System.out.println("Date      : " + getDateTime());
        System.out.println("===================================");
    }

    @Override
    public String toString() {
        return "Refund{" +
                "RefundID='" + getTransactionID() + '\'' +
                ", Email='" + getEmail() + '\'' +
                ", OrderID='" + orderId + '\'' +
                ", Amount=RM" + refundAmount +
                ", Reason='" + refundReason + '\'' +
                ", DateTime='" + getDateTime() + '\'' +
                '}';
    }
}
