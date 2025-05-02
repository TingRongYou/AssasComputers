/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Acer
 */

// A class that is use to store operations that a manager can perform after login
public class ManagerController {
    
    private InventoryManagement inventoryManagement;
    private Scanner scanner;

    /**
    * constructor
    */
    public ManagerController() {
        inventoryManagement = new InventoryManagement();
        scanner = new Scanner(System.in);
    }

    // Display menu when manager login to their account
    public void managerMenu(Manager manager) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n\n#" + "=".repeat(46) + " Manager Menu " + "=".repeat(46) + "#");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Search Product");
            System.out.println("5. View All Products");
            System.out.println("6. View All Order");
            System.out.println("7. Update Order Status");
            System.out.println("8. Logout");
            System.out.println("#" + "=".repeat(106) + "#");
            System.out.print("Please enter your option (1-8): ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
            } else {
                System.out.println(">>> Invalid input! Please enter a number between 1 and 8.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> updateProduct();
                case 4 -> searchProduct();
                case 5 -> viewAllProducts();
                case 6 -> OrderFileHandler.viewAllOrders();
                case 7 -> OrderFileHandler.updateOrderStatus();
                case 8 -> {
                    exit = true;
                    System.out.println(">>> Log out successfully\n\n");
                    AssasComputers.main(new String[0]);
                }
                default -> System.out.println(">>> Invalid choice! Please enter a number between 1 and 8.");
            }
        }
    }

    // Manager add product to Product.txt 
   public void addProduct() {
        System.out.println("\n\n#" + "=".repeat(46) + " Add Product " + "=".repeat(46) + "#");

        String productId;
        do {
            System.out.print("Enter Product ID: ");
            productId = scanner.nextLine();
            if (!ProductValidation.isValidProductId(productId)) {
                System.out.println(">>> Invalid product ID. Only alphanumeric, dash or underscore allowed.");
                System.out.println("");
            }
        } while (!ProductValidation.isValidProductId(productId));

        String productName;
        do {
            System.out.print("Enter Product Name: ");
            productName = scanner.nextLine();
            if (!ProductValidation.isValidProductName(productName)) {
                System.out.println(">>> Product name cannot be empty.");
                System.out.println("");
            }
        } while (!ProductValidation.isValidProductName(productName));

        double productPrice;
        while (true) {
            System.out.print("Enter Product Price: ");
            try {
                productPrice = Double.parseDouble(scanner.nextLine());
                if (ProductValidation.isValidPrice(productPrice)) break;
                else {
                    System.out.println(">>> Product price must be non-negative.");
                    System.out.println("");
                }
            } catch (NumberFormatException e) {
                System.out.println(">>> Please enter a valid number for price.");
                System.out.println("");
            }
        }

        int productStock;
        while (true) {
            System.out.print("Enter Product Stock: ");
            try {
                productStock = Integer.parseInt(scanner.nextLine());
                if (ProductValidation.isValidStock(productStock)) break;
                else {
                    System.out.println(">>> Product stock must be non-negative.");
                    System.out.println("");
                }
            } catch (NumberFormatException e) {
                System.out.println(">>> Please enter a valid whole number for stock.");
                System.out.println("");
            }
        }

        String productDescription;
        do {
            System.out.print("Enter Product Description: ");
            productDescription = scanner.nextLine();
            if (!ProductValidation.isValidDescription(productDescription)) {
                System.out.println(">>> Description cannot be empty.");
                System.out.println("");
            }
        } while (!ProductValidation.isValidDescription(productDescription));

        String productColor;
        do {
            System.out.print("Enter Product Color: ");
            productColor = scanner.nextLine();
            if (!ProductValidation.isValidColor(productColor)) {
                System.out.println(">>> Color must contain only alphabetic characters and cannot be empty.");
                System.out.println("");
            }
        } while (!ProductValidation.isValidColor(productColor));

        String typeInput;
        Product.ProductType productType = null;
        while (true) {
            System.out.print("Enter Product Type (Keyboard/Monitor/Laptop): ");
            typeInput = scanner.nextLine().toUpperCase();
            if (ProductValidation.isValidEnumValue(typeInput, Product.ProductType.class)) {
                productType = Product.ProductType.valueOf(typeInput);
                break;
            } else {
                System.out.println(">>> Invalid product type. Please enter: Keyboard, Monitor, or Laptop.");
                System.out.println("");
            }
        }

        Product product = null;

        switch (productType) {
            case KEYBOARD:
                String kbType;
                do {
                    System.out.print("Enter Keyboard Type: ");
                    kbType = scanner.nextLine();
                    if (kbType.isEmpty()) {
                        System.out.println(">>> Keyboard type cannot be empty.");
                        System.out.println("");
                    }
                } while (kbType.isEmpty());
                
                String kbSwitches;
                do {
                    System.out.print("Enter Keyboard Switches: ");
                    kbSwitches = scanner.nextLine();
                    if (kbSwitches.isEmpty()) {
                        System.out.println(">>> Keyboard switches cannot be empty.");
                        System.out.println("");
                    }
                } while (kbSwitches.isEmpty());
                
                String kbSize;
                do {
                    System.out.print("Enter Keyboard Size: ");
                    kbSize = scanner.nextLine();
                    if (kbSize.isEmpty()) {
                        System.out.println(">>> Keyboard size cannot be empty.");
                        System.out.println("");
                    }
                } while (kbSize.isEmpty());
                
                product = new Keyboard(productId, productName, productPrice, productStock,
                        productDescription, productColor, productType, kbType, kbSwitches, kbSize);
                break;

            case MONITOR:
                String resolution;
                do {
                    System.out.print("Enter Monitor Resolution: ");
                    resolution = scanner.nextLine();
                    if (resolution.isEmpty()) {
                        System.out.println(">>> Monitor resolution cannot be empty.");
                        System.out.println("");
                    }
                } while (resolution.isEmpty());
                
                String panelSize;
                do {
                    System.out.print("Enter Monitor Panel Size: ");
                    panelSize = scanner.nextLine();
                    if (panelSize.isEmpty()) {
                        System.out.println(">>> Monitor panel size cannot be empty.");
                        System.out.println("");
                    }
                } while (panelSize.isEmpty());
                
                String refreshRate;
                do {
                    System.out.print("Enter Monitor Refresh Rate: ");
                    refreshRate = scanner.nextLine();
                    if (refreshRate.isEmpty()) {
                        System.out.println(">>> Monitor refresh rate cannot be empty.");
                        System.out.println("");
                    }
                } while (refreshRate.isEmpty());
                
                product = new Monitor(productId, productName, productPrice, productStock,
                        productDescription, productColor, productType, resolution, panelSize, refreshRate);
                break;

            case LAPTOP:
                String ram;
                do {
                    System.out.print("Enter Laptop RAM: ");
                    ram = scanner.nextLine();
                    if (ram.isEmpty()) {
                        System.out.println(">>> Laptop RAM cannot be empty.");
                        System.out.println("");
                    }
                } while (ram.isEmpty());
                
                String rom;
                do {
                    System.out.print("Enter Laptop ROM: ");
                    rom = scanner.nextLine();
                    if (rom.isEmpty()) {
                        System.out.println(">>> Laptop ROM cannot be empty.");
                        System.out.println("");
                    }
                } while (rom.isEmpty());
                
                String cpu;
                do {
                    System.out.print("Enter Laptop CPU: ");
                    cpu = scanner.nextLine();
                    if (cpu.isEmpty()) {
                        System.out.println(">>> Laptop CPU cannot be empty.");
                        System.out.println("");
                    }
                } while (cpu.isEmpty());
                
                product = new Laptop(productId, productName, productPrice, productStock,
                        productDescription, productColor, productType, ram, rom, cpu);
                break;
        }

        if (product != null) {
            inventoryManagement.addProduct(new Inventory(), product, productType.toString());
        }
    }

    // Manager remove product from Product.txt 
    private void removeProduct() {
        System.out.print("\nEnter Product ID to remove: ");
        String productId = scanner.nextLine();

        Product product = inventoryManagement.findProductById(productId);
        if (product == null) {
            System.out.println(">>> Product not found!");
            return;
        }

        boolean removed = inventoryManagement.removeProductById(productId);
        if (removed) {
            System.out.println(">>> Product removed successfully!");
        } else {
            System.out.println(">>> Product removal failed!");
        }
    }

    // Manager update product in Product.txt 
    private void updateProduct() {
        System.out.print("\nEnter Product ID to update: ");
        String productId = scanner.nextLine();

        Product existingProduct = inventoryManagement.findProductById(productId);
        if (existingProduct == null) {
            System.out.println(">>> Product not found!");
            return;
        }

        // Name
        System.out.print("Enter New Product Name (press Enter to keep \"" + existingProduct.getProductName() + "\"): ");
        String newName = scanner.nextLine();
        if (newName.isEmpty()) newName = existingProduct.getProductName();

        // Price
        System.out.print("Enter New Product Price (press Enter to keep " + existingProduct.getProductPrice() + "): ");
        String priceInput = scanner.nextLine();
        double newPrice = existingProduct.getProductPrice();
        if (!priceInput.isEmpty()) {
            try {
                newPrice = Double.parseDouble(priceInput);
                if (newPrice < 0) {
                    System.out.println(">>> Price cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(">>> Invalid price input. Please enter a valid number.");
                return;
            }
        }

        // Stock
        System.out.print("Enter New Product Stock (press Enter to keep " + existingProduct.getProductStock() + "): ");
        String stockInput = scanner.nextLine();
        int newStock = existingProduct.getProductStock();
        if (!stockInput.isEmpty()) {
            try {
                newStock = Integer.parseInt(stockInput);
                if (newStock < 0) {
                    System.out.println(">>> Stock cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println(">>> Invalid stock input. Please enter a whole number.");
                return;
            }
        }


        // Description
        System.out.print("Enter New Product Description (press Enter to keep current): ");
        String newDescription = scanner.nextLine();
        if (newDescription.isEmpty()) {
            newDescription = existingProduct.getProductDescription();
        } else if (!ProductValidation.isValidDescription(newDescription)) {
            System.out.println(">>> Invalid product description.");
            return;
        }

        // Color
        System.out.print("Enter New Product Color (press Enter to keep current): ");
        String newColor = scanner.nextLine();
        if (newColor.isEmpty()) {
            newColor = existingProduct.getProductColor();
        } else if (!ProductValidation.isValidColor(newColor)) {
            System.out.println(">>> Invalid product color.");
            return;
        }

        // Extra info
        String[] extraDetails = null;
        if (existingProduct instanceof Keyboard) {
            System.out.print("Enter New Keyboard Type (press Enter to keep current): ");
            String kbType = scanner.nextLine();
            System.out.print("Enter New Keyboard Switches (press Enter to keep current): ");
            String kbSwitches = scanner.nextLine();
            System.out.print("Enter New Keyboard Size (press Enter to keep current): ");
            String kbSize = scanner.nextLine();

            extraDetails = new String[] {
                kbType.isEmpty() ? ((Keyboard) existingProduct).getType() : kbType,
                kbSwitches.isEmpty() ? ((Keyboard) existingProduct).getSwitches() : kbSwitches,
                kbSize.isEmpty() ? ((Keyboard) existingProduct).getSize() : kbSize
            };

        } else if (existingProduct instanceof Monitor) {
            System.out.print("Enter New Monitor Resolution (press Enter to keep current): ");
            String resolution = scanner.nextLine();
            System.out.print("Enter New Monitor Panel Size (press Enter to keep current): ");
            String panelSize = scanner.nextLine();
            System.out.print("Enter New Monitor Refresh Rate (press Enter to keep current): ");
            String refreshRate = scanner.nextLine();

            extraDetails = new String[] {
                resolution.isEmpty() ? ((Monitor) existingProduct).getResolution() : resolution,
                panelSize.isEmpty() ? ((Monitor) existingProduct).getPanelSize() : panelSize,
                refreshRate.isEmpty() ? ((Monitor) existingProduct).getRefreshRate() : refreshRate
            };

        } else if (existingProduct instanceof Laptop) {
            System.out.print("Enter New Laptop RAM (press Enter to keep current): ");
            String ram = scanner.nextLine();
            System.out.print("Enter New Laptop ROM (press Enter to keep current): ");
            String rom = scanner.nextLine();
            System.out.print("Enter New Laptop CPU (press Enter to keep current): ");
            String cpu = scanner.nextLine();

            extraDetails = new String[] {
                ram.isEmpty() ? ((Laptop) existingProduct).getRam() : ram,
                rom.isEmpty() ? ((Laptop) existingProduct).getRom() : rom,
                cpu.isEmpty() ? ((Laptop) existingProduct).getCpu() : cpu
            };
        }

        boolean updated = inventoryManagement.updateProductById(
            productId, newName, newPrice, newStock, newDescription, newColor, extraDetails
        );

        if (updated) {
            System.out.println(">>> Product updated successfully!");
        } else {
            System.out.println(">>> Product update failed!");
        }
    }

   // Manger search product by using product id
   private void searchProduct() {
        System.out.print("\nEnter Product ID to search: ");
        String productId = scanner.nextLine();

        String result = inventoryManagement.searchProductById(productId);

        // Only print the result if it's the "not found" message
        if (!result.isEmpty()) {
            System.out.println(result);
        }
    }

   public void viewAllProducts() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Product.getProductPath()));
            String line;
            boolean found = false;

            System.out.println("\n\n#" + "=".repeat(85) + " Product List " + "=".repeat(85) + "#");
            System.out.printf("%-10s | %-22s | %-10s | %-5s | %-12s | %-10s | %-28s | %-40s\n",
                    "Product ID", "Name", "Price", "Stock", "Color", "Type", "Description", "Extra Info");
            System.out.println("-".repeat(186));

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 10) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int stock = Integer.parseInt(parts[3].trim());
                    String description = parts[4].trim();
                    String color = parts[5].trim();
                    Product.ProductType type = Product.ProductType.valueOf(parts[6].trim().toUpperCase());

                    String extraInfo = "-";
                    switch (type) {
                        case LAPTOP:
                            extraInfo = String.format("RAM: %s, Storage: %s, Processor: %s",
                                    parts[7].trim(), parts[8].trim(), parts[9].trim());
                            break;
                        case MONITOR:
                            extraInfo = String.format("Resolution: %s, Size: %s, Refresh Rate: %s",
                                    parts[7].trim(), parts[8].trim(), parts[9].trim());
                            break;
                        case KEYBOARD:
                            extraInfo = String.format("Type: %s, Switches: %s, Size: %s",
                                    parts[7].trim(), parts[8].trim(), parts[9].trim());
                            break;
                    }

                    System.out.printf("%-10s | %-22s | RM%-8.2f | %-5d | %-12s | %-10s | %-28s | %-40s\n",
                            id, name, price, stock, color, type, truncateDescription(description), extraInfo);
                    found = true;
                }
            }

            System.out.println("#" + "=".repeat(184) + "#");

            if (!found) {
                System.out.println(">>> No products available.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Product file not found at " + Product.getProductPath());
        } catch (IOException e) {
            System.out.println("Error reading product data: " + e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }
    }
   
    private String truncateDescription(String desc) {
        return desc.length() > 25 ? desc.substring(0, 22) + "..." : desc;
    }

}
