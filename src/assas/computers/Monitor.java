/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assas.computers;

/**
 *
 * @author Acer
 */
public class Monitor extends Product{
    String resolution;
    String panelSize;
    String refreshRate;
    
    /**
    * constructor
    */
    public Monitor (String productID, String productName, double productPrice, int productStock, String productDescription, String productColor, ProductType productType, String resolution, String panelSize, String refreshRate) {
        super(productID, productName, productPrice, productStock, productDescription, productColor, productType);
        this.resolution = resolution;
        this.panelSize = panelSize;
        this.refreshRate = refreshRate;
    }
    
    /**
    * getter and setter
    */
    public String getResolution () { 
        return resolution; }
    
    public String getPanelSize() { 
        return panelSize; }
    
    public String getRefreshRate() { 
        return refreshRate; }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setPanelSize(String panelSize) {
        this.panelSize = panelSize;
    }

    public void setRefreshRate(String refreshRate) {
        this.refreshRate = refreshRate;
    }
    
}
