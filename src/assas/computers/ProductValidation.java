/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

import java.util.Scanner;

/**
 *
 * @author Acer
 */

// A class use to validate input for product information, ensure the product information is valid
public class ProductValidation {
    
    /**
    * Validate product id
    */
    public static boolean isValidProductId(String id) {
        return id != null && !id.trim().isEmpty() && id.matches("^[A-Za-z0-9_-]+$");
        // Ensure product id entered is not empty and matches the pattern (more than 1 letter or alphabet, as well as hyphen and dash)
    }

    /**
    * Validate product name
    */
    public static boolean isValidProductName(String name) {
        return name != null && !name.trim().isEmpty();
        // Ensure the product name entered is not empty
    }

    /**
    * Validate product price
    */
    public static boolean isValidPrice(double price) {
        return price >= 0.0;
        // Ensure the price entered is in positive number
    }

    /**
    * Validate stock number
    */
    public static boolean isValidStock(int stock) {
        return stock >= 0;
        // Ensure the stock number entered is in positive number
    }

    /**
    * Validate product description
    */
    public static boolean isValidDescription(String desc) {
        return desc != null && !desc.trim().isEmpty();
    }

    /**
    * Validate product color
    */
    public static boolean isValidColor(String color) {
        return color != null && !color.trim().isEmpty();
    }

    /**
    * Validate product type enumerator
    */
    public static boolean isValidEnumValue(String input, Class<? extends Enum<?>> enumClass) {
        for (Enum<?> constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
    
    /**
    * Validate input in double
    */
    public static double getValidatedDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print(">>> Invalid input. Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    /**
    * Validate input in integer
    */
    public static int getValidatedInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print(">>> Invalid input. Enter a valid integer: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

}
