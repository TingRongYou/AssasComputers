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
public class Monitor extends Product{
    String resolution;
    String panelSize;
    String refreshRate;
    
    public Monitor (String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, String productType, String resolution, String panelSize, String refreshRate) {
        super(productID, productName, productPrice, productStock, productDescription, productColor, productType);
        this.resolution = resolution;
        this.panelSize = panelSize;
        this.refreshRate = refreshRate;
    }
    
    public void getDetails() {
        
        String line;
        Scanner scanner = new Scanner(System.in);

       
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(";");

                    if (words[6].equals("Monitor")) {  
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
        if(productID.charAt(0)!=('M')){
            System.out.println(">>> Error: Product ID Should Start With M !!");
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
    
    public boolean refreshRateValidate(String refreshRate){
        if(refreshRate != null && refreshRate.matches("\\d+Hz")){
            return true;
        }
        System.out.println(">>> Error: Refresh Rate Must Be a Number followed By 'Hz' (eg: 60Hz / 75Hz)!");
        return false;
    }
    
    public boolean validateResolution(String resolution){
        if(resolution != null && resolution.matches("\\d+x\\d+")){
            return true;
        }
        System.out.println(">>> Error: Resolution Must Be In 'Width x Height' Format (eg: '1920x1080'");
        return false;
    }
    
    public boolean validatePanelSize(String panelSize){
        if(panelSize != null && panelSize.matches("d+cm x \\d+cm")){
            return true;
        }
        System.out.println(">>> Error: Panel Size Must Be In 'XXcm x XXcm' Format (eg: 50cm x 50cm)");
        return false;
        
    }
    
    public String getResolution() { 
        return resolution; }
    
    public String getPanelSize() { 
        return panelSize; }
    
    public String getRefreshRate() { 
        return refreshRate; }
}
