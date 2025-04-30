/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */

public class Payment extends Transaction {
    private PaymentMethod paymentMethod;

    // Enum for payment method
    public static enum PaymentMethod {
        CREDITCARD,
        DEBITCARD,
        ONLINEBANKING,
        EWALLET
    }

    /**
    * constructors
    */
    public Payment() {
        super();
    }

    public Payment(String paymentID, PaymentMethod paymentMethod, String email, String dateTime) {
        super(paymentID, email, dateTime); // call Transaction's constructor
        this.paymentMethod = paymentMethod;
    }

    /**
    * getter and setter
    */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Choose payment method
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
                return null; 
        }
    }

    // Implement abstract method
    @Override
    public void printTransactionDetails() {
        System.out.println("Payment ID : " + getTransactionID());
        System.out.println("Email      : " + getEmail());
        System.out.println("Date       : " + getDateTime());
        System.out.println("Method     : " + paymentMethod);
    }
}
