/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author User
 */
public class Admin extends Staff {
    
    public Admin(String staffID, String email, Role role, String setupKey) {
        super(staffID, email, Role.ADMIN, setupKey);
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSetupKey() {
        return setupKey;
    }

    public void setSetupKey(String setupKey) {
        this.setupKey = setupKey;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    @Override
    public void postLoginAction() {
        System.out.println("Welcome, Admin. You can manage staff account for Assas Computers.");
        new AdminController().adminMenu(this); // Delegate to controller
    }
}
