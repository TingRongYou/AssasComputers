/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import java.awt.*;
import java.net.URI;

// A class that store operation regarding MFA operation for user login (Google Authenticator application needed)
public class MultiFactorAuthentication {
    
    // Create instance of GoogleAuthenticator to manage MFA operations
    // Generate secret keys and verify OTP codes
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // Get secret key
    public String setupMFA(String email) {
        GoogleAuthenticatorKey key = gAuth.createCredentials(); // Generates new random secret key
        String secret = key.getKey(); // Special code that the Google Authenticator app will use to generate correct OTP codes for the user
        // Builds special URL [(otpauth://) <-- Used by Authenticator apps to generate one-time passcodes using OATH] that can be converted into a QR code
        // call openQRCodeInBrowser to let user scan QR code using the app
        openQRCodeInBrowser("otpauth://totp/assas.computers:" + email + "?secret=" + secret + "&issuer=assas.computers");
        return secret; // Return secret key to store in file
    }

    // Verify if OTP entered by user is valid
    public boolean verifyOTP(String secret, String otpInput) {
        // Check if OTP is exactly 6 digits
        // gAuth.authorize(secret, otpInput) check if the OTP entered by the user is valid based on secret stored
        return otpInput.matches("\\d{6}") && gAuth.authorize(secret, Integer.parseInt(otpInput)); // If valid, then true, else false
    }

    // Open qr code for the user to scan using google authenticator application
    public static void openQRCodeInBrowser(String qrUrl) {
        try {
            // Take qrUrl and builds a URL for free QR code generation service (api.qrserver.com)
            String qrApiUrl = "https://api.qrserver.com/v1/create-qr-code/?data=" + qrUrl + "&size=200x200";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(qrApiUrl));
            }
        } catch (Exception e) {
            System.out.println(">>> Warning: Unable to open QR code in browser.");
        }
    }
}
