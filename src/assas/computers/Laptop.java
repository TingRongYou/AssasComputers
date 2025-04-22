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
    String ram;
    String rom;
    String cpu;
    
    /**
    * constructor
    */
    public Laptop(String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, ProductType productType, String ram, String rom, String cpu) {
        super(productID, productName, productPrice, productStock, productDescription, productColor, productType);
        this.ram = ram;
        this.rom = rom;
        this.cpu = cpu;
    }
    
    /**
    * getter and setter
    */
    public String getRam() { 
        return ram; }
    
    public String getRom() { 
        return rom; }
    
    public String getCpu() { 
        return cpu; }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
    
}
