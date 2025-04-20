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

    public NormalStaff(String staffID, String email) {
        super(staffID, email, Role.NORMALSTAFF);
    }
    
    
    
    public void performRoleDuties(){
        System.out.println(">>> Normal Staff: Cannot add/ remove products.");
        
        
    }
    @Override
    public void postLoginAction() {
        System.out.println(">>> Welcome to Normal Staff Dashboard.");
        // Maybe show options like "View Profile", "Logout", etc.
    }
}
