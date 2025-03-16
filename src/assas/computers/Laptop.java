/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
public class Laptop extends Product{
    int ram;
    int rom;
    String cpu;
    
    Laptop(String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, int ram, int rom, String cpu) {
        super(productID, productName, productPrice, productStock, productDescription, productColor);
        this.ram = ram;
        this.rom = rom;
        this.cpu = cpu;
    }
    
}
