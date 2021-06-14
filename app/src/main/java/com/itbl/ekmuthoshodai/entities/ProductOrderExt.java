package com.itbl.ekmuthoshodai.entities;

public class ProductOrderExt {

   String imName;
   String imStatus;
   String imQuantity;
   String imRAmount;
   String imDiscount;

    public ProductOrderExt(String imName, String imStatus, String imQuantity, String imRAmount, String imDiscount) {
        this.imName = imName;
        this.imStatus = imStatus;
        this.imQuantity = imQuantity;
        this.imRAmount = imRAmount;
        this.imDiscount = imDiscount;
    }

    public String getImName() {
        return imName;
    }

    public void setImName(String imName) {
        this.imName = imName;
    }

    public String getImStatus() {
        return imStatus;
    }

    public void setImStatus(String imStatus) {
        this.imStatus = imStatus;
    }

    public String getImQuantity() {
        return imQuantity;
    }

    public void setImQuantity(String imQuantity) {
        this.imQuantity = imQuantity;
    }

    public String getImRAmount() {
        return imRAmount;
    }

    public void setImRAmount(String imRAmount) {
        this.imRAmount = imRAmount;
    }

    public String getImDiscount() {
        return imDiscount;
    }

    public void setImDiscount(String imDiscount) {
        this.imDiscount = imDiscount;
    }
}
