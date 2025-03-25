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
    
    Monitor (String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, String productType, String resolution, String panelSize, String refreshRate) {
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
}
