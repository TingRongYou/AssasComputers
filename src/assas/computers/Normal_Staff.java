/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author User
 */
public class Normal_Staff extends Staff {
    public Normal_Staff(String name, String email, String password, String phoneNum){
        super(name, email, password, phoneNum,"xx" );
    }
    
    
    public void performRoleDuties(){
        System.out.println(">>> Normal Staff: Cannot add/ remove products.");
        
        
    }
    
}
