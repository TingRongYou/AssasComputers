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

// A class that use to store customer account registration
public class CustManageProfile {
    
    public boolean registerCustomer(String username, String email, String password, String phoneNum, String deliveryAddress) {
        if (!UserAccountValidation.usernameValidate(username) || 
            !UserAccountValidation.emailValidate(email) || 
            !UserAccountValidation.passwordValidate(password) || 
            !UserAccountValidation.phoneNumValidate(phoneNum) ||
            !UserAccountValidation.addressCheck(deliveryAddress)) {
            return false;
        }

        if (CustomerFileHandler.isEmailRegistered(email)) {
            System.out.println(">>> Email already registered.");
            return false;
        }

        // Generate MFA secret
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String mfaSecret = key.getKey();

        // Let the UI class open the QR code
        MultiFactorAuthentication.openQRCodeInBrowser("otpauth://totp/assas.computers:" + email + "?secret=" + mfaSecret + "&issuer=assas.computers");

        // Save account
        CustomerFileHandler.saveCustomerData(username, email, password, phoneNum, deliveryAddress, mfaSecret);
        return true;
    }


}
