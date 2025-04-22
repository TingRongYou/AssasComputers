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
public class Inventory {
        private List<Product> productList;

    public Inventory() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public boolean removeProductById(String productId) {
        return productList.removeIf(p -> p.getProductId().equals(productId));
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Product getProductById(String productId) {
        for (Product p : productList) {
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

}
