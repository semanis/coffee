/*
 */
package com.smanis.coffee.models;

public class BeanModel {

    private String beanId;
    private String beanName;
    private String beanDensity;
    private int inStock;
    
    public BeanModel(String id, String name, String density, int inStock) {
        this.beanId = id;
        this.beanName = name;
        this.beanDensity = density;
        this.inStock = inStock;
    }

    public String toString() {
        return this.getBeanName();
    }

    /**
     * @return the beanId
     */
    public String getBeanId() {
        return beanId;
    }

    /**
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * @return the beanName
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * @return the beanDensity
     */
    public String getBeanDensity() {
        return beanDensity;
    }

    /**
     * @param beanDensity the beanDensity to set
     */
    public void setBeanDensity(String beanDensity) {
        this.beanDensity = beanDensity;
    }

    /**
     * @return the inStock
     */
    public int getInStock() {
        return this.inStock;
    }

    /**
     * @param inStock the inStock to set
     */
    public void setInStock(int in) {
        this.inStock = in;
    }
    
    public boolean isInStock() {
        return this.inStock == 1;
    }

 
}
