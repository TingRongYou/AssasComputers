package assas.computers;

import java.io.*;
import java.util.*;

/**
 * Handles saving and loading cart data from a file.
 */
public class CartHandler {
    
    // File path where cart data is stored
    private static final String CART_FILE_PATH = "src/textFile/Cart.txt";
    
    private Cart cart;
    private ProductCatalog productCatalog;

    /**
     * Constructor that associates a cart with this handler and loads product data.
     */
    public CartHandler(Cart cart) {
        this.cart = cart;
        this.productCatalog = ProductCatalog.getInstance();
    }

    /**
     * Loads the current user's cart items from the cart file.
     * Supports both new format (5 fields) and old format (3 fields).
     */
    public void loadCart() {
        HashMap<String, Integer> cartItems = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                
                // Check for new format (userEmail;productID;productName;price;quantity)
                if (data.length >= 5 && data[0].equals(cart.getEmail())) {
                    String productID = data[1];
                    int quantity = Integer.parseInt(data[4]);
                    cartItems.put(productID, cartItems.getOrDefault(productID, 0) + quantity);
                }
                // Support for legacy format (userEmail;productID;quantity)
                else if (data.length == 3 && data[0].equals(cart.getEmail())) {
                    String productID = data[1];
                    int quantity = Integer.parseInt(data[2]);
                    cartItems.put(productID, cartItems.getOrDefault(productID, 0) + quantity);
                }
            }

            // Set the loaded items into the cart
            cart.setCartItems(cartItems);

        } catch (IOException | NumberFormatException e) {
            System.out.println(">>> Error: Failed to load cart.");
        }
    }

    /**
     * Saves the current user's cart to the file, preserving other user's carts.
     * If the product is not found in the catalog, that entry is skipped.
     */
    public void saveCart() {
        List<String> otherUsersData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(cart.getEmail() + ";")) {
                    otherUsersData.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Failed to read existing cart data.");
            return;
        }

        // Write all data back: first other users' data, then current user's cart
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE_PATH))) {
            for (String entry : otherUsersData) {
                writer.write(entry);
                writer.newLine();
            }

            // Append current user's cart items to the file
            for (Map.Entry<String, Integer> entry : cart.getCartItems().entrySet()) {
                String productID = entry.getKey();
                Product product = productCatalog.getProduct(productID);

                if (product != null) {
                    String line = String.format("%s;%s;%s;%.2f;%d", 
                                                cart.getEmail(), 
                                                productID, 
                                                product.getProductName(), 
                                                product.getProductPrice(), 
                                                entry.getValue());
                    writer.write(line);
                    writer.newLine();
                } else {
                    System.out.println(">>> Error: Product not found in the catalog while saving cart: " + productID);
                    System.out.println("");
                }
            }

            System.out.print(">>> Cart saved successfully.");
        } catch (IOException e) {
            System.out.println(">>> Error: Failed to save cart.");
            System.out.println("");
        }
    }
}