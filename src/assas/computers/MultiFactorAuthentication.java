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

// A class that store operation regarding MFA operation for user login
public class MultiFactorAuthentication {
    
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // Get secret key
    public String setupMFA(String email) {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secret = key.getKey();
        openQRCodeInBrowser("otpauth://totp/assas.computers:" + email + "?secret=" + secret + "&issuer=assas.computers");
        return secret;
    }

    // Verify if OTP entered by user is valid
    public boolean verifyOTP(String secret, String otpInput) {
        return otpInput.matches("\\d{6}") && gAuth.authorize(secret, Integer.parseInt(otpInput));
    }

    // Open qr code for the user to scan using google authenticator application
    public static void openQRCodeInBrowser(String qrUrl) {
        try {
            String qrApiUrl = "https://api.qrserver.com/v1/create-qr-code/?data=" + qrUrl + "&size=200x200";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(qrApiUrl));
            }
        } catch (Exception e) {
            System.out.println(">>> Warning: Unable to open QR code in browser.");
        }
    }
}
