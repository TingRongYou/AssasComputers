/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
import assas.computers.Product.ProductType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;




public class AssasComputers {

    /**
     * @param args the command line arguments
     */
    
    public static void displayLogo() {
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .----------------. \n" +
                            "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                            "| |      __      | || |    _______   | || |    _______   | || |      __      | || |    _______   | |\n" +
                            "| |     /  \\     | || |   /  ___  |  | || |   /  ___  |  | || |     /  \\     | || |   /  ___  |  | |\n" +
                            "| |    / /\\ \\    | || |  |  (__ \\_|  | || |  |  (__ \\_|  | || |    / /\\ \\    | || |  |  (__ \\_|  | |\n" +
                            "| |   / ____ \\   | || |   '.___`-.   | || |   '.___`-.   | || |   / ____ \\   | || |   '.___`-.   | |\n" +
                            "| | _/ /    \\ \\_ | || |  |`\\____) |  | || |  |`\\____) |  | || | _/ /    \\ \\_ | || |  |`\\____) |  | |\n" +
                            "| ||____|  |____|| || |  |_______.'  | || |  |_______.'  | || ||____|  |____|| || |  |_______.'  | |\n" +
                            "| |              | || |              | || |              | || |              | || |              | |\n" +
                            "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                            " '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");
    }
    
    public static void home() {
        
        Scanner scanner = new Scanner(System.in);
                
                int customerOption;
                
                do {
                    
                    System.out.println("\n\n#" + "=".repeat(28) + "Home" + "=".repeat(28) + "#");
                    System.out.println("1. Search and filter product\n2. View Cart\n3. View Order History\n4. Track Order Status\n5. Log out");
                    System.out.println("#" + "=".repeat(60) + "#");
                    System.out.print("Please enter your option(1-5): ");
                    customerOption = scanner.nextInt();
                    
                    Customer customer = new Customer();
                    
                    switch (customerOption)
                    {
                         case 1:
                            searchAndFilterProducts();
                            break;
                        case 2:
                            Cart cart = new Cart(Cart.AuthService.getCurrentUserEmail());
                            cart.displayCart();

                            if (!cart.getCartItems().isEmpty()) {  // Only ask if cart is not empty
                                Scanner checkoutScanner = new Scanner(System.in);
                                while (true) {
                                    System.out.print("\nDo you want to proceed to checkout? (y/n): ");
                                    String checkoutChoice = checkoutScanner.nextLine().trim().toLowerCase();

                                    if (checkoutChoice.equals("y")) {
                                        cart.checkout();
                                        break;
                                    } else if (checkoutChoice.equals("n")) {
                                        System.out.println("Returning to home menu...");
                                        break;
                                    } else {
                                        System.out.println("Invalid input. Please enter 'y' or 'n'.");
                                    }
                                }
                            }
                            break;
                        case 3:
                            // View Order History implementation
                            break;
                        case 4:
                            // Track Order Status implementation
                            break;
                        case 5:
                            customerPage();
                            break;
                        default: System.out.println("\nPlease enter valid option(1-5)!!\n");

                    }

                } while (customerOption != 1 && customerOption != 2 && customerOption != 3 && customerOption != 4 && customerOption != 5);
                
                scanner.close();
        
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
            System.out.println("#" + "=".repeat(60) + "#");
            System.out.print("Please enter your option(1-6): ");
            int searchOption = scanner.nextInt();
            scanner.nextLine();

            String searchType = "";
            String searchColor = "";
            double minPrice = 0;
            double maxPrice = Double.MAX_VALUE;

            switch (searchOption) {
                case 1:
                    System.out.print("\nEnter product type (e.g., Laptop, Keyboard): ");
                    searchType = scanner.nextLine();
                    break;
                case 2:
                    System.out.print("\nEnter minimum price: ");
                    minPrice = scanner.nextDouble();
                    System.out.print("Enter maximum price: ");
                    maxPrice = scanner.nextDouble();
                    scanner.nextLine();
                     if (maxPrice < minPrice) {
                    System.out.println("Maximum price cannot be less than minimum price. Swapping values.");
                    double temp = minPrice;
                    minPrice = maxPrice;
                    maxPrice = temp;
                }
                    break;
                case 3:
                    System.out.print("\nEnter color: ");
                    searchColor = scanner.nextLine();
                    break;
                case 4:
                System.out.print("\nEnter product type (leave empty for any): ");
                searchType = scanner.nextLine();
                System.out.print("Enter color (leave empty for any): ");
                searchColor = scanner.nextLine();

                // Handle minimum price input
                System.out.print("Enter minimum price (press Enter for any): ");
                String minInput = scanner.nextLine().trim();
                minPrice = 0.0;  // Default minimum
                if (!minInput.isEmpty()) {
                    try {
                        minPrice = Double.parseDouble(minInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid minimum price format. Using default (0).");
                    }
                }

                // Handle maximum price input
                System.out.print("Enter maximum price (press Enter for any): ");
                String maxInput = scanner.nextLine().trim();
                maxPrice = Double.MAX_VALUE;  // Default maximum
                if (!maxInput.isEmpty()) {
                    try {
                        maxPrice = Double.parseDouble(maxInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid maximum price format. Using default (no limit).");
                    }
                }

                // Validate price range
                if (maxPrice < minPrice) {
                    System.out.println("Maximum price cannot be less than minimum price. Swapping values.");
                    double temp = minPrice;
                    minPrice = maxPrice;
                    maxPrice = temp;
                }
                break;
                case 5:
                    break; // View all products
                case 6:
                    return;
                default:
                    System.out.println("\nPlease enter valid option(1-6)!!\n");
                    continue;
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
            
            System.out.println("\n\n#" + "=".repeat(23) + "Search Results" + "=".repeat(24) + "#");
            System.out.printf("%-10s %-20s %-10s %-10s %-15s %-15s %-30s\n","ID", "Name", "Price", "Stock", "Color", "Type", "Description");
            System.out.println("-".repeat(110));
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 7) {
                    Product product = new Product(
                        parts[0].trim(), 
                        parts[1].trim(), 
                        Double.parseDouble(parts[2].trim()), 
                        Integer.parseInt(parts[3].trim()), 
                        parts[4].trim(), 
                        parts[5].trim(), 
                        ProductType.valueOf(parts[6].trim().toUpperCase())
                    );

                    boolean matchesType = productType.isEmpty() || product.productType.name().equalsIgnoreCase(productType);
                    boolean matchesColor = productColor.isEmpty() || product.productColor.equalsIgnoreCase(productColor);
                    boolean matchesPrice = product.productPrice >= minPrice && product.productPrice <= maxPrice;

                    if (matchesType && matchesColor && matchesPrice) {
                        System.out.printf("%-10s %-20s RM%-9.2f %-10d %-15s %-15s %-30s\n",  product.productID,  product.productName, product.productPrice,  product.productStock, product.productColor, product.productType, truncateDescription(product.productDescription));
                        found = true;
                    }
                }
            }
            
            if (!found) {
                System.out.println("No products found matching your criteria.");
            } else {
                productActionMenu();
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: Product data file not found at " + Product.filePath);
        } catch (IOException e) {
            System.out.println("Error reading product data: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }
    }

  private static void productActionMenu() {
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
        System.out.println("\n#" + "=".repeat(23) + "Product Actions" + "=".repeat(23) + "#");
        System.out.println("1. Add product to cart");
        System.out.println("2. Delete product from cart");
        System.out.println("3. Search for more products");
        System.out.println("4. Return to home menu");
        System.out.println("#" + "=".repeat(60) + "#");
        System.out.print("Please enter your option(1-4): ");
        
        try {
            int action = scanner.nextInt();
            
            switch (action) {
                case 1:
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
                    String email = Cart.AuthService.getCurrentUserEmail();
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

                    System.out.print("Show updated cart? (y/n): ");
                    scanner.nextLine(); // Consume newline
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        cart.displayCart();
                    }
                    break;

                case 2: 
                    // Get current user
                    String userEmail = Cart.AuthService.getCurrentUserEmail();
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
                    System.out.println("\nPlease enter valid option(1-4)!!\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }
}

    private static String truncateDescription(String description) {
        return description.length() > 30 ? 
            description.substring(0, 27) + "..." : 
            description;
    }
    
    public static void customerPage() {
        
                Scanner scanner = new Scanner(System.in);
                
                int customerOption;
                
                do {
                    
                    System.out.println("\n\n#" + "=".repeat(26) + "Customer" + "=".repeat(26) + "#");
                    System.out.println("1. Customer registration\n2. Customer login");
                    System.out.println("#" + "=".repeat(60) + "#");
                    System.out.print("Please enter your option(1/2): ");
                    customerOption = scanner.nextInt();
                    
                    Customer customer = new Customer();
                    
                    switch (customerOption)
                    {
                        case 1:
                            register();
                            break;

                        case 2:
                            login();
                            home();
                            break;
                        default: System.out.println("\nPlease enter valid option(1/2)!!\n");

                    }

                } while (customerOption != 1 && customerOption != 2);
                
                scanner.close();
                
    }
    
    public static void staffPage() {
        
                Scanner scanner = new Scanner(System.in);
                
                int staffOption;
                
                do {
                    
                    System.out.println("\n\n#" + "=".repeat(27) + "Staff" + "=".repeat(28) + "#");
                    System.out.println("1. Staff registration\n2. Staff login");
                    System.out.println("#" + "=".repeat(60) + "#");
                    System.out.print("Please enter your option(1/2): ");
                    staffOption = scanner.nextInt();
                    
                    
                    switch (staffOption)
                    {
                        case 1:
                            //staff.registration();
                            break;
                            
                        case 2:
                            //staff.login();
                            break;
                        default: System.out.println("\nPlease enter valid option(1/2)!!\n");

                    }

                } while (staffOption != 1 && staffOption != 2);
                
                scanner.close();
                
    }
    
    public static void register() {
        Scanner scanner = new Scanner(System.in);
        String username, email, password, phoneNum, deliveryAddress;

        System.out.println("\n\n#" + "=".repeat(27) + " Customer Account Registration " + "=".repeat(28) + "#");

        // Username validation
        do {
            System.out.print("Please enter a username: ");
            username = scanner.nextLine().trim();
            if (!UserAccountValidation.usernameValidate(username)) {
                System.out.println(">>> Error: Username must be 1-20 characters.");
            }
        } while (!UserAccountValidation.usernameValidate(username));

        // Email validation
        do {
            System.out.print("Please enter your email: ");
            email = scanner.nextLine().trim();
            if (!UserAccountValidation.emailValidate(email)) {
                System.out.println(">>> Error: Your email should include '@' and '.com'");
            } else if (CustomerFileHandler.isEmailRegistered(email)) {
                System.out.println(">>> Error: This email is already registered.");
            }
        } while (!UserAccountValidation.emailValidate(email) || CustomerFileHandler.isEmailRegistered(email));

        // Password validation
        do {
            System.out.print("Please enter a password: ");
            password = scanner.nextLine().trim();
            if (!UserAccountValidation.passwordValidate(password)) {
                System.out.println(">>> Error: Password must include uppercase, lowercase, number, special char (!@#%*&$), and be 8-15 chars long.");
            }
        } while (!UserAccountValidation.passwordValidate(password));

        // Phone number validation
        do {
            System.out.print("Please enter your phone number: ");
            phoneNum = scanner.nextLine().trim();
            if (!UserAccountValidation.phoneNumValidate(phoneNum)) {
                System.out.println(">>> Error: Phone number must start with '01' and be 10-11 digits.");
            }
        } while (!UserAccountValidation.phoneNumValidate(phoneNum));

        // Delivery address validation
        do {
            System.out.print("Please enter your delivery address: ");
            deliveryAddress = scanner.nextLine().trim();
            if (!UserAccountValidation.addressCheck(deliveryAddress)) {
                System.out.println(">>> Error: Address must be 1–50 characters.");
            }
        } while (!UserAccountValidation.addressCheck(deliveryAddress));

        // Set up MFA
        MultiFactorAuthentication mfa = new MultiFactorAuthentication();
        String mfaSecret = mfa.setupMFA(email);
        System.out.print("Enter the 6-digit code from Google Authenticator: ");
        String otpInput = scanner.nextLine().trim();
        if (!mfa.verifyOTP(mfaSecret, otpInput)) {
            System.out.println(">>> Error: Invalid OTP. Registration failed.");
            return;
        }

        // Save data
        boolean saved = CustomerFileHandler.saveCustomerData(username, email, password, phoneNum, deliveryAddress, mfaSecret);
        if (saved) {
            System.out.println(">>> Registration Successful! Welcome, " + username);
        } else {
            System.out.println(">>> Registration Failed! Please try again.");
        }
    }
    public static void login() {
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
                        System.out.println("\n>>> Login Successful! Welcome, " + customerData[0]);
                        Cart.AuthService.setCurrentUserEmail(email); // Track the logged-in user
                        isAuthenticated = true;
                        break;
                    } else {
                        System.out.println(">>> Error: Invalid OTP. Please try again.");
                    }
                } else {
                    System.out.println(">>> Error: Incorrect password.");
                }
            } else {
                System.out.println(">>> Error: Email not found or data corrupted.");
            }
        } while (!isAuthenticated);
    }

    
    public static void main(String[] args) {
               
        displayLogo();
        System.out.println("\n\nWelcome to Assas Computer!");
        
        int identity;
        
        do {
        Scanner scanner = new Scanner(System.in);
            
        System.out.println("\n\n#" + "=".repeat(25) + "Main Menu" + "=".repeat(26) + "#");
        System.out.println("1. Customer \n2. Staff");
        System.out.println("#" + "=".repeat(60) + "#");
        System.out.print("Please enter your option(1/2): ");
        identity = scanner.nextInt();
        
        
        switch (identity)
        {
            case 1:
                customerPage();
                break;
               
            case 2:
                staffPage();
                break;
            default: System.out.println("\nPlease enter valid option(1/2)!!\n");

        }
        scanner.close();

        } while (identity != 1 && identity != 2);
                
    }
    
}
