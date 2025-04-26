/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */

// A class that is used to handle user's authentication status, to manage login session
public class AuthService { 
    
    // Store email of current login user
    // Static so that the login state is shared accross the entire application
    private static String currentUserEmail = null;  

    /**
    * getter and setter
    */
    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }  

    public static void setCurrentUserEmail(String email) {
        currentUserEmail = email;
    } 

    public static void clearCurrentUser() {
        currentUserEmail = null;
    }  
}
