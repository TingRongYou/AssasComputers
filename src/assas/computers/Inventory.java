/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */

// A class managing a list of Product objects
public class Inventory {
    
    // A list to store all products
    private List<Product> productList; 

    /**
    * constructors
    */
    public Inventory() {
        this.productList = new ArrayList<>();
    }

    // Method to add new product into inventory
    public void addProduct(Product product) {
        productList.add(product);
    }

    // Method to remove product from inventory by product ID
    // Returns true if product is removed, false otherwise
    public boolean removeProductById(String productId) {
        return productList.removeIf(p -> p.getProductId().equals(productId));
        // removeIf checks each product (p), and removes if the product ID matches
    }

    public List<Product> getProductList() {
        return productList;
    }

    // Returns the Product if found, otherwise returns null
    public Product getProductById(String productId) {
        for (Product p : productList) {
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

}
