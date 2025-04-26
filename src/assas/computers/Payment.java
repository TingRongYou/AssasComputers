/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
public class Payment {
    private String paymentID;
    //Order order;
    private PaymentMethod paymentMethod;
    
    public static enum PaymentMethod {
        CREDITCARD,
        DEBITCARD,
        ONLINEBANKING,
        EWALLET
    }

    public Payment() {
    }
    
    public Payment(String paymentID, PaymentMethod paymentMethod) {
        this.paymentID = paymentID;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public static PaymentMethod choosePaymentMethod(String paymentMethod) {
    if (paymentMethod == null) {
        return null;
    }

    switch (paymentMethod.trim().toUpperCase()) {
        case "CREDITCARD":
            return PaymentMethod.CREDITCARD;
        case "DEBITCARD":
            return PaymentMethod.DEBITCARD;
        case "ONLINEBANKING":
            return PaymentMethod.ONLINEBANKING;
        case "EWALLET":
            return PaymentMethod.EWALLET;
        default:
            return null; // Or throw an IllegalArgumentException
        }
    } 
    
    

    
    
            
            
}
