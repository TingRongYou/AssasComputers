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
    double size;
    
    Keyboard (String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, String type, String switches, double size) {
        super(productID, productName, productPrice, productStock, productDescription, productColor);
        this.type = type;
        this.switches = switches;
        this.size = size;
    }
    
}
