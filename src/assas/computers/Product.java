/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public abstract class Product {
    
    static final String filePath = "src/Product.txt";
    protected String productID;
    protected String productName;
    protected double productPrice;
    protected int productStock;
    protected String productDescription;
    protected String productColor;
    protected String productType;
    
    private static ArrayList<Product> productList = new ArrayList<>();
    
    Product (String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, String productType) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productColor = productColor;
        this.productType = productType;
            
        }
    
    public String getProductID() {
        return productID; }
    
    public String getProductName() { 
        return productName; }
    
    public double getProductPrice() { 
        return productPrice; }
    
    public int getProductStock() { 
        return productStock; }
    
    public String getProductDescription() { 
        return productDescription; }
    
    public String getProductColor() { 
        return productColor; }

    Object getProductId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
        
    }
    
        
    

    
    

