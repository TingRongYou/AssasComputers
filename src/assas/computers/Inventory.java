/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Inventory {
    private ArrayList<Product> productList;     
    
   
    public Inventory(){
        this.productList = new ArrayList<>();
    }
    
    public void addProduct(Product product){
            productList.add(product);
            System.out.println("Product added to inventory: " + product.getProductName());
    }
    public void removeProductById(String productId) {
    for (int i = 0; i < productList.size(); i++) {
        if (productList.get(i).getProductId().equals(productId)) {
            productList.remove(i);
            System.out.println(">>> Product removed from inventory: " + productId);
            return;
        }
    }
    System.out.println(">>> Error: Product not found in inventory!");
}

}
