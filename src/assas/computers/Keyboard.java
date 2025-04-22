/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
public class Keyboard extends Product{
    String type;
    String switches;
    String size;
    
    /**
    * constructor
    */
    public Keyboard (String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, ProductType productType, String type, String switches, String size) {
        super(productID, productName, productPrice, productStock, productDescription, productColor, productType);
        this.type = type;
        this.switches = switches;
        this.size = size;
    }
    
    /**
    * getter and setter
    */
    public String getType() {
        return type; }
    
    public String getSwitches() {
        return switches; }
    
    public String getSize() { 
        return size; }

    public void setType(String type) {
        this.type = type;
    }

    public void setSwitches(String switches) {
        this.switches = switches;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
}

        

    

