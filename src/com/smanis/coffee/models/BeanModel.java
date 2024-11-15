/*
 */
package com.smanis.coffee.models;

public class BeanModel {

    private String beanId;
    private String beanName;
    private String beanDensity;
    
    public BeanModel(String id, String name, String density) {
        this.beanId = id;
        this.beanName = name;
        this.beanDensity = density;
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
     * @param beanId the beanId to set
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
}
