/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public class Order {
    private String orderID;
    private Customer customer;
    private double totalAmount;
    private String orderDate;
    private OrderStatus orderStatus;
    private static ArrayList<Order> orderItem = new ArrayList<>();
    
    public enum OrderStatus {
        ORDERACCEPTED, ORDERSHIPPED, ORDERARRIVED, ORDERCANCELLED
    }
       
    public Order() {
        
    }

    public Order(String orderID, Customer customer, double totalAmount, String orderDate) {
        this.orderID = orderID;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static ArrayList<Order> getOrderItem() {
        return orderItem;
    }

    public static void setOrderItem(ArrayList<Order> orderItem) {
        Order.orderItem = orderItem;
    }       
        
}
