package assas.computers;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Customer extends User {
    private static final String filePath = "src/CustomerAcc.txt";
    private final String deliveryAddress;

    public Customer(String name, String email, String password, String phoneNum, String deliveryAddress) {
        super(name, email, password, phoneNum);
        this.deliveryAddress = deliveryAddress;
    }

    public Customer() {
        this.deliveryAddress = null;
    }

    @Override
    public void registration() {
        Scanner scanner = new Scanner(System.in);
        String username, email, password, phoneNum, deliveryAddress;

        System.out.println("\n\n#" + "=".repeat(27) + " Customer Account Registration " + "=".repeat(28) + "#");

        boolean validUsername = false;
        do {
            System.out.print("Please enter a username: ");
            username = scanner.nextLine().trim();
            validUsername = Customer.usernameValidate(username);
            
            if(!validUsername){
                System.out.println(">>> Error: Please Enter Your Username Again !!");
                System.out.println();
            }
        } while (!Customer.usernameValidate(username));

        do {
            System.out.print("Please enter your email: ");
            email = scanner.nextLine().trim();
            if(!Customer.emailValidate(email)){
                System.out.println(">>> Error: Your Email Should Include '@' And '.com'!");
                System.out.println(">>> Error: Please Enter Your Email Again !!");
                System.out.println();
            }
            else if(isEmailRegistered(email)){
                System.out.println(">>> Error: Customer Account Already Registered. Invalid Registration!");
                System.out.println();
            }
        } while (!Customer.emailValidate(email) || isEmailRegistered(email));

        boolean validPassword = true;
        do {
            System.out.print("Please enter a password: ");
            password = scanner.nextLine();
            validPassword = Customer.passwordValidate(password);
            if(!validPassword){
                System.out.println(">>> Error: Please Enter Customer Password Again !!");
                System.out.println();
            }
        } while (!validPassword);

        do {
             System.out.print("Please enter your phone number: ");
            phoneNum = scanner.nextLine();
            if(!phoneNumValidate(phoneNum)){
                System.out.println(">>> Error: Your Phone Number Should Start With '01' And Be 10 Or 11 Digits Long!");
                System.out.println(">>> Error: Please Enter Your Email Again !!");
                System.out.println();
            }
        } while (!phoneNumValidate(phoneNum));

        do {
            System.out.print("Please enter your delivery address: ");
            deliveryAddress = scanner.nextLine();
            if(!addressCheck(deliveryAddress)) {
                System.out.println(">>> Error: Your Delivery Address Should Be Between 1 to 50 Characters.");
                System.out.println("");
            }
        } while (!addressCheck(deliveryAddress));

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String mfaSecret = key.getKey();

        openQRCodeInBrowser("otpauth://totp/assas.computers:" + email + "?secret=" + mfaSecret + "&issuer=assas.computers");

        System.out.print("Enter the 6-digit code from Google Authenticator: ");
        String otpInput = scanner.nextLine().trim();

        if (!otpInput.matches("\\d{6}") || !gAuth.authorize(mfaSecret, Integer.parseInt(otpInput))) {
            System.out.println(">>> Error: Invalid OTP. Registration failed.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(username + ";" + email + ";" + password + ";" + phoneNum + ";" + deliveryAddress + ";" + mfaSecret);
            writer.newLine();

            System.out.println("\n>>> Registration Successful! Customer Account Data Saved.");

            writer.flush();

        } catch (IOException e) {
            System.out.println(">>> Error: Unable to save customer account data.");
        }

        login();
    }
    private void openQRCodeInBrowser(String qrUrl) {
        try {
            String qrApiUrl = "https://api.qrserver.com/v1/create-qr-code/?data=" + qrUrl + "&size=200x200";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(qrApiUrl));
            }
        } catch (Exception e) {
            System.out.println(">>> Warning: Unable to open QR code in browser.");
        }
    }

    @Override
    public boolean isEmailRegistered(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(";");
                if (words.length > 1 && words[1].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println(">>> Error: Cannot read the file!");
        }
        return false;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    @Override
    public void login() {
        boolean isAuthenticated = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\n#" + "=".repeat(27) + " Customer Account Login " + "=".repeat(28) + "#");

        do {
            System.out.print("Please enter customer account email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Please enter customer account password: ");
            String password = scanner.nextLine().trim();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(";");
                    if (words.length == 6 && words[1].equals(email) && words[2].equals(password)) {
                        isAuthenticated = true;
                        String mfaSecret = words[5];

                        boolean isValidOTP = false;
                        do {
                        GoogleAuthenticator gAuth = new GoogleAuthenticator();
                        System.out.print("Enter the 6-digit code from Google Authenticator: ");
                        String otpInput = scanner.nextLine().trim();

                        if (otpInput.matches("\\d{6}") && gAuth.authorize(mfaSecret, Integer.parseInt(otpInput))) {
                            System.out.println("\n>>> Login Successfully! Welcome, " + words[0]);
                            isValidOTP = true;
                            break;
                        } else {
                            System.out.println(">>> Error: Invalid OTP. Please Try Again.");
                        }
                      } while (!isValidOTP);
                    }
                }
            } catch (IOException e) {
                System.out.println(">>> Error: Unable to read account data.");
                return;
            }

            if (!isAuthenticated) {
                System.out.println(">>> Error: Invalid Credentials. Please Try Again.");
            }
        } while (!isAuthenticated);
    }
} 
 