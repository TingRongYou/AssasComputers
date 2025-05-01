/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
public abstract class Transaction {
    protected String transactionID;
    protected String email;
    protected String dateTime;

    /**
    * constructors
    */
    protected Transaction() {}

    protected Transaction(String transactionID, String email, String dateTime) {
        this.transactionID = transactionID;
        this.email = email;
        this.dateTime = dateTime;
    }

    /**
    * getter and setter
    */
    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    // Abstract method to be implemented by subclasses
    public abstract void printTransactionDetails();
}
