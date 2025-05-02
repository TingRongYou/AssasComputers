/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import static assas.computers.Product.ProductType.KEYBOARD;
import static assas.computers.Product.ProductType.LAPTOP;
import static assas.computers.Product.ProductType.MONITOR;

/**
 *
 * @author Acer
 */
public abstract class Product {
    
    private static final String PRODUCT_FILE_PATH = "src/textFile/Product.txt";
    protected String productID;
    protected String productName;
    protected double productPrice;
    protected int productStock;
    protected String productDescription;
    protected String productColor;
    protected ProductType productType;
        
    /**
    * constructor
    */
    protected Product(String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, ProductType productType) {
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
    
    public static void displayAllProductTypes() {
        System.out.println("\n\n#" + "=".repeat(20) + " Available Product Types " + "=".repeat(20) + "#");
        for (ProductType type : ProductType.values()) {
            System.out.println("- " + type.name().charAt(0) + type.name().substring(1).toLowerCase());
        }
        System.out.print("#" + "=".repeat(65) + "#");
    }

    
    /**
    * getter and setter
    */
    public static String getProductPath(){
        return PRODUCT_FILE_PATH;
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
    
        
    

    
    

