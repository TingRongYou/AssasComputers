/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import static assas.computers.AssasComputers.main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class CustomerController {
    
    public static void customerPage() {
        Scanner scanner = new Scanner(System.in);
        int customerOption = -1;

        do {
            System.out.println("\n\n#" + "=".repeat(25) + " Customer " + "=".repeat(25) + "#");
            System.out.println("1. Customer registration\n2. Customer login\n3. Back");
            System.out.println("#" + "=".repeat(60) + "#");
            System.out.print("Please enter your option (1-3): ");

            if (scanner.hasNextInt()) {
                customerOption = scanner.nextInt();
                scanner.nextLine(); // Consume newline left behind

                switch (customerOption) {
                    case 1:
                        customerRegister();
                        break;
                    case 2:
                        customerLogin();
                        home();
                        break;
                    case 3:
                        System.out.println(">>> Returning to main menu...\n\n");
                        main(new String[0]);
                    default:
                        System.out.println(">>> Please enter a valid option (1-3)!!");
                }
            } else {
                System.out.println(">>> Invalid input. Please enter a number between 1 and 3.");
                scanner.nextLine(); // Clear invalid input
            }

        } while (true); 
    }
    
    public static void customerRegister() {
        Scanner scanner = new Scanner(System.in);
        String username, email, password, phoneNum, deliveryAddress;

        System.out.println("\n\n#" + "=".repeat(27) + " Customer Account Registration " + "=".repeat(28) + "#");

        // Username validation
        do {
            System.out.print("Please enter a username: ");
            username = scanner.nextLine().trim();
            if (!UserAccountValidation.usernameValidate(username)) {
                System.out.println(">>> Error: Username must be 1-20 characters.");
                System.out.println("");
            }
        } while (!UserAccountValidation.usernameValidate(username));

        // Email validation
        do {
            System.out.print("Please enter your email: ");
            email = scanner.nextLine().trim();
            if (!UserAccountValidation.emailValidate(email)) {
                System.out.println(">>> Error: Your email should include '@' and '.com'");
                System.out.println("");
            } else if (CustomerFileHandler.isEmailRegistered(email)) {
                System.out.println(">>> Error: This email is already registered.");
                System.out.println("");
            }
        } while (!UserAccountValidation.emailValidate(email) || CustomerFileHandler.isEmailRegistered(email));

        // Password validation
        do {
            System.out.print("Please enter a password: ");
            password = scanner.nextLine().trim();
            if (!UserAccountValidation.passwordValidate(password)) {
                System.out.println(">>> Error: Password must include uppercase, lowercase, number, special char (!@#%*&$), and be 8-15 chars long.");
                System.out.println("");
            }
        } while (!UserAccountValidation.passwordValidate(password));

        // Phone number validation
        do {
            System.out.print("Please enter your phone number: ");
            phoneNum = scanner.nextLine().trim();
            if (!UserAccountValidation.phoneNumValidate(phoneNum)) {
                System.out.println(">>> Error: Phone number must start with '01' and be 10-11 digits.");
                System.out.println("");
            }
        } while (!UserAccountValidation.phoneNumValidate(phoneNum));

        // Delivery address validation
        do {
            System.out.print("Please enter your delivery address: ");
            deliveryAddress = scanner.nextLine().trim();
            if (!UserAccountValidation.addressCheck(deliveryAddress)) {
                System.out.println(">>> Error: Address must be 1-50 characters.");
                System.out.println("");
            }
        } while (!UserAccountValidation.addressCheck(deliveryAddress));
        
        System.out.println(">>> Please scan the QR code");
        System.out.println("");

        // Set up MFA
        MultiFactorAuthentication mfa = new MultiFactorAuthentication();
        String mfaSecret = mfa.setupMFA(email);
        // Get OTP code
        System.out.print("Enter the 6-digit code from Google Authenticator: ");
        String otpInput = scanner.nextLine().trim();
        if (!mfa.verifyOTP(mfaSecret, otpInput)) {
            System.out.println(">>> Error: Invalid OTP. Registration failed.");
            System.out.println("");
            return;
        }

        // Save data
        boolean saved = CustomerFileHandler.saveCustomerData(username, email, password, phoneNum, deliveryAddress, mfaSecret);
        if (saved) {
            System.out.println(">>> Registration Successful! Welcome, " + username);
            customerLogin();
        } else {
            System.out.println(">>> Registration Failed! Please try again.");
            System.out.println("");
            customerRegister();
        }
    }
    
    public static void customerLogin() {
        Scanner scanner = new Scanner(System.in);
        boolean isAuthenticated = false;

        System.out.println("\n\n#" + "=".repeat(27) + " Customer Account Login " + "=".repeat(28) + "#");

        do {
            System.out.print("Please enter your email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Please enter your password: ");
            String password = scanner.nextLine().trim();

            // Read and validate user credentials from file
            String[] customerData = CustomerFileHandler.getCustomerDataByEmail(email);
            if (customerData != null && customerData.length == 6) {
                String savedPassword = customerData[2];
                String mfaSecret = customerData[5];

                if (savedPassword.equals(password)) {
                    // Validate MFA
                    MultiFactorAuthentication mfa = new MultiFactorAuthentication();
                    System.out.print("Enter the 6-digit code from Google Authenticator: ");
                    String otpInput = scanner.nextLine().trim();

                    if (mfa.verifyOTP(mfaSecret, otpInput)) {
                        System.out.println(">>> Login Successful! Welcome, " + customerData[0]);
                        AuthService.setCurrentUserEmail(email); // Track the logged-in user
                        isAuthenticated = true;
                        home();
                        break;
                    } else {
                        System.out.println(">>> Error: Invalid OTP. Please try again.");
                        System.out.println("");
                    }
                } else {
                    System.out.println(">>> Error: Incorrect password.");
                    System.out.println("");
                }
            } else {
                System.out.println(">>> Error: Email not found or data corrupted.");
                System.out.println("");
            }
        } while (!isAuthenticated);
    }
    
   public static void home() {
        Scanner scanner = new Scanner(System.in);
        int customerOption;

        do {
            System.out.println("\n\n#" + "=".repeat(27) + " Home " + "=".repeat(27) + "#");
            System.out.println("1. Search and filter product");
            System.out.println("2. View Cart");
            System.out.println("3. View Order History");
            System.out.println("4. View Payment History");
            System.out.println("5. Request Refund"); // <--- Added here
            System.out.println("6. Log out");
            System.out.println("#" + "=".repeat(60) + "#");
            System.out.print("Please enter your option (1-6): ");

            if (scanner.hasNextInt()) {
                customerOption = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println(">>> Invalid input. Please enter a number between 1 and 6.");
                scanner.nextLine(); // Clear invalid input
                customerOption = -1; // Force loop to continue
            }

            Customer customer = new Customer();
            customer.setEmail(AuthService.getCurrentUserEmail());

            switch (customerOption) {
                case 1:
                    searchAndFilterProducts();
                    break;
                case 2:
                    viewCart();
                    break;
                case 3:
                    OrderFileHandler.viewOrderHistory(AuthService.getCurrentUserEmail());
                    break;
                case 4:
                    PaymentHandler.viewPaymentHistory(AuthService.getCurrentUserEmail());
                    break;
                case 5:
                    requestRefund();
                    break;
                case 6:
                    System.out.println(">>> Logged out successfully.");
                    customerPage();
                    break;
                default:
                    System.out.println(">>> Please enter a valid option (1-6)!!");
            }

        } while (customerOption != 6); // Loop until Log out

        scanner.close();
    }
   
   private static void requestRefund() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n#" + "=".repeat(25) + " Request Refund " + "=".repeat(25) + "#");
        System.out.print("Enter your Order ID to refund: ");
        String orderId = scanner.nextLine().trim();

        Order order = OrderFileHandler.getEligibleOrder(orderId);

        if (order == null) {
            System.out.println(">>> Order not found! Please check your Order ID.");
            return;
        }

        if (!OrderFileHandler.isRefundEligible(order)) {
            System.out.println(">>> This order is not eligible for refund. Only orders with ORDERACCEPTED status can be refunded.");
            return;
        }

        String currentUserEmail = AuthService.getCurrentUserEmail();
        if (!currentUserEmail.equals(order.getCustomer().getEmail())) {
            System.out.println(">>> You can only request refunds for your own orders!");
            return;
        }

        // Display order details
        System.out.println("\nOrder Details:");
        System.out.println("Order ID: " + order.getOrderID());
        System.out.println("Email: " + order.getCustomer().getEmail());
        System.out.printf("Amount: RM%.2f\n", order.getTotalAmount());
        System.out.println("Status: " + order.getOrderStatus());

        System.out.print("\nDo you want to request a refund for this order? (Y/N): ");
        String confirmation = scanner.nextLine().trim().toUpperCase();
        if (!confirmation.equals("Y")) {
            System.out.println(">>> Refund request cancelled.");
            return;
        }

        System.out.print("Enter reason for refund: ");
        String refundReason = scanner.nextLine().trim();
        if (refundReason.isBlank()) {
            System.out.println(">>> Refund reason cannot be empty.");
            return;
        }

        Refund refund = new Refund(currentUserEmail, orderId, order.getTotalAmount(), refundReason);
        refund.saveToFile();

        boolean updated = OrderFileHandler.updateOrderStatus(orderId, Order.OrderStatus.ORDERCANCELLED.toString());

        if (updated) {
            System.out.println("\n>>> Refund request submitted successfully!");
            refund.printTransactionDetails();
        } else {
            System.out.println("\n>>> Refund request submitted, but failed to update order status!");
        }
    }
   
    public static void viewCart() {
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart(AuthService.getCurrentUserEmail());

        // Display cart items
        cart.displayCart();

        if (cart.getCartItems().isEmpty()) {
            System.out.println("");
            System.out.println(">>> Your cart is empty.");
            return;
        }

        while (true) {
            System.out.print("\nDo you want to proceed to checkout? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                // Prompt for item IDs
                System.out.print("\nEnter items to checkout (comma-separated, e.g. L0001,P1234): ");
                String[] rawIds = scanner.nextLine().split(",");
                // Convert array of string to list of string
                List<String> selectedIds = Arrays.asList(rawIds);

                Map<String, Integer> validItems = cart.getValidCheckoutItems(selectedIds);
                if (validItems.isEmpty()) {
                    System.out.println(">>> No valid items selected. Returning to home.");
                    break;
                }

                double total = cart.generateCheckoutSummary(validItems);

                // Confirm checkout
                System.out.print("\nConfirm checkout? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    System.out.println(">>> Checkout cancelled.");
                    break;
                }

                // Payment method selection
                System.out.println("\n#" + "=".repeat(30) + " Payment Process " + "=".repeat(30) + "#");
                System.out.println("Available Methods: CREDITCARD, DEBITCARD, ONLINEBANKING, EWALLET");
                System.out.println("");
                System.out.print("Enter payment method: ");
                String methodInput = scanner.nextLine().trim().toUpperCase();

                Payment.PaymentMethod method = Payment.choosePaymentMethod(methodInput);
                while (method == null) {
                    System.out.print(">>> Invalid method. Try again: ");
                    System.out.println("");
                    method = Payment.choosePaymentMethod(scanner.nextLine().trim().toUpperCase());
                }

                // Amount handling
                double amountPaid;
                System.out.print("Enter amount paid (RM): ");
                try {
                    amountPaid = Double.parseDouble(scanner.nextLine());
                    while (amountPaid < total) {
                        System.out.print(">>> Insufficient amount. Enter again (RM): ");
                        amountPaid = Double.parseDouble(scanner.nextLine());
                    }
                } catch (NumberFormatException e) {
                    System.out.println(">>> Invalid amount. Returning to home.");
                    break;
                }

                System.out.println("");

                // Order ID and date
                String orderId = OrderManagement.generateOrderID();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String orderDate = now.format(formatter);

                // Create Customer
                Customer customer = new Customer();
                customer.setEmail(AuthService.getCurrentUserEmail());

                PaymentHandler handler = new PaymentHandler();
                Payment finalPayment = handler.processPayment(method, amountPaid, total, orderId, validItems, orderDate);

                if (finalPayment == null) {
                    System.out.println(">>> Payment failed.");
                    return;
                }

                System.out.println(">>> Payment successful! Thank you.");

                cart.processCheckout(validItems, amountPaid, method, orderId, orderDate);

                // Save order
                Order order = new Order(orderId, customer, total, orderDate);
                order.setOrderStatus(Order.OrderStatus.ORDERACCEPTED);
                OrderFileHandler.saveOrder(order, new ArrayList<>(validItems.keySet()));

                cart.deductStock(validItems);

                System.out.println(">>> Order successfully placed! Order ID: " + orderId);

                // Print receipt 
                PaymentHandler.printReceipt(finalPayment, amountPaid, total, customer.getEmail(), orderId, validItems, orderDate);
                break;

            } else if (choice.equals("n")) {
                break;
            } else {
                System.out.println(">>> Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    public static void searchAndFilterProducts() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n\n#" + "=".repeat(20) + " Search & Filter " + "=".repeat(20) + "#");
            System.out.println("1. Search by product type");
            System.out.println("2. Filter by price range");
            System.out.println("3. Filter by color");
            System.out.println("4. Combine filters");
            System.out.println("5. View all products");
            System.out.println("6. Return to home");
            System.out.println("#" + "=".repeat(57) + "#");
            System.out.print("Please enter your option (1-6): ");

            int searchOption;
            if (scanner.hasNextInt()) {
                searchOption = scanner.nextInt();
                scanner.nextLine(); // Clear newline
            } else {
                System.out.println(">>> Invalid input. Please enter a number between 1 and 6.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            String searchType = "";
            String searchColor = "";
            double minPrice = 0;
            double maxPrice = Double.MAX_VALUE;

            switch (searchOption) {
                case 1 -> {
                    Product.displayAllProductTypes();
                    System.out.print("\nEnter product type (e.g., Laptop, Keyboard): ");
                    searchType = scanner.nextLine();
                }
                case 2 -> {
                    try {
                        System.out.print("\nEnter minimum price: ");
                        minPrice = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter maximum price: ");
                        maxPrice = Double.parseDouble(scanner.nextLine());
                        if (maxPrice < minPrice) {
                            System.out.println(">>> Maximum price cannot be less than minimum price. Swapping values.");
                            double temp = minPrice;
                            minPrice = maxPrice;
                            maxPrice = temp;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(">>> Invalid input. Please enter valid numeric values.");
                        continue;
                    }
                }
                case 3 -> {
                    System.out.print("\nEnter color: ");
                    searchColor = scanner.nextLine();
                }
                case 4 -> {
                    System.out.print("\nEnter product type (leave empty for any): ");
                    searchType = scanner.nextLine();
                    System.out.print("Enter color (leave empty for any): ");
                    searchColor = scanner.nextLine();

                    System.out.print("Enter minimum price (press Enter for any): ");
                    String minInput = scanner.nextLine().trim();
                    if (!minInput.isEmpty()) {
                        try {
                            minPrice = Double.parseDouble(minInput);
                        } catch (NumberFormatException e) {
                            System.out.println(">>> Invalid minimum price format. Using default (0).");
                        }
                    }

                    System.out.print("Enter maximum price (press Enter for any): ");
                    String maxInput = scanner.nextLine().trim();
                    if (!maxInput.isEmpty()) {
                        try {
                            maxPrice = Double.parseDouble(maxInput);
                        } catch (NumberFormatException e) {
                            System.out.println(">>> Invalid maximum price format. Using default (no limit).");
                        }
                    }

                    if (maxPrice < minPrice) {
                        System.out.println(">>> Maximum price cannot be less than minimum price. Swapping values.");
                        double temp = minPrice;
                        minPrice = maxPrice;
                        maxPrice = temp;
                    }
                }
                case 5 -> {
                    // Leave searchType, searchColor, minPrice, maxPrice 
                }
                case 6 -> {
                    return; // Exit to home
                }
                default -> {
                    System.out.println(">>> Please enter a valid option (1-6)!!");
                    continue;
                }
            }

            searchProducts(searchType, searchColor, minPrice, maxPrice);
        }
    }

    private static void searchProducts(String productType, String productColor, double minPrice, double maxPrice) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Product.filePath));
            String line;
            boolean found = false;

            System.out.println("\n\n#" + "=".repeat(80) + " Product Search Results " + "=".repeat(80) + "#");
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

                    boolean matchesType = productType.isEmpty() || type.name().equalsIgnoreCase(productType);
                    boolean matchesColor = productColor.isEmpty() || color.equalsIgnoreCase(productColor);
                    boolean matchesPrice = price >= minPrice && price <= maxPrice;

                    if (matchesType && matchesColor && matchesPrice) {
                        Product product;
                        String extraInfo = "";

                        switch (type) {
                            case LAPTOP:
                                product = new Laptop(id, name, price, stock, description, color, type,
                                        parts[7].trim(), parts[8].trim(), parts[9].trim());
                                extraInfo = String.format("RAM: %s, Storage: %s, Processor: %s",
                                        parts[7], parts[8], parts[9]);
                                break;
                            case MONITOR:
                                product = new Monitor(id, name, price, stock, description, color, type,
                                        parts[7].trim(), parts[8].trim(), parts[9].trim());
                                extraInfo = String.format("Resolution: %s, Size: %s, Refresh Rate: %s",
                                        parts[7], parts[8], parts[9]);
                                break;
                            case KEYBOARD:
                                product = new Keyboard(id, name, price, stock, description, color, type,
                                        parts[7].trim(), parts[8].trim(), parts[9].trim());
                                extraInfo = String.format("Type: %s, Switches: %s, Size: %s",
                                        parts[7], parts[8], parts[9]);
                                break;
                            default:
                                product = new Product(id, name, price, stock, description, color, type);
                                extraInfo = "-";
                        }

                        System.out.printf("%-10s | %-22s | RM%-8.2f | %-5d | %-12s | %-10s | %-28s | %-40s\n",
                                id, name, price, stock, color, type, truncateDescription(description), extraInfo);
                        found = true;
                    }
                }
            }

            System.out.println("#" + "=".repeat(184) + "#");

            if (!found) {
                System.out.println(">>> No products found matching your criteria.");
            } else {
                productActionMenu();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Product data file not found at " + Product.filePath);
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

  private static void productActionMenu() {
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
        System.out.println("\n\n#" + "=".repeat(22) + " Product Actions " + "=".repeat(22) + "#");
        System.out.println("1. Add product to cart");
        System.out.println("2. Delete product from cart");
        System.out.println("3. Search for more products");
        System.out.println("4. Return to home menu");
        System.out.println("#" + "=".repeat(61) + "#");
        System.out.print("Please enter your option(1-4): ");
        
        try {
            int action = scanner.nextInt();
            
            switch (action) {
                case 1:
                    System.out.println("");
                    System.out.print("Enter product ID to add to cart: ");
                    String productID = scanner.next().trim();

                    // Quantity validation
                    int quantity = 0;
                    boolean validInput = false;
                    while (!validInput) {
                        System.out.print("Enter quantity: ");
                        try {
                            quantity = scanner.nextInt();
                            if (quantity > 0) {
                                validInput = true;
                            } else {
                                System.out.println(">>> Error: Quantity must be at least 1!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println(">>> Error: Please enter a valid number!");
                            scanner.nextLine(); // Clear invalid input
                        }
                    }

                    // User check
                    String email = AuthService.getCurrentUserEmail();
                    if (email == null) {
                        System.out.println(">>> Error: No user logged in!");
                        break;
                    }

                    // Cart operations
                    Cart cart = new Cart(email);
                    cart.loadCart();
                    
                    Product product = Cart.productCatalog.get(productID);
                    if (product == null) {
                        System.out.println(">>> Error: Product not found.");
                        break;
                    }

                    if (product.productStock < quantity) {
                        System.out.println(">>> Error: Not enough stock available! (Available: " + product.productStock + ")");
                        break;
                    }

                    cart.addItem(productID, quantity);
                    cart.saveCart();

                    scanner.nextLine(); // Consume leftover newline after nextInt()

                    String showCartInput;
                    do {
                        System.out.print("\nShow updated cart? (y/n): ");
                        showCartInput = scanner.nextLine().trim().toLowerCase();

                        if (showCartInput.equals("y")) {
                            viewCart();
                            break;
                        } else if (showCartInput.equals("n")) {
                            break;
                        } else {
                            System.out.println(">>> Invalid input! Please enter 'y' or 'n'.");
                        }
                    } while (true);
                    break;

                case 2: 
                    // Get current user
                    String userEmail = AuthService.getCurrentUserEmail();
                    if (userEmail == null) {
                        System.out.println(">>> Error: No user logged in!");
                        break;
                    }

                    // Load and display cart first
                    Cart userCart = new Cart(userEmail);
                    userCart.loadCart();

                    if (userCart.getCartItems().isEmpty()) {
                        System.out.println(">>> Your cart is empty. Nothing to delete!");
                        break;
                    }

                    System.out.println("\nCurrent Cart Contents:");
                    userCart.displayCart();

                    // Get product ID to delete
                    System.out.print("\nEnter product ID to delete from cart: ");
                    String deleteID = scanner.next().trim();

                    // Perform deletion
                    userCart.deleteProduct(deleteID);

                    // Show updated cart confirmation
                    System.out.print("Show updated cart? (y/n): ");
                    scanner.nextLine(); // Clear buffer
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        userCart.displayCart();
                    }
                    break;

                case 3:
                    searchAndFilterProducts();
                    break;
                    
                case 4:
                    home();
                    return;
                    
                default:
                    System.out.println(">>> Please enter valid option(1-4)!!");
            }
        } catch (InputMismatchException e) {
            System.out.println(">>> Invalid input! Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }
}

  // Reduce the description length
    private static String truncateDescription(String description) {
        return description.length() > 30 ? 
            description.substring(0, 27) + "..." : 
            description;
    }
    
}
