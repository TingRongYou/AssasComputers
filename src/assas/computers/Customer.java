package assas.computers;

public class Customer extends User {
    private static final String filePath = "src/textFile/CustomerAcc.txt";
    private final String deliveryAddress;
    
    /** 
     * constructors
     */
     public Customer() {
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
        return filePath;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
} 
 