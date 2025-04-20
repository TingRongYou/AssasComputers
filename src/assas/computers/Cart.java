    package assas.computers;

import assas.computers.Product.ProductType;
    import java.io.*;
    import java.util.*;

    public class Cart {
        static final String filePath = "src/textFile/Cart.txt";
        static final String productFilePath = "src/textFile/Product.txt";
        static public HashMap<String, Product> productCatalog = new HashMap<>();

        private String email; 
        private HashMap<String, Integer> cartItems; // Key: productID, Value: quantity

        // Static block to load products once
        static {
            loadProducts();
        }

        public Cart(String email) {
            this.email = email;
            this.cartItems = new HashMap<>();
            loadCart(); // Load user's existing cart data
        }

        public static class AuthService { 
        private static String currentUserEmail = null;  // Added "String" type

        public static String getCurrentUserEmail() {
            return currentUserEmail;
        }  

        public static void setCurrentUserEmail(String email) {
            currentUserEmail = email;
        } 

        public static void clearCurrentUser() {
            currentUserEmail = null;
        }  
    }


        // Load products into catalog
        public static void loadProducts() {
        productCatalog.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(productFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 10) {
                    String id = data[0];
                    String name = data[1];
                    double price = Double.parseDouble(data[2]);
                    int stock = Integer.parseInt(data[3]);
                    String desc = data[4];
                    String color = data[5];
                    String typeString = data[6];
                    ProductType productType = ProductType.valueOf(typeString.trim().toUpperCase());

                    // Common fields (RAM, ROM, CPU, etc.)
                    String spec1 = data[7];
                    String spec2 = data[8];
                    String spec3 = data[9];

                    Product product = null;

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


                    if (product != null) {
                        productCatalog.put(id, product);
                    }
                } else {
                    System.out.println("Incomplete product data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Number Format Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Product Type: " + e.getMessage());
        }

}



        // Load user's cart from file
        public void loadCart() {
        cartItems.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                // Check for new format (5 fields)
                if (data.length >= 5 && data[0].equals(email)) {
                    String productID = data[1];
                    int quantity = Integer.parseInt(data[4]); // Quantity is now at index 4
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
        if (cartItems.isEmpty()) {
            System.out.println(">>> Your cart is empty.");
            return;
        }

        System.out.println("\n#====================================================================#");
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

                double itemTotal = product.productPrice * entry.getValue();
                System.out.printf("| %-8s | %-18s   | %5d  | %10.2f  | %8.2f |%n", 
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
        System.out.println("#====================================================================#");
    }

        // Save cart to file
        public void saveCart() {
        List<String> otherUsersData = new ArrayList<>();

        // Read all non-current-user entries
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(email + ";")) {
                    otherUsersData.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Failed to save cart.");
        }

        // Write back all data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write other users' data
            for (String entry : otherUsersData) {
                writer.write(entry);
                writer.newLine();
            }

            // Write current user's data WITH PRODUCT DETAILS
            for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                String productID = entry.getKey();
                Product product = productCatalog.get(productID);
                if (product != null) {
                    String line = String.format("%s;%s;%s;%.2f;%d",email, productID, product.productName, product.productPrice,  entry.getValue());
                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println(">>> Cart saved successfully.");
        } catch (IOException e) {
            System.out.println(">>> Error: Failed to save cart.");
        }
    }

        // Validate stock before checkout
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
    }

    public void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println(">>> Your cart is empty. Nothing to checkout!");
            return;
        }

        // Display cart without IDs
        System.out.println("\n#=== Items Available for Checkout ===#");
        displayCart();  // Use your existing display format

        // Get product IDs to checkout
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter items to checkout (comma-separated, e.g. L0001,P1234): ");
        String[] selectedIds = scanner.nextLine().split(",");

        // Validate selections
        HashMap<String, Integer> selectedItems = new HashMap<>();
        for (String id : selectedIds) {
            id = id.trim().toUpperCase();
            if (cartItems.containsKey(id)) {
                selectedItems.put(id, cartItems.get(id));
            } else {
                System.out.println(">>> Warning: " + id + " not found in cart - skipping");
            }
        }

        if (selectedItems.isEmpty()) {
            System.out.println(">>> No valid items selected for checkout!");
            return;
        }

        // Display checkout summary
        System.out.println("\n#=== Checkout Summary ===#");
        double total = 0.0;
        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            Product p = productCatalog.get(entry.getKey());
            if (p != null) {
                double itemTotal = p.productPrice * entry.getValue();
                System.out.printf("%-20s x%-3d = $%.2f%n", 
                    p.productName, entry.getValue(), itemTotal);
                total += itemTotal;
            }
        }
        System.out.println("---------------------------");
        System.out.printf("TOTAL: $%.2f%n", total);

        // Confirm checkout
        System.out.print("\nConfirm checkout? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            // Process checkout
            for (String id : selectedItems.keySet()) {
                cartItems.remove(id);  // Remove checked out items
                Product p = productCatalog.get(id);
                if (p != null) {
                    p.productStock -= selectedItems.get(id);  // Update stock
                }
            }
            saveCart();
            System.out.println(">>> Checkout completed successfully!");
        } else {
            System.out.println(">>> Checkout cancelled!");
        }
    }
        // Getters
        public String getEmail() { return email; }
        public HashMap<String, Integer> getCartItems() { return new HashMap<>(cartItems); }
    }