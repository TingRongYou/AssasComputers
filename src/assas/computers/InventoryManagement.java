/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Acer
 */

// A class that is use to manage inventory, to assist operation performed by manager
public class InventoryManagement {
    
    private final String FILE_PATH = "src/textFile/Product.txt";

    // Add product to Product.txt
    public void addProduct(Inventory inventory, Product product, String productType) {
        inventory.addProduct(product);
        saveProductToFile(product, productType);
        System.out.println(">>> Product Added and Saved Successfully!");
    }

    // Save a single product to file
    private void saveProductToFile(Product product, String productType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            StringBuilder sb = new StringBuilder();

            sb.append(product.getProductID()).append(";")
              .append(product.getProductName()).append(";")
              .append(product.getProductPrice()).append(";")
              .append(product.getProductStock()).append(";")
              .append(product.getProductDescription()).append(";")
              .append(product.getProductColor()).append(";")
              .append(productType);

            if (product instanceof Keyboard kb) {
                sb.append(";").append(kb.getType())
                  .append(";").append(kb.getSwitches())
                  .append(";").append(kb.getSize());
            } else if (product instanceof Monitor mon) {
                sb.append(";").append(mon.getResolution())
                  .append(";").append(mon.getPanelSize())
                  .append(";").append(mon.getRefreshRate());
            } else if (product instanceof Laptop lap) {
                sb.append(";").append(lap.getRam())
                  .append(";").append(lap.getRom())
                  .append(";").append(lap.getCpu());
            }

            // Write the built line with a newline character
            writer.write(sb.toString());
            writer.newLine(); // ✅ ensures a new line after every product

        } catch (IOException e) {
            System.out.println(">>> Error: Error saving product! " + e.getMessage());
        }
    }

    // Use when modify, deleting or re-saving everything (updateProduct function for manager)
    private void saveAllProductsToFile(List<String[]> allProducts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] productDetails : allProducts) {
                writer.write(String.join(";", productDetails));
                writer.newLine(); // Important for new lines between products
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Failed to save updated product list. " + e.getMessage());
        }
    }

    // Use to get all of the products in Product.txt
    public List<String[]> getAllProducts() {
        List<String[]> allProducts = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return allProducts;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productDetails = line.split(";");
                if (productDetails.length >= 7) {
                    allProducts.add(productDetails);
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error reading file: " + e.getMessage());
        }

        return allProducts;
    }

    // Remove product based on id
    public boolean removeProductById(String productId) {
        List<String[]> allProducts = getAllProducts();
        boolean removed = allProducts.removeIf(p -> p[0].equalsIgnoreCase(productId));

        if (removed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String[] product : allProducts) {
                    writer.write(String.join(";", product));
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println(">>> Error writing file: " + e.getMessage());
                return false;
            }
        }

        return removed;
    }
    
    // Update product information based on id
    public boolean updateProductById(String productId, String newName, double newPrice, int newStock,
       String newDescription, String newColor, String[] extraDetails) {
       List<String[]> allProducts = getAllProducts();
       boolean updated = false;

       for (String[] productDetails : allProducts) {
           if (productDetails[0].equalsIgnoreCase(productId)) {
               productDetails[1] = newName;
               productDetails[2] = String.valueOf(newPrice);
               productDetails[3] = String.valueOf(newStock);
               productDetails[4] = newDescription;
               productDetails[5] = newColor;

               String productType = productDetails[6].toUpperCase(); // KEYBOARD, MONITOR, LAPTOP

               // Handle extra details
               if (extraDetails != null) {
                   switch (productType) {
                       case "KEYBOARD":
                           productDetails[7] = extraDetails[0]; // type
                           productDetails[8] = extraDetails[1]; // switches
                           productDetails[9] = extraDetails[2]; // size
                           break;
                       case "MONITOR":
                           productDetails[7] = extraDetails[0]; // resolution
                           productDetails[8] = extraDetails[1]; // panel size
                           productDetails[9] = extraDetails[2]; // refresh rate
                           break;
                       case "LAPTOP":
                           productDetails[7] = extraDetails[0]; // RAM
                           productDetails[8] = extraDetails[1]; // ROM
                           productDetails[9] = extraDetails[2]; // CPU
                           break;
                   }
               }

               updated = true;
               break;
           }
       }

       if (updated) {
           saveAllProductsToFile(allProducts);
       }

       return updated;
   }

    // Find product in Product.txt by product id
    public Product findProductById(String productId) {
        List<String[]> allProducts = getAllProducts();

        for (String[] productDetails : allProducts) {
            if (productDetails[0].equalsIgnoreCase(productId)) {
                // Common fields
                String id = productDetails[0];
                String name = productDetails[1];
                double price = Double.parseDouble(productDetails[2]);
                int stock = Integer.parseInt(productDetails[3]);
                String description = productDetails[4];
                String color = productDetails[5];
                String type = productDetails[6];

                Product.ProductType productType = Product.ProductType.valueOf(type.toUpperCase());

                // Convert to correct product subclass
                switch (productType) {
                    case KEYBOARD:
                        return new Keyboard(id, name, price, stock, description, color, productType,
                            productDetails[7], productDetails[8], productDetails[9]);
                    case MONITOR:
                        return new Monitor(id, name, price, stock, description, color, productType,
                            productDetails[7], productDetails[8], productDetails[9]);
                    case LAPTOP:
                        return new Laptop(id, name, price, stock, description, color, productType,
                            productDetails[7], productDetails[8], productDetails[9]);
                }
            }
        }
        return null; // Not found
    }
    
    // Display product searched through product id
    public String searchProductById(String productId) {
        List<String[]> allProducts = getAllProducts();

        for (String[] productDetails : allProducts) {
            if (productDetails[0].equalsIgnoreCase(productId)) {
                String productType = productDetails[6];

                System.out.println("\n#" + "=".repeat(46) + " Product Details " + "=".repeat(46) + "#");

                // Common fields
                System.out.printf("%-12s | %-20s | %-8s | %-6s | %-25s | %-10s | %-12s\n",
                        "Product ID", "Name", "Price", "Stock", "Description", "Color", "Type");
                System.out.println("-".repeat(110));

                System.out.printf("%-12s | %-20s | %-8s | %-6s | %-25s | %-10s | %-12s\n",
                        productDetails[0],  // ID
                        productDetails[1],  // Name
                        productDetails[2],  // Price
                        productDetails[3],  // Stock
                        productDetails[4],  // Description
                        productDetails[5],  // Color
                        productType);       // Type

                System.out.println("-".repeat(110));

                // Exclusive fields
                switch (productType.toUpperCase()) {
                    case "KEYBOARD":
                        System.out.printf("Keyboard Type     : %s\n", productDetails[7]);
                        System.out.printf("Switches          : %s\n", productDetails[8]);
                        System.out.printf("Size              : %s\n", productDetails[9]);
                        break;
                    case "MONITOR":
                        System.out.printf("Resolution        : %s\n", productDetails[7]);
                        System.out.printf("Panel Size        : %s\n", productDetails[8]);
                        System.out.printf("Refresh Rate      : %s\n", productDetails[9]);
                        break;
                    case "LAPTOP":
                        System.out.printf("RAM               : %s\n", productDetails[7]);
                        System.out.printf("ROM               : %s\n", productDetails[8]);
                        System.out.printf("CPU               : %s\n", productDetails[9]);
                        break;
                    default:
                        System.out.println("Unknown product type.");
                }

                System.out.println("#" + "=".repeat(109) + "#\n");
                return ""; // Found, already printed
            }
        }

        return ">>> Product with ID " + productId + " not found!";
    }


}
