/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
public class UserAccountValidation {
    
    /**
    * Validate a user name from 1 to 20 character
    */
    public static boolean usernameValidate(String username) {
        return (username.length() > 0 && username.length() <= 20);
    }

    /**
    * Validate a email matches basic format
    */
    public static boolean emailValidate(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        //^ shows the start of the string
        //[\\w.-] matches any letter, number, underscore, period and dash
        //\\.[a-zA-Z]{2,} ensures the end of the email matches format of ".xx", better flexibility for domain like .yahoo with at least 2 letters
        //$ shows the end of the string
    }

    /**
    * Validate a phone number matches basic format
    */
    public static boolean phoneNumValidate(String phoneNum) {
        return phoneNum.matches("^01\\d{8,9}$");
        //phone number format starting with 01 and follows with 8 to 9 number
    }

    /**
    * Validate a password
    */
    public static boolean passwordValidate(String password) {
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false, sufficientLength = false;
        boolean validPassword = true;
        char[] specialCharacters = {'!', '@', '#', '%', '*', '&', '$'};

        if (password.length() >= 8 && password.length() <= 15) {
            sufficientLength = true;
        } 
        else{
            sufficientLength = false;
        }
        
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)){
                hasUpper = true;
            }
            else if (Character.isLowerCase(ch)){
                hasLower = true;
            }
            else if (Character.isDigit(ch)){
                hasDigit = true;
            }
            else {
                for (char special : specialCharacters) {
                    if (ch == special) {
                        hasSpecial = true;
                        break;
                    }
                }
            }
        }
    
        return hasUpper && hasLower && hasDigit && hasSpecial && sufficientLength;

  }
    
    /**
    * Validate a delivery address from 1 to 50 character
    */
    public static boolean addressCheck(String deliveryAddress) {
        deliveryAddress = deliveryAddress.trim();
        return (deliveryAddress.length() > 0 && deliveryAddress.length() <= 50);
        }
    
    /**
    * Validate staff id matches staff id format
    */
    public static boolean isValidStaffID(String staffID) {
        return staffID.matches("S\\d{4}");
    }
    
}
