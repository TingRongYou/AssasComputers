/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import static assas.computers.Product.ProductType.KEYBOARD;
import static assas.computers.Product.ProductType.LAPTOP;
import static assas.computers.Product.ProductType.MONITOR;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public class Product {
    
    static final String filePath = "src/textFile/Product.txt";
    protected String productID;
    protected String productName;
    protected double productPrice;
    protected int productStock;
    protected String productDescription;
    protected String productColor;
    protected ProductType productType;
    
    private static ArrayList<Product> productList = new ArrayList<>();
    
    /**
    * constructor
    */
    public Product(String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, ProductType productType) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productColor = productColor;
        this.productType = productType;
            
    }
    
     // Enum for ProductType
    public static enum ProductType{
        KEYBOARD,
        MONITOR,
        LAPTOP;
    }
    
    // Converting input type from string to enum value
    public static ProductType fromString(String type){
        switch(type.toLowerCase()){
            case"keyboard" : return KEYBOARD;
            case "monitor" : return MONITOR;
            case "laptop" : return LAPTOP;
            default: throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
    
    /**
    * getter and setter
    */
    public static String getProductPath(){
        return filePath;
    }
    
    public String getProductID() {
        return productID; 
    }
    
    public String getProductName() { 
        return productName; 
    }
    
    public double getProductPrice() { 
        return productPrice; 
    }
    
    public int getProductStock() { 
        return productStock; 
    }
    
    public String getProductDescription() { 
        return productDescription; 
    }
    
    public String getProductColor() { 
        return productColor; 
    }
    
    public ProductType getProductType() {
        return productType; 
    }

    public String getProductId() {
        return productID;
    }
    
 }
    
        
    

    
    

