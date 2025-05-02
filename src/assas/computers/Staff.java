/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */

public abstract class Staff extends User {
    private static final String STAFF_FILE_PATH = "src/textFile/StaffAcc.txt"; // Keep as static constant
    protected String staffID;
    protected String email;
    protected String setupKey;
    protected Role role;
    
    /** 
     * constructors
     */
    protected Staff() {
        
    }
    
    protected Staff(String staffID, String email, Role role, String setupKey){
        this.staffID = staffID;
        this.email = email;        
        this.role = role;
        this.setupKey = setupKey;
    }
    
    /** 
     * getter and setter
     */
    public String getStaffID() {
        return staffID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public static String getStaffPath(){
        return STAFF_FILE_PATH;
    }
    
    public Role getRole() {
        return role;
    }

    public String getSetupKey() {
        return setupKey;
    }

    public void setSetupKey(String key) {
        this.setupKey = key;
    }

    public enum Role {
        NORMALSTAFF, ADMIN, MANAGER
    }
    
    public abstract void postLoginAction(); // Enforce all of the staff to have a unique postLoginAction
    
    @Override
    public String toString() {
        return staffID + " (" + email + ") - Role: " + role;
    }
}
