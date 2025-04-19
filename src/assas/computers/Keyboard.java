/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class Keyboard extends Product{
    String type;
    String switches;
    String size;
    

    
    public Keyboard (String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, ProductType productType, String type, String switches, String size) {
        super(productID, productName, productPrice, productStock, productDescription, productColor, productType);
        this.type = type;
        this.switches = switches;
        this.size = size;
    }
    
    public void getDetails() {
        
        String line;
        Scanner scanner = new Scanner(System.in);

       
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(";");

                    if (words[6].equals("Keyboard")) {  
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
        if(productID.charAt(0)!=('K')){
            System.out.println(">>> Error: Product ID Should Start With K !!");
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
    
   public boolean keyboardTypeValidate(String type){
       if(type == null || type.trim().isEmpty()){
           System.out.println(" >>> Error: Keyboard Type Cannot Be Empty!");
           return false;
       }
       
       type = type.trim().toLowerCase();
       
       if(!type.equalsIgnoreCase("mechanical") && !type.equalsIgnoreCase("membrane")){
           System.out.println(">>> Error: Keyboard Type Must Be Either 'Mechanical' or 'Membrane'!");
           return false;
       }
       return true; 
   }
   
  public boolean keyboardSwitchesValidate(String switches) {
    if (switches == null || switches.trim().isEmpty()) {
        System.out.println(">>> Error: Keyboard Switches Cannot Be Empty!");
        return false;
    }

    switches = switches.trim().toLowerCase();

    if (!switches.equals("red") && !switches.equals("blue") && !switches.equals("brown")) {
        System.out.println(">>> Error: Keyboard Switches Must Be 'Red', 'Blue', or 'Brown'!");
        return false;
    }

    // Use StringBuffer to append " switches"
    StringBuffer sb = new StringBuffer(switches);
    sb.append(" switches");

    String formattedSwitches = sb.toString();
    System.out.println("Validated switches value: " + formattedSwitches);
    
    return true;
}

   
   public boolean keyboardSizeValidate(String size){
       if(size == null || size.trim().isEmpty()){
           System.out.println(">>> Keyboard Size Cannot Be Empty!");
           return false;
       }
       
       size = size.trim();
       
       if(!size.matches("^\\d+%$")){
           System.out.println(">>> Error: Keyboard Size Must Be A Number Followed By % (eg: 100% / 75% / 60%))");
           return false;
       }
       return true;
   }
    
    public String getType() {
        return type; }
    
    public String getSwitches() {
        return switches; }
    
    public String getSize() { 
        return size; }
}

        

    

