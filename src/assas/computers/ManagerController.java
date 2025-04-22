/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.util.Scanner;

/**
 *
 * @author Acer
 */

// A class that is use to store operations that a manager can perform after login
public class ManagerController {
    
    private InventoryManagement inventoryManagement;
    private Scanner scanner;

    
    public ManagerController() {
        inventoryManagement = new InventoryManagement();
        scanner = new Scanner(System.in);
    }

    // Display menu when manager login to their account
    public void managerMenu(Manager manager) {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n#" + "=".repeat(46) + " Manager Menu " + "=".repeat(46) + "#");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Search Product");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline
            
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    searchProduct();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting Manager Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }

    // Manager add product to Product.txt 
    public void addProduct() {
        System.out.println("\n#" + "=".repeat(46) + " Add Product " + "=".repeat(46) + "#");

        String productId;
        do {
            System.out.print("\nEnter Product ID: ");
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
                System.out.println("Please enter a valid number for price.");
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
                    System.out.println("Product stock must be non-negative.");
                    System.out.println("");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number for stock.");
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
                System.out.println(">>> Color cannot be empty.");
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
                System.out.print("Enter Keyboard Type: ");
                String kbType = scanner.nextLine();
                System.out.print("Enter Keyboard Switches: ");
                String kbSwitches = scanner.nextLine();
                System.out.print("Enter Keyboard Size: ");
                String kbSize = scanner.nextLine();
                product = new Keyboard(productId, productName, productPrice, productStock,
                        productDescription, productColor, productType, kbType, kbSwitches, kbSize);
                break;

            case MONITOR:
                System.out.print("Enter Monitor Resolution: ");
                String resolution = scanner.nextLine();
                System.out.print("Enter Monitor Panel Size: ");
                String panelSize = scanner.nextLine();
                System.out.print("Enter Monitor Refresh Rate: ");
                String refreshRate = scanner.nextLine();
                product = new Monitor(productId, productName, productPrice, productStock,
                        productDescription, productColor, productType, resolution, panelSize, refreshRate);
                break;

            case LAPTOP:
                System.out.print("Enter Laptop RAM: ");
                String ram = scanner.nextLine();
                System.out.print("Enter Laptop ROM: ");
                String rom = scanner.nextLine();
                System.out.print("Enter Laptop CPU: ");
                String cpu = scanner.nextLine();
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

        System.out.print("Enter New Product Name: ");
        String newName = scanner.nextLine();

        System.out.print("Enter New Product Price: ");
        double newPrice = ProductValidation.getValidatedDouble(scanner);

        System.out.print("Enter New Product Stock: ");
        int newStock = ProductValidation.getValidatedInt(scanner);

        System.out.print("Enter New Product Description: ");
        String newDescription = scanner.nextLine();
        if (!ProductValidation.isValidDescription(newDescription)) {
            System.out.println(">>> Invalid product description.");
            return;
        }

        System.out.print("Enter New Product Color: ");
        String newColor = scanner.nextLine();
        if (!ProductValidation.isValidColor(newColor)) {
            System.out.println(">>> Invalid product color.");
            return;
        }

        String[] extraDetails = null;
        if (existingProduct instanceof Keyboard) {
            System.out.print("Enter New Keyboard Type: ");
            String kbType = scanner.nextLine();
            System.out.print("Enter New Keyboard Switches: ");
            String kbSwitches = scanner.nextLine();
            System.out.print("Enter New Keyboard Size: ");
            String kbSize = scanner.nextLine();
            extraDetails = new String[] { kbType, kbSwitches, kbSize };

        } else if (existingProduct instanceof Monitor) {
            System.out.print("Enter New Monitor Resolution: ");
            String resolution = scanner.nextLine();
            System.out.print("Enter New Monitor Panel Size: ");
            String panelSize = scanner.nextLine();
            System.out.print("Enter New Monitor Refresh Rate: ");
            String refreshRate = scanner.nextLine();
            extraDetails = new String[] { resolution, panelSize, refreshRate };

        } else if (existingProduct instanceof Laptop) {
            System.out.print("Enter New Laptop RAM: ");
            String ram = scanner.nextLine();
            System.out.print("Enter New Laptop ROM: ");
            String rom = scanner.nextLine();
            System.out.print("Enter New Laptop CPU: ");
            String cpu = scanner.nextLine();
            extraDetails = new String[] { ram, rom, cpu };
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


}
