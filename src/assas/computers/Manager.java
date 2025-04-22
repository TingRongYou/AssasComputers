package assas.computers;

public class Manager extends Staff {
    
    /**
    * constructor
    */
    public Manager() {
        
    }

    public Manager(String staffID, String email, Role role, String setupKey) {
        super(staffID, email, Role.MANAGER, setupKey);
    }
    
    //Action that only manager can perform after login
    @Override
    public void postLoginAction() {
        System.out.println("Welcome, Manager. You can manage products.");
        new ManagerController().managerMenu(this); // Delegate to controller
    }
}

