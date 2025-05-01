package assas.computers;

import assas.computers.Product.ProductType;
import static assas.computers.Product.ProductType.KEYBOARD;
import static assas.computers.Product.ProductType.LAPTOP;
import static assas.computers.Product.ProductType.MONITOR;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Cart {
    
    // Define file path for cart and product
    static final String CARTFILEPATH = "src/textFile/Cart.txt";
    static final String PRODUCTFILEPATH = "src/textFile/Product.txt";
    
    // A data structure that stores data in key-value pairs 
    static public HashMap<String, Product> productCatalog = new HashMap<>(); // Key: productID, Value: product type (e.g. Keyboard/Laptop)

    private String email; 
    private HashMap<String, Integer> cartItems; // Key: productID, Value: quantity

    // Static block to load products once
    static {
        loadProducts();
    }

    /** 
     * constructors 
     */
    public Cart(String email) {
        this.email = email;
        this.cartItems = new HashMap<>();
        loadCart(); // Load user's existing cart data
    }
    
    /** 
     * getter 
     */
    public String getEmail() { return email; }
    public HashMap<String, Integer> getCartItems() { return new HashMap<>(cartItems); }
    
    // Load products into catalog
    public static void loadProducts() {
        
        // Clear existing products in the catalog before reloading
        productCatalog.clear();
        
        // Read file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTFILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                // Split line into parts using semicolon
                String[] data = line.split(";");
                if (data.length >= 10) {
                    // Extract product data
                    String id = data[0];
                    String name = data[1];
                    // Convert String to double for calculation
                    double price = Double.parseDouble(data[2]);
                    int stock = Integer.parseInt(data[3]);
                    String desc = data[4];
                    String color = data[5];
                    String typeString = data[6];
                    
                    // Convert String to enum
                    ProductType productType = ProductType.valueOf(typeString.trim().toUpperCase());

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
                            continue;
                    }

                    // Add product to catalog if valid
                    if (product != null) {
                        productCatalog.put(id, product);
                    }
                } else {
                    System.out.println("Incomplete product data: " + line);
                }
            }
        } catch (IOException e) {
            // Handle file read errors
            System.out.println("IO Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Handle invalid number formats in price or stock
            System.out.println("Number Format Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Handle invalid enum values (wrong product type)
            System.out.println("Invalid Product Type: " + e.getMessage());
        }

    }
    
    // Load user's cart from file
    public void loadCart() {
        cartItems.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(CARTFILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                // Check for new format (5 fields), ensure it matches user email    
                if (data.length >= 5 && data[0].equals(email)) {
                    String productID = data[1];
                    int quantity = Integer.parseInt(data[4]); // Quantity is now at index 4
                    // Add to cart item                     
                    cartItems.put(productID, cartItems.getOrDefault(productID, 0) + quantity);
                }
                // Optional: Handle old format (3 fields) for backward compatibility
                else if (data.length == 3 && data[0].equals(email)) {
                    String productID = data[1];
                    int quantity = Integer.parseInt(data[2]);
                    cartItems.put(productID, cartItems.getOrDefault(productID, 0) + quantity);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(">>> Error: Failed to load cart.");
        }
    }
        // Add item to cart with quantity tracking
        public void addItem(String productID , int qty) {
            Product product = productCatalog.get(productID);
            if (product == null) {
                System.out.println(">>> Error: Product not found.");
                return;
            }

            int currentQty = cartItems.getOrDefault(productID, 0);
            if (product.productStock < currentQty + 1) {
                System.out.println(">>> Error: Not enough stock for " + product.productName + "(Available: " + product.productStock + ")");
                return;
            }

            cartItems.put(productID, currentQty + qty);
            System.out.println(">>> "+ qty +" x "+ product.productName + " added to cart.");
        }

        // Display cart with quantities
       public void displayCart() {

        System.out.println("\n\n#====================================================================#");
        System.out.println("|                            Shopping Cart                           |");
        System.out.println("#====================================================================#");
        System.out.println("| ID       | Item                |   Qty  |    Price     |   Total   |");
        System.out.println("#====================================================================#");

        double grandTotal = 0.0;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            Product product = productCatalog.get(entry.getKey());
            if (product != null) {
                // Format product ID (first 8 characters)
                String displayID = product.productID.length() > 8 ? 
                    product.productID.substring(0, 5) + "..." : product.productID;

                // Format product name (first 18 characters)
                String displayName = product.productName.length() > 18 ? 
                    product.productName.substring(0, 15) + "..." : product.productName;

                // - for left align, .2 for 2 decimal places
                double itemTotal = product.productPrice * entry.getValue();
                System.out.printf("| %-8s | %-18s   | %5d  | %10.2f  | %9.2f |%n", 
                    displayID, 
                    displayName, 
                    entry.getValue(), 
                    product.productPrice, 
                    itemTotal);

                grandTotal += itemTotal;
            }
        }

        System.out.println("#====================================================================#");
        System.out.printf("| %-48s %15.2f   |%n", "Grand Total:  ", grandTotal);
        System.out.print("#====================================================================#");
    }

    public void saveCart() {
        List<String> otherUsersData = new ArrayList<>();

        // --- STEP 1: Read all lines from Cart.txt and store other users' data ---
        try (BufferedReader reader = new BufferedReader(new FileReader(CARTFILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // If the line does NOT belong to the current user, keep it
                if (!line.startsWith(email + ";")) {
                    otherUsersData.add(line);  // Keep other users' data
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Failed to read existing cart data.");
            return; // Stop execution if reading fails
        }

         // --- STEP 2: Write ALL data back (other users + current user's updated cart) ---
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CARTFILEPATH))) {
            // Write other users' data first
            for (String entry : otherUsersData) {
                writer.write(entry);
                writer.newLine();
            }

            // Write current user's data WITH PRODUCT DETAILS
            for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                String productID = entry.getKey();
                Product product = productCatalog.get(productID);

                if (product != null) {
                     // Format: email;productID;productName;price;quantity
                    String line = String.format("%s;%s;%s;%.2f;%d", email, productID, product.productName, product.productPrice, entry.getValue());
                    writer.write(line);
                    writer.newLine();
                } else {
                    System.out.println(">>> Error: Product not found in the catalog while saving cart: " + productID);
                    System.out.println("");
                }
            }

            System.out.println(">>> Cart saved successfully.");
        } catch (IOException e) {
            System.out.println(">>> Error: Failed to save cart.");
            System.out.println("");
        }
    }
    // Validate stock before checkout (check if enough stock)
    public boolean validateStock() {
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            Product product = productCatalog.get(entry.getKey());

            if (product == null) {
                System.out.println(">>> Error: Product not found in catalog: " + entry.getKey());
                return false;
            }

            System.out.println("Checking stock for " + product.productName + " (Available: " + product.productStock + ", Needed: " + entry.getValue() + ")");

            if (product.productStock < entry.getValue()) {
                System.out.println(">>> Error: Insufficient stock for " + product.productName);
                return false;
            }
        }
        
        // All products have enough stock
        return true;
    }

    // Delete a product from cart
    public void deleteProduct(String productID) {
        if (!cartItems.containsKey(productID)) {
            System.out.println(">>> Error: Product " + productID + " not found in your cart.");
            return;
        }

        // Remove from in-memory cart
        cartItems.remove(productID);

        // Update file by saving the cart
        saveCart();
        System.out.println(">>> Product " + productID + " successfully removed from cart.");
        System.out.println("");
    }

    // Get valid items from user input
    public Map<String, Integer> getValidCheckoutItems(List<String> selectedIds) {
        Map<String, Integer> selectedItems = new HashMap<>();

        for (String id : selectedIds) {
            id = id.trim().toUpperCase();
            if (cartItems.containsKey(id)) {
                selectedItems.put(id, cartItems.get(id));
            } else {
                System.out.println(">>> Warning: " + id + " not found in cart - skipping");
            }
        }
        return selectedItems;
    }

    // Show summary and return total amount
    public double generateCheckoutSummary(Map<String, Integer> selectedItems) {
        System.out.println("\n#" + "=".repeat(10) + " Checkout Summary " + "=".repeat(10) + "#");
        double total = 0.0;

        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            Product p = productCatalog.get(entry.getKey());
            if (p != null) {
                double itemTotal = p.productPrice * entry.getValue();
                System.out.printf("%-20s x%-3d = RM %.2f%n",
                        p.productName, entry.getValue(), itemTotal);
                total += itemTotal;
            }
        }

        System.out.println("-".repeat(40));
        System.out.printf("TOTAL: RM %.2f%n", total);
        System.out.print("#" + "=".repeat(38) + "#");
        return total;
    }

    public void processCheckout(Map<String, Integer> selectedItems, double amountPaid,
        Payment.PaymentMethod method, String orderId, String dateTime) {
        // Remove from cart and reduce stock
        for (String id : selectedItems.keySet()) {
            cartItems.remove(id);
            Product p = productCatalog.get(id);
            if (p != null) {
                p.productStock -= selectedItems.get(id);
            }
        }

        saveCart();
    }

    public static void deductStock(Map<String, Integer> selectedItems) {
        String filePath = "src/textFile/Product.txt";
        List<String> updatedLines = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (String line : lines) {
                String[] parts = line.split(";");
                if (parts.length < 4) {
                    updatedLines.add(line); // Skip malformed lines
                    continue;
                }

                String productId = parts[0].trim();
                int stock = Integer.parseInt(parts[3].trim());

                if (selectedItems.containsKey(productId)) {
                    int quantityToDeduct = selectedItems.get(productId);

                    if (stock >= quantityToDeduct) {
                        stock -= quantityToDeduct;
                        System.out.println(">>> Stock deducted for " + productId + ": " + quantityToDeduct + " units.");
                    } else {
                        System.out.println(">>> Insufficient stock for product: " + productId + ", skipping.");
                    }

                    // Update the stock in the parts array
                    parts[3] = String.valueOf(stock);
                }

                // Reconstruct the updated line
                updatedLines.add(String.join(";", parts));
            }

            // Write updated content back to file
            Files.write(Paths.get(filePath), updatedLines);

        } catch (IOException e) {
            System.out.println(">>> Error accessing product.txt: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(">>> Error parsing stock number: " + e.getMessage());
        }
    }

}