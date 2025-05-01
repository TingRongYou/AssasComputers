/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;
    
public abstract class User {
    /*static is not needed since static variable shares resources among all instances of the class,
    every time a new user registers, their details will overwrite the previous user's data
    */
    
    protected String username;
    protected String email;
    protected String password;
    protected String phoneNum;
    
    /** 
     * constructors
     */
    protected User() {
        
    }
    
    protected User(String name, String email, String password, String phoneNum) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }
    
    /**
     * Getter and Setter
     */
    public String getUsername(){
        return username;  
    }
    
    public String getEmail(){
       return email;
    }
    
    public String getPassword(){
        return password;
    }
    public String getPhoneNum(){
        return phoneNum;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}


