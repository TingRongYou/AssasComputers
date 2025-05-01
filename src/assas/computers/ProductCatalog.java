package assas.computers;

import assas.computers.Product.ProductType;
import static assas.computers.Product.ProductType.*;
import java.io.*;
import java.util.*;

/**
 * Responsible for loading and managing the product inventory
 */
public class ProductCatalog {
    
    private static ProductCatalog instance;
    private static final String PRODUCT_FILE_PATH = "src/textFile/Product.txt";
    
    // Product catalog storage
    private static Map<String, Product> products;

    private ProductCatalog() {
        products = new HashMap<>();
        loadProducts();
    }
    
    /**
     * Get the instance of ProductCatalog
     */
    public static synchronized ProductCatalog getInstance() {
        if (instance == null) {
            instance = new ProductCatalog();
        }
        return instance;
    }
    
    public Product getProduct(String productID) {
    // Ensure the product ID is trimmed and in the correct format (case-sensitive match)
    if (productID == null || productID.trim().isEmpty()) {
        return null; 
    }
    return products.get(productID.trim()); // Trim productID and match against stored keys
    }
    
    /**
     * Check if product exists in catalog
     */
    public boolean hasProduct(String productID) {
        return products.containsKey(productID);
    }
    
    /**
     * Get all products in the catalog
     * @return HashMap of all products (productID -> Product)
     */
    public Map<String, Product> getAllProducts() {
        // Return a copy to prevent direct modifications
        return new HashMap<>(products);
    }
    
    /**
     * Reload products from file
     */
    public static void reloadProducts() {
        loadProducts();
    }
    
    /**
     * Load products from file into catalog
     */
    private static void loadProducts() {
        // Clear existing products in the catalog before reloading
        products.clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] data = line.split(";");
                if (data.length >= 10) {
                    // Extract product data
                    String id = data[0];
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    int stock = Integer.parseInt(data[3]);
                    String desc = data[4];
                    String color = data[5];
                    String typeString = data[6].trim().toUpperCase(); // Convert to uppercase for consistency
                    
                    // Convert String to enum
                    ProductType productType;
                    try {
                        productType = ProductType.valueOf(typeString);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Product Type in file: " + typeString);
                        continue; // Skip invalid product type and move to next line
                    }

                    // Product specifics fields (RAM, ROM, CPU, etc.)
                    String spec1 = data[7];
                    String spec2 = data[8];
                    String spec3 = data[9];

                    Product product = null;

                    // Create product based on type
                    switch (productType) {
                        case LAPTOP:
                            product = new Laptop(id, name, price, stock, desc, color, productType, spec1, spec2, spec3);
                            break;
                        case MONITOR:
                            product = new Monitor(id, name, price, stock, desc, color, productType, spec1, spec2, spec3);
                            break;
                        case KEYBOARD:
                            product = new Keyboard(id, name, price, stock, desc, color, productType, spec1, spec2, spec3);
                            break;
                        default:
                            System.out.println("Unknown product type: " + typeString);
                            continue; // Skip unknown product type
                    }

                    // Add product to catalog if valid
                    if (product != null) {
                        products.put(id, product);
                    }
                } else {
                    System.out.println("Incomplete product data: " + line);
                }
            }
        } catch (IOException e) {
            // Handle file read errors
            System.out.println("IO Error while loading products: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Handle invalid number formats in price or stock
            System.out.println("Number Format Error: " + e.getMessage());
        }
    }
}