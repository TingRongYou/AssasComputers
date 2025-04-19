/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import static assas.computers.Product.filePath;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class Laptop extends Product{
    String ram;
    String rom;
    String cpu;
    
    public Laptop(String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, ProductType productType, String ram, String rom, String cpu) {
        super(productID, productName, productPrice, productStock, productDescription, productColor, productType);
        this.ram = ram;
        this.rom = rom;
        this.cpu = cpu;
    }
    
    public void getDetails() {
        
        String line;
        Scanner scanner = new Scanner(System.in);

       
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(";");

                    if (words[6].equals("Laptop")) {  
                        for (int i = 0; i < words.length; i++) {
                            System.out.println(words[i]);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(">>> Error: Unable to read account data.");
            }

    }
    
    @Override
    public boolean productIDValidate(String productID){
        boolean isValid = true;
        if(productID.length()!=5){
            System.out.println(">>> Error: Product ID Should Be In 5 Character !!");
            isValid = false;
        }
        if(productID.charAt(0)!=('L')){
            System.out.println(">>> Error: Product ID Should Start With L !!");
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
    
    public boolean validateRam(String ram){
        if(ram != null && ram.matches("\\d+GB")){
            return true;
        }
        System.out.println(">>> Error: RAM Must Be A Number Followed By 'GB' (eg: 8GB, 16GB)");
        return false;
    }
    
    public boolean validateRom(String rom){
        if(rom != null && rom.matches("\\d+GB")){
            return true;
        }
        System.out.println(">>> Error: ROM Must Be A Number Followed By 'GB' (eg: 256GB, 512GB)");
        return false;
    }
    
    public boolean validateCpu(String cpu){
        if(cpu != null){
            return true;
        }
        System.out.println(">>> Error: CPU Cannot Be Empty!");
        return false;
    }
    
    
    public String getRam() { 
        return ram; }
    
    public String getRom() { 
        return rom; }
    
    public String getCpu() { 
        return cpu; }
    
    
    
}
