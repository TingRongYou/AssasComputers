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
import java.util.Scanner;

public class MultiFactorAuthentication {
    
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public String setupMFA(String email) {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secret = key.getKey();
        openQRCodeInBrowser("otpauth://totp/assas.computers:" + email + "?secret=" + secret + "&issuer=assas.computers");
        return secret;
    }

    public boolean verifyOTP(String secret) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the 6-digit OTP: ");
        String otpInput = scanner.nextLine().trim();

        return otpInput.matches("\\d{6}") && gAuth.authorize(secret, Integer.parseInt(otpInput));
    }

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
