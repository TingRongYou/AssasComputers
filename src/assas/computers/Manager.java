package assas.computers;

import assas.computers.Product.ProductType;
import static assas.computers.Product.ProductType.KEYBOARD;
import static assas.computers.Product.ProductType.LAPTOP;
import static assas.computers.Product.ProductType.MONITOR;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Manager extends Staff {
    private static final String filePath = "src/Product.txt";
    private Scanner scanner = new Scanner(System.in);

    public Manager(String staffID, String email) {
        super(staffID, email, Role.MANAGER);
    }

    public void addProduct(Inventory inventory) {
        System.out.println("Choose Product Type to Add:");
        System.out.println("1. Keyboard\n2. Monitor\n3. Laptop");
        System.out.print("Enter choice (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline

        System.out.print("Enter Product ID: ");
        String productID = scanner.nextLine();

        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter Price: ");
        double productPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume leftover newline

        System.out.print("Enter Stock Quantity: ");
        int productStock = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline

        System.out.print("Enter Product Description: ");
        String productDescription = scanner.nextLine();

        System.out.print("Enter Product Color: ");
        String productColor = scanner.nextLine();

        Product product = null;
        String productType = "";

        switch (choice) {
            case 1:
                productType = "Keyboard";
                System.out.print("Enter Keyboard Type: ");
                String type = scanner.nextLine();
                System.out.print("Enter Switch Type: ");
                String switches = scanner.nextLine();
                System.out.print("Enter Keyboard Size: ");
                String size = scanner.nextLine();
                product = new Keyboard(productID, productName, productPrice, productStock, productDescription, productColor, KEYBOARD, type, switches, size);
                break;

            case 2:
                productType = "Monitor";
                System.out.print("Enter Monitor Resolution: ");
                String resolution = scanner.nextLine();
                System.out.print("Enter Panel Size: ");
                String panelSize = scanner.nextLine();
                System.out.print("Enter Refresh Rate: ");
                String refreshRate = scanner.nextLine();
                product = new Monitor(productID, productName, productPrice, productStock, productDescription, productColor, MONITOR, resolution, panelSize, refreshRate);
                break;

            case 3:
                productType = "Laptop";
                System.out.print("Enter Laptop RAM (GB): ");
                String ram = scanner.nextLine();
                System.out.print("Enter Laptop ROM (GB): ");
                String rom = scanner.nextLine();
                System.out.print("Enter Processor Model: ");
                String cpu = scanner.nextLine();
                product = new Laptop(productID, productName, productPrice, productStock, productDescription, productColor, LAPTOP, ram, rom, cpu);
                break;

            default:
                System.out.println(">>> Error: Invalid choice! Please select 1, 2, or 3.");
                return;
        }

        inventory.addProduct(product);
        saveProductToFile(product, productType);
        System.out.println(">>> Product Added and Saved Successfully!");
    }

    private void saveProductToFile(Product product, String productType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(product.getProductID() + ";" + product.getProductName() + ";" + product.getProductPrice() + ";" +
                    product.getProductStock() + ";" + product.getProductDescription() + ";" + product.getProductColor() + ";" +
                    productType);

            if (product instanceof Keyboard) {
                Keyboard kb = (Keyboard) product;
                writer.write(";" + kb.getType() + ";" + kb.getSwitches() + ";" + kb.getSize());
            } else if (product instanceof Monitor) {
                Monitor mon = (Monitor) product;
                writer.write(";" + mon.getResolution() + ";" + mon.getPanelSize() + ";" + mon.getRefreshRate());
            } else if (product instanceof Laptop) {
                Laptop lap = (Laptop) product;
                writer.write(";" + lap.getRam() + ";" + lap.getRom() + ";" + lap.getCpu());
            }

            writer.newLine(); // Ensure only one newline at the end
        } catch (IOException e) {
            System.out.println(">>> Error: Error saving product! " + e.getMessage());
        }
    }

   public void removeProduct(Inventory inventory) {
    File file = new File(filePath);
    if (!file.exists()) {
        System.out.println(">>> Error: Product file does not exist!");
        return;
    }

    ArrayList<String[]> keyboards = new ArrayList<>();
    ArrayList<String[]> monitors = new ArrayList<>();
    ArrayList<String[]> laptops = new ArrayList<>();
    ArrayList<String[]> allProducts = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] productDetails = line.split(";");
            if (productDetails.length < 7) continue; // Ensure valid data

            allProducts.add(productDetails);
            switch (productDetails[6].toLowerCase()) {
                case "keyboard": keyboards.add(productDetails); break;
                case "monitor": monitors.add(productDetails); break;
                case "laptop": laptops.add(productDetails); break;
            }
        }
    } catch (IOException e) {
        System.out.println(">>> Error: Cannot read the file!");
        return;
    }

    if (allProducts.isEmpty()) {
        System.out.println(">>> No products available to remove.");
        return;
    }

    // Ask user for product type
    System.out.print("Enter product type to remove (L for Laptop, M for Monitor, K for Keyboard, E to cancel): ");
    String typeChoice = scanner.next().toUpperCase();
    ArrayList<String[]> selectedList = null;
    String typeName = "";
    String[] headers = new String[0];

    switch (typeChoice) {
        case "K": selectedList = keyboards; typeName = "Keyboards";
            headers = new String[]{"ID", "Name", "Price", "Stock", "Desc", "Color", "Type", "Switch", "Size"}; break;
        case "M": selectedList = monitors; typeName = "Monitors";
            headers = new String[]{"ID", "Name", "Price", "Stock", "Desc", "Color", "Type", "Resolution", "Panel Size", "Refresh Rate"}; break;
        case "L": selectedList = laptops; typeName = "Laptops";
            headers = new String[]{"ID", "Name", "Price", "Stock", "Desc", "Color", "Type", "RAM", "ROM", "CPU"}; break;
        case "E":
            System.out.println(">>> Product removal canceled.");
            return;
        default:
            System.out.println(">>> Invalid choice!");
            return;
    }

    if (selectedList.isEmpty()) {
        System.out.println(">>> No products available in this category.");
        return;
    }

    // Display selected category
    printTable(typeName, selectedList, headers);

    // Ask for Product ID
    System.out.print("\nEnter the Product ID to remove (E to cancel): ");
    String productIdToRemove = scanner.next().toUpperCase();
    if (productIdToRemove.equals("E")) {
        System.out.println(">>> Product removal canceled.");
        return;
    }

    boolean found = false;
    for (int i = 0; i < allProducts.size(); i++) {
        if (allProducts.get(i)[0].equalsIgnoreCase(productIdToRemove)) { // Search for ProductID
            System.out.println(">>> Successfully removed: " + String.join(";", allProducts.remove(i)));
            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println(">>> Error: Product ID not found!");
        return;
    }

    // Write updated product list back to file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        for (String[] product : allProducts) {
            writer.write(String.join(";", product));
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println(">>> Error: Failed to update the file!");
    }
}
   
   // Print Table Method (Fixed)
    public void printTable(String title, ArrayList<String[]> products, String[] headers) {
    if (products.isEmpty()) {
        System.out.println("\nNo " + title + " available.");
        return;
    }

    System.out.println("\n" + "=".repeat(10) + " " + title + " " + "=".repeat(10));

    // Determine column widths
    int[] columnWidths = new int[headers.length + 1]; // Extra column for index numbers
    columnWidths[0] = 5; // "No." column width

    for (int i = 0; i < headers.length; i++) {
        columnWidths[i + 1] = headers[i].length();
    }

    for (String[] product : products) {
        for (int i = 0; i < product.length; i++) {
            if (i + 1 < columnWidths.length) {
                columnWidths[i + 1] = Math.max(columnWidths[i + 1], product[i].length());
            }
        }
    }

    // Print top border
    printSeparatorLine(columnWidths);

    // Print header row
    System.out.print("| ");
    System.out.printf("%-" + columnWidths[0] + "s | ", "No.");
    for (int i = 0; i < headers.length; i++) {
        System.out.printf("%-" + columnWidths[i + 1] + "s | ", headers[i]);
    }
    System.out.println();

    // Print middle border
    printSeparatorLine(columnWidths);

    // Print product rows
    for (int i = 0; i < products.size(); i++) {
        System.out.print("| ");
        System.out.printf("%-" + columnWidths[0] + "d | ", i + 1);
        for (int j = 0; j < headers.length; j++) {
            String value = (j < products.get(i).length) ? products.get(i)[j] : ""; // Handle missing fields
            System.out.printf("%-" + columnWidths[j + 1] + "s | ", value);
        }
        System.out.println();
    }

    // Print bottom border
    printSeparatorLine(columnWidths);
}

// Helper method to print separator lines
private void printSeparatorLine(int[] columnWidths) {
    System.out.print("+");
    for (int width : columnWidths) {
        System.out.print("-".repeat(width + 2) + "+");
    }
    System.out.println();
}


public void editProduct(Inventory inventory) {
    File file = new File(filePath);
    if (!file.exists()) {
        System.out.println(">>> Error: Product file does not exist!");
        return;
    }

    ArrayList<String[]> allProducts = new ArrayList<>();
    String[] selectedProduct = null;
    int productIndex = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            allProducts.add(line.split(";"));
        }
    } catch (IOException e) {
        System.out.println(">>> Error: Cannot read the file!");
        return;
    }

    if (allProducts.isEmpty()) {
        System.out.println("No Product Available to edit!");
        return;
    }

    System.out.print("Enter the Product ID to edit (E to cancel): ");
    String productIdEdit = scanner.next().trim().toUpperCase();
    scanner.nextLine();

    if (productIdEdit.equals("E")) {
        System.out.println("Product Editing Canceled.");
        return;
    }

    boolean found = false;
    for (int i = 0; i < allProducts.size(); i++) {
        if (allProducts.get(i)[0].equalsIgnoreCase(productIdEdit)) {
            selectedProduct = allProducts.get(i);
            productIndex = i;
            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println(">>> Error: Product ID Not Found!!");
        return;
    }

    String selectedType = selectedProduct[6].toUpperCase();
    String[] headers;
    ProductType  type = ProductType.valueOf(selectedType.toUpperCase()); // Convert to enum

    switch (type) {

        case KEYBOARD:
            headers = new String[]{"ID", "Name", "Price", "Stock", "Description", "Color", "Type", "Keyboard Type", "Switch", "Size"};
            break;
        case MONITOR:
            headers = new String[]{"ID", "Name", "Price", "Stock", "Description", "Color", "Type", "Resolution", "Panel Size", "Refresh Rate"};
            break;
        case LAPTOP:
            headers = new String[]{"ID", "Name", "Price", "Stock", "Description", "Color", "Type", "RAM", "ROM", "CPU"};
            break;
        default:
            System.out.println(">>> Error: Invalid Product Type!");
            return;
    }

    // Print the selected product details
    ArrayList<String[]> singleProductList = new ArrayList<>();
    singleProductList.add(selectedProduct);
    printTable("Selected Product", singleProductList, headers);
    System.out.println("");

    System.out.println("Select what you want to edit: ");
    for (int i = 1; i < headers.length; i++) {
        System.out.println(i + ". " + headers[i]);
    }
    System.out.println("");

    System.out.print("Enter choice ((1-" + (headers.length - 1) + "), 0 to cancel): ");
    String choiceInput = scanner.nextLine();

    if (!choiceInput.matches("\\d+")) {
        System.out.println(">>> Error: Invalid input! Please enter a number.");
        return;
    }

    int choice = Integer.parseInt(choiceInput);

    if (choice == 0) {
        System.out.println("Product editing canceled!");
        return;
    }

    if (choice < 1 || choice >= headers.length) {
        System.out.println(">>> Error: Invalid choice entered!!");
        return;
    }

    // Prevent editing of product type
    if (choice == 6) {
        System.out.println(">>> Error: Product type cannot be edited!");
        return;
    }

    String newValue;
    boolean isValid;
    do {
        System.out.print("Enter the new value for " + headers[choice] + ": ");
        newValue = scanner.nextLine().trim();
        isValid = true;

        if (newValue.isEmpty()) {
            System.out.println(">>> Error: Value Cannot Be Empty!");
            isValid = false;
            continue;
        }

        if (newValue.equalsIgnoreCase(selectedProduct[choice])) {
            System.out.println(">>> Error: The new value entered is same as current!");
            isValid = false;
            continue;
        }

        // General validation for Name, Price, Stock, Description, Color
        switch (choice) {
            case 1: // Name
                if (!newValue.matches("[a-zA-Z0-9\\s]{2,}")) {
                    System.out.println(">>> Error: Invalid name format.");
                    isValid = false;
                }
                break;
            case 2: // Price
                try {
                    double price = Double.parseDouble(newValue);
                    if (price < 0) {
                        System.out.println(">>> Error: Price must be non-negative.");
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(">>> Error: Invalid price format.");
                    isValid = false;
                }
                break;
            case 3: // Stock
                if (!newValue.matches("\\d+")) {
                    System.out.println(">>> Error: Stock must be a positive integer.");
                    isValid = false;
                }
                break;
            case 4: // Description
                if (newValue.length() < 5) {
                    System.out.println(">>> Error: Description is too short.");
                    isValid = false;
                }
                break;
            case 5: // Color
                if (!newValue.matches("[a-zA-Z\\s]{3,}")) {
                    System.out.println(">>> Error: Invalid color name.");
                    isValid = false;
                }
                break;
        }

        // Specific validations based on product type
        switch (type) {
            case KEYBOARD:
                Keyboard kb = new Keyboard("", "", 0, 0, "", "", KEYBOARD, "", "", "");
                if (choice == 7) {
                    isValid = kb.keyboardTypeValidate(newValue);
                } 
                else if (choice == 8) {
                    isValid = kb.keyboardSwitchesValidate(newValue);
                } 
                else if (choice == 9) {
                    isValid = kb.keyboardSizeValidate(newValue);
                }
                break;
            case MONITOR:
                Monitor mon = new Monitor("", "", 0, 0, "", "", MONITOR, "", "", "");
                if (choice == 7) {
                    isValid = mon.validateResolution(newValue);
                } 
                else if (choice == 8) {
                    isValid = mon.validatePanelSize(newValue);
                } 
                else if (choice == 9) {
                    isValid = mon.refreshRateValidate(newValue);
                }
                break;
            case LAPTOP:
                Laptop lap = new Laptop("", "", 0, 0, "", "", LAPTOP, "", "", "");
                if (choice == 7) isValid = lap.validateRam(newValue);
                else if (choice == 8) {
                    isValid = lap.validateRom(newValue);
                } 
                else if (choice == 9) {
                    isValid = lap.validateCpu(newValue);
                }
                break;
        }

    } while (!isValid);

    selectedProduct[choice] = newValue;
    
    StringBuffer sb = new StringBuffer();
    for(String[] product : allProducts){
        sb.append(String.join(";", product));
        sb.append("\n");
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
        writer.write(sb.toString());
    } 
    catch (IOException e) {
        System.out.println(">>> Error: Failed to update the file!");
        return;
    }

    System.out.println(">>> Product updated successfully!");
    }
    @Override
    public void postLoginAction() {
        System.out.println("Welcome, Manager. You can manage products.");
    }
}

