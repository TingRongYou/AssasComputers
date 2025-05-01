package assas.computers;

import java.util.*;

/**
 * Handles the checkout process
 */
public class CheckoutManager {
    
    private Cart cart;
    private ProductCatalog productCatalog;
    private StockManager stockManager;
    
    /**
     * Constructor initializes with user's cart, product catalog, and stock manager
     */
    public CheckoutManager(Cart cart) {
        this.cart = cart;
        this.productCatalog = ProductCatalog.getInstance();
        this.stockManager = new StockManager();
    }
    
    /**
     * Filters and returns valid checkout items from the provided list of product IDs.
     */
    public Map<String, Integer> getValidCheckoutItems(List<String> selectedIds) {
        Map<String, Integer> selectedItems = new HashMap<>();

        for (String id : selectedIds) {
            id = id.trim().toUpperCase(); // Normalize ID
            if (cart.getCartItems().containsKey(id)) {
                selectedItems.put(id, cart.getCartItems().get(id));
            } else {
                System.out.println(">>> Warning: " + id + " not found in cart - skipping");
            }
        }
        return selectedItems;
    }
    
    /**
     * Generates and prints a checkout summary, showing item names, quantities, and totals.
     */
    public double generateCheckoutSummary(Map<String, Integer> selectedItems) {
        System.out.println("\n#" + "=".repeat(10) + " Checkout Summary " + "=".repeat(10) + "#");
        double total = 0.0;

        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            Product p = productCatalog.getProduct(entry.getKey());
            if (p != null) {
                double itemTotal = p.getProductPrice() * entry.getValue();
                System.out.printf("%-20s x%-3d = RM %.2f%n",
                        p.getProductName(), entry.getValue(), itemTotal);
                total += itemTotal;
            }
        }

        System.out.println("-".repeat(40));
        System.out.printf("TOTAL: RM %.2f%n", total);
        System.out.print("#" + "=".repeat(38) + "#");
        return total;
    }
    
    /**
     * Processes the checkout by validating stock, deducting stock, and removing
     * purchased items from the user's cart.
     */
    public void processCheckout(Map<String, Integer> selectedItems, double amountPaid,
                              Payment.PaymentMethod paymentMethod, String orderId, String dateTime) {
        
        // Step 1: Validate that sufficient stock exists
        if (!stockManager.validateStock(selectedItems)) {
            System.out.println(">>> Checkout failed: Insufficient stock.");
            return;
        }
        
        // Step 2: Deduct purchased quantities from stock
        stockManager.deductStock(selectedItems);
        
        // Step 3: Remove checked out items from the cart
        for (String id : selectedItems.keySet()) {
            cart.deleteProduct(id);
        }
        
        // Step 4: Confirm checkout completion
        System.out.println("\n>>> Checkout completed successfully!");
    }
}