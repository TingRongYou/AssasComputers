package assas.computers;

public class Customer extends User {
    private static final String CUSTOMER_FILE_PATH = "src/textFile/CustomerAcc.txt";
    private String deliveryAddress;
    
    /** 
     * constructors
     */
     public Customer() {
        this.deliveryAddress = null;
    }
     
    public Customer(String email) {
        setEmail(email);
        this.deliveryAddress = null;
    }

    public Customer(String name, String email, String password, String phoneNum, String deliveryAddress) {
        super(name, email, password, phoneNum);
        this.deliveryAddress = deliveryAddress;
    }

    /** 
     * getter
     */
    public static String getFilePath() {
        return CUSTOMER_FILE_PATH;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
} 
 