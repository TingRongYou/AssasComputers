package assas.computers;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Handles stock management operations
 */
public class StockManager {

    private static final String PRODUCT_FILE_PATH = "src/textFile/Product.txt";
    private ProductCatalog productCatalog;

    /**
     * Constructor initializes the product catalog instance
     */
    public StockManager() {
        this.productCatalog = ProductCatalog.getInstance();
    }

    /**
     * Validates whether the selected items have enough stock for checkout
     */
    public boolean validateStock(Map<String, Integer> selectedItems) {
        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            Product product = productCatalog.getProduct(entry.getKey());

            if (product == null) {
                System.out.println(">>> Error: Product not found in catalog: " + entry.getKey());
                return false;
            }

            if (product.getProductStock() < entry.getValue()) {
                System.out.println(">>> Error: Insufficient stock for " + product.getProductName());
                return false;
            }
        }

        // All products have sufficient stock
        return true;
    }

    /**
     * Deducts the purchased quantities from the stock and saves the result to the product file.
     * Also reloads the product catalog to reflect the updated stock levels.
     */
    public static void deductStock(Map<String, Integer> selectedItems) {
        List<String> updatedLines = new ArrayList<>();

        try {
            // Read all lines from the product file
            List<String> lines = Files.readAllLines(Paths.get(PRODUCT_FILE_PATH));

            for (String line : lines) {
                String[] parts = line.split(";");
                
                // Skip malformed lines
                if (parts.length < 4) {
                    updatedLines.add(line);
                    continue;
                }

                String productId = parts[0].trim();
                int stock = Integer.parseInt(parts[3].trim());

                // Check if this product needs stock deducted
                if (selectedItems.containsKey(productId)) {
                    int quantityToDeduct = selectedItems.get(productId);

                    if (stock >= quantityToDeduct) {
                        stock -= quantityToDeduct; // Deduct the stock
                    } else {
                        System.out.println(">>> Insufficient stock for product: " + productId + ", skipping.");
                    }

                    // Update the stock value
                    parts[3] = String.valueOf(stock);
                }
                updatedLines.add(String.join(";", parts));
            }

            // Write all updated lines back to the product file
            Files.write(Paths.get(PRODUCT_FILE_PATH), updatedLines);

            // Reload the product catalog to reflect updated stock levels in memory
            ProductCatalog.reloadProducts();

        } catch (IOException e) {
            System.out.println(">>> Error accessing product.txt: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(">>> Error parsing stock number: " + e.getMessage());
        }
    }
}