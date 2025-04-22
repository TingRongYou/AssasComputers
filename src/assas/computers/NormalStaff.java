/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author User
 */
public class NormalStaff extends Staff {

    /**
    * constructor
    */
    public NormalStaff() {
    }

    public NormalStaff(String staffID, String email, Role role, String setupKey) {
        super(staffID, email, Role.NORMALSTAFF, setupKey);
    }
    
    //Action that normal staff can perform after login
    @Override
    public void postLoginAction() {
        System.out.println(">>> Welcome to Normal Staff Dashboard.");
        // Maybe show options like "View Profile", "Logout", etc.
    }
}
