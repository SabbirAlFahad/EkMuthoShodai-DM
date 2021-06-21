package com.itbl.ekmuthoshodai.entities;

public class ProductOrderExt {

   String odrNo;
   String odrQuantity;
   String odrRate;
   String odrAmount;
   String odrStatus;
   String oderDate;

    public ProductOrderExt(String odrNo, String odrQuantity, String odrRate, String odrAmount, String odrStatus, String oderDate) {
        this.odrNo = odrNo;
        this.odrQuantity = odrQuantity;
        this.odrRate = odrRate;
        this.odrAmount = odrAmount;
        this.odrStatus = odrStatus;
        this.oderDate = oderDate;
    }

    public String getOdrNo() {
        return odrNo;
    }

    public void setOdrNo(String odrNo) {
        this.odrNo = odrNo;
    }

    public String getOdrQuantity() {
        return odrQuantity;
    }

    public void setOdrQuantity(String odrQuantity) {
        this.odrQuantity = odrQuantity;
    }

    public String getOdrRate() {
        return odrRate;
    }

    public void setOdrRate(String odrRate) {
        this.odrRate = odrRate;
    }

    public String getOdrAmount() {
        return odrAmount;
    }

    public void setOdrAmount(String odrAmount) {
        this.odrAmount = odrAmount;
    }

    public String getOdrStatus() {
        return odrStatus;
    }

    public void setOdrStatus(String odrStatus) {
        this.odrStatus = odrStatus;
    }

    public String getOderDate() {
        return oderDate;
    }

    public void setOderDate(String oderDate) {
        this.oderDate = oderDate;
    }
}
