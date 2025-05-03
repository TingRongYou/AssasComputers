package assas.computers;

import java.util.*;

/**
 * Represents a user's shopping cart
 * Handles core cart functionality (add, remove, display items)
 */
public class Cart {
    
    private final String email;
    private HashMap<String, Integer> cartItems; // Key: productID, Value: quantity
    ProductCatalog productCatalog;
    private CartHandler handler;
    
    /**
     * Constructor - creates a new cart for a user
     */
    public Cart(String email) {
        this.email = email;
        this.cartItems = new HashMap<>();
        this.productCatalog = ProductCatalog.getInstance();
        this.handler = new CartHandler(this);
        
        // Load existing cart data
        handler.loadCart();
    }
    
    /**
     * Get user's email
     */
    public String getEmail() { 
        return email; 
    }
    
    /**
     * Get cart items
     */
    public HashMap<String, Integer> getCartItems() { 
        return new HashMap<>(cartItems); 
    }
    
    /**
     * Set cart items (used by CartPersistence when loading)
     */
    public void setCartItems(HashMap<String, Integer> items) {
        this.cartItems = new HashMap<>(items);
    }
    
    /**
     * Add item to cart
     */
    public void addItem(String productID, int qty) {
        if (qty <= 0) {
            System.out.println(">>> Error: Quantity must be greater than zero.");
            return;
        }
        
        Product product = productCatalog.getProduct(productID);
        if (product == null) {
            System.out.println(">>> Error: Product not found.");
            return;
        }

        int currentQty = cartItems.getOrDefault(productID, 0);
        if (product.getProductStock() < currentQty + qty) {
            System.out.println(">>> Error: Not enough stock for " + product.getProductName() + 
                              " (Available: " + product.getProductStock() + ")");
            return;
        }

        cartItems.put(productID, currentQty + qty);
        System.out.println(">>> "+ qty +" x "+ product.getProductName() + " added to cart.");
        handler.saveCart();
        
    }
    
    /**
     * Delete a product from cart
     */
    public void deleteProduct(String productID) {
        if (!cartItems.containsKey(productID)) {
            System.out.println(">>> Error: Product " + productID + " not found in your cart.");
            return;
        }

        // Remove from in-memory cart
        cartItems.remove(productID);

        // Save cart after modification
        handler.saveCart();
    }
    
    /**
     * Display cart contents
     */
    public void displayCart() {
        System.out.println("\n\n#====================================================================#");
        System.out.println("|                            Shopping Cart                           |");
        System.out.println("#====================================================================#");
        System.out.println("| ID       | Item                |   Qty  |    Price     |   Total   |");
        System.out.println("#====================================================================#");

        double grandTotal = 0.0;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            Product product = productCatalog.getProduct(entry.getKey());
            if (product != null) {
                // Format product ID (first 8 characters)
                String displayID = product.getProductID().length() > 8 ? 
                    product.getProductID().substring(0, 5) + "..." : product.getProductID();

                // Format product name (first 18 characters)
                String displayName = product.getProductName().length() > 18 ? 
                    product.getProductName().substring(0, 15) + "..." : product.getProductName();

                // - for left align, .2 for 2 decimal places
                double itemTotal = product.getProductPrice() * entry.getValue();
                System.out.printf("| %-8s | %-18s  | %5d  | %10.2f   | %9.2f |%n", 
                    displayID, 
                    displayName, 
                    entry.getValue(), 
                    product.getProductPrice(), 
                    itemTotal);

                grandTotal += itemTotal;
            }
        }

        System.out.println("#====================================================================#");
        System.out.printf("| %-48s %15.2f   |%n", "Grand Total:  ", grandTotal);
        System.out.print("#====================================================================#");
    }
    
    /**
     * Clear all items from cart
     */
    public void clearCart() {
        cartItems.clear();
        handler.saveCart();
        System.out.println(">>> Cart cleared successfully.");
    }
}