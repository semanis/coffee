/*
 */
package com.smanis.coffee.models;

import java.time.LocalDateTime;

/**
 *
 * @author semanis
 */
public class RoastLog {
    private String beanName;
    private String beanId;
    private float greenWeight;
    private float roastedWeight;
    private float moistureLoss;
    private String chargeTemp;
    private LocalDateTime roastStart;
    private LocalDateTime dryTime;
    private LocalDateTime firstCrackStart;
    private LocalDateTime firstCrackEnd;
    private LocalDateTime secondCrackStart;
    private LocalDateTime secondCrackEnd;
    private LocalDateTime endRoast;
    private String roastNotes;
    private String tastingNotes;
    private String id;

    /**
     * @return the beanName
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * @param beanName the beanName to set
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
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
     * @return the greenWeight
     */
    public float getGreenWeight() {
        return greenWeight;
    }

    /**
     * @param greenWeight the greenWeight to set
     */
    public void setGreenWeight(float greenWeight) {
        this.greenWeight = greenWeight;
    }

    /**
     * @return the RoastedWeight
     */
    public float getRoastedWeight() {
        return roastedWeight;
    }

    /**
     * @param RoastedWeight the RoastedWeight to set
     */
    public void setRoastedWeight(float roastedWeight) {
        this.roastedWeight = roastedWeight;
    }

    /**
     * @return the moistureLoss
     */
    public float getMoistureLoss() {
        return moistureLoss;
    }

    /**
     * @param moistureLoss the moistureLoss to set
     */
    public void setMoistureLoss(float moistureLoss) {
        this.moistureLoss = moistureLoss;
    }

    /**
     * @return the chargeTemp
     */
    public String getChargeTemp() {
        return chargeTemp;
    }

    /**
     * @param chargeTemp the chargeTemp to set
     */
    public void setChargeTemp(String chargeTemp) {
        this.chargeTemp = chargeTemp;
    }

    /**
     * @return the roastStart
     */
    public LocalDateTime getRoastStart() {
        return roastStart;
    }

    /**
     * @param roastStart the roastStart to set
     */
    public void setRoastStart(LocalDateTime roastStart) {
        this.roastStart = roastStart;
    }

    /**
     * @return the dryTime
     */
    public LocalDateTime getDryTime() {
        return dryTime;
    }

    /**
     * @param dryTime the dryTime to set
     */
    public void setDryTime(LocalDateTime dryTime) {
        this.dryTime = dryTime;
    }

    /**
     * @return the firstCrackStart
     */
    public LocalDateTime getFirstCrackStart() {
        return firstCrackStart;
    }

    /**
     * @param firstCrackStart the firstCrackStart to set
     */
    public void setFirstCrackStart(LocalDateTime firstCrackStart) {
        this.firstCrackStart = firstCrackStart;
    }

    /**
     * @return the firstCrackEnd
     */
    public LocalDateTime getFirstCrackEnd() {
        return firstCrackEnd;
    }

    /**
     * @param firstCrackEnd the firstCrackEnd to set
     */
    public void setFirstCrackEnd(LocalDateTime firstCrackEnd) {
        this.firstCrackEnd = firstCrackEnd;
    }

    /**
     * @return the endRoast
     */
    public LocalDateTime getEndRoast() {
        return endRoast;
    }

    /**
     * @param endRoast the endRoast to set
     */
    public void setEndRoast(LocalDateTime endRoast) {
        this.endRoast = endRoast;
    }

    /**
     * @return the roastNotes
     */
    public String getRoastNotes() {
        return roastNotes;
    }

    /**
     * @param roastNotes the roastNotes to set
     */
    public void setRoastNotes(String roastNotes) {
        this.roastNotes = roastNotes;
    }

    /**
     * @return the tastingNotes
     */
    public String getTastingNotes() {
        return tastingNotes;
    }

    /**
     * @param tastingNotes the tastingNotes to set
     */
    public void setTastingNotes(String tastingNotes) {
        this.tastingNotes = tastingNotes;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the secondCrackStart
     */
    public LocalDateTime getSecondCrackStart() {
        return secondCrackStart;
    }

    /**
     * @param secondCrackStart the secondCrackStart to set
     */
    public void setSecondCrackStart(LocalDateTime secondCrackStart) {
        this.secondCrackStart = secondCrackStart;
    }

    /**
     * @return the secondCrackEnd
     */
    public LocalDateTime getSecondCrackEnd() {
        return secondCrackEnd;
    }

    /**
     * @param secondCrackEnd the secondCrackEnd to set
     */
    public void setSecondCrackEnd(LocalDateTime secondCrackEnd) {
        this.secondCrackEnd = secondCrackEnd;
    }


}
