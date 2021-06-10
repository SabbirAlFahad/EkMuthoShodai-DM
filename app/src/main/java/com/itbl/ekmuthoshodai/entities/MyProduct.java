package com.itbl.ekmuthoshodai.entities;

public class MyProduct {

    private String imName;
    private String imStock;
    private String imRate;
    private String imQuantity;
    private String imAmount;
    private String imDiscount;

    public MyProduct(String imName, String imStock,
                     String imRate, String imQuantity, String imAmount, String imDiscount) {

        this.imName = imName;
        this.imStock = imStock;
        this.imRate = imRate;
        this.imQuantity = imQuantity;
        this.imAmount = imAmount;
        this.imDiscount = imDiscount;
    }

    public String getImName() {
        return imName;
    }

    public void setImName(String imName) {
        this.imName = imName;
    }

    public String getImStock() {
        return imStock;
    }

    public void setImStock(String imStock) {
        this.imStock = imStock;
    }

    public String getImRate() {
        return imRate;
    }

    public void setImRate(String imRate) {
        this.imRate = imRate;
    }

    public String getImQuantity() {
        return imQuantity;
    }

    public void setImQuantity(String imQuantity) {
        this.imQuantity = imQuantity;
    }

    public String getImAmount() {
        return imAmount;
    }

    public void setImAmount(String imAmount) {
        this.imAmount = imAmount;
    }

    public String getImDiscount() {
        return imDiscount;
    }

    public void setImDiscount(String imDiscount) {
        this.imDiscount = imDiscount;
    }

}
