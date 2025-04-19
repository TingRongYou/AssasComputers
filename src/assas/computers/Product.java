/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import static assas.computers.Product.ProductType.KEYBOARD;
import static assas.computers.Product.ProductType.LAPTOP;
import static assas.computers.Product.ProductType.MONITOR;
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
    
    static final String filePath = "src/textFile/Product.txt";
    protected String productID;
    protected String productName;
    protected double productPrice;
    protected int productStock;
    protected String productDescription;
    protected String productColor;
    protected ProductType productType;
    
    private static ArrayList<Product> productList = new ArrayList<>();
    
    public static enum ProductType{
        KEYBOARD,
        MONITOR,
        LAPTOP;
    }
    
    
    Product (String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, ProductType productType) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productColor = productColor;
        this.productType = productType;
            
    }
    
    public static ProductType fromString(String type){
        switch(type.toLowerCase()){
            case"keyboard" : return KEYBOARD;
            case "monitor" : return MONITOR;
            case "laptop" : return LAPTOP;
            default: throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
    
    
    
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
    
    public boolean productIDValidate(String productID){
        boolean isValid = true;
        if(productID.length()!=5){
            System.out.println(">>> Error: Product ID Should Be In 5 Character !!");
            isValid = false;
        }
        if(productID.charAt(0)!=('P')){
            System.out.println(">>> Error: Product ID Should Start With P !!");
            isValid = false;
        }
        for(int i=1; i<productID.length(); i++){
            if(!Character.isDigit(productID.charAt(i))){
                System.out.println(">>> Error: The Second Until Fifth Character Should Be Digit");
                isValid = false;
                break;
            }
        }
        return isValid; 
    }
    
    public boolean productPriceValidate(double productPrice){
        if(productPrice <= 0){
            System.out.println(">>> Error: Product Price must be Greater Than 0!");
            return false;
        }
        
        // Check if the price has more than two decimal places
        String priceStr = String.valueOf(productPrice);
        if(priceStr.contains(".")){
            String[] parts = priceStr.split("\\.");
            if(parts[1].length() > 2){
                System.out.println(">>> Error: Product Price Can Have At Most Decimal Places!");
                return false;
            }
            
        }
        return true;
    }
    
    public boolean productStockValidate (int productStock){
        if(productStock < 0){
            System.out.println(">>> Error: Product Stock cannot be negative!");
            return false;  
        }
        return true;
    }
    
    public boolean productDescriptionValidate(String productDescription){
        if(productDescription == null || productDescription.trim().isEmpty()){
            System.out.println(">>> Error: Product Description Cannot be Empty!");
            return false;       
        }
        
        if(productDescription.length() > 100){
            System.out.println(">>> Error: Product Description Cannot Exceed 100 Characters!");
            return false;     
        }
        return true;
    }
    
    public boolean productColorValidate(String productColor){
        if(productColor == null || productColor.trim().isEmpty()){
            System.out.println(">>> Product Color Cannot be Empty!");
            return false;
        }
        if(productColor.length() > 30){
            System.out.println(">>> Error: Product Color Cannot Exceed 30 Characters!");
            return false;
        }
        if(!productColor.matches("[a-zA-Z\\s]+")){
            System.out.println(">>> Error:  Product Color Should only Contain Letters.");
            return false;
        }
        return true;
    } 
    
    public boolean productTypeValidate(String productType, String productID){
        if(productType == null || productType.trim().isEmpty()){
            System.out.println(">>> Error Product Type Cannot Be Empty! ");
            return false;
        }
        
        char firstLetter = productID.charAt(0);
        productType = productType.trim().toLowerCase();
        
        if((firstLetter == 'K'  && !productType.equals("keyboard")) || 
          (firstLetter == 'L' && !productType.equals("laptop")) ||
          (firstLetter == 'M' && !productType.equals("monitor"))){
            System.out.println(">>> Error: Product Type Does Not Match Product ID");
            return false;
        }
        return true;
    }
 }
    
        
    

    
    

