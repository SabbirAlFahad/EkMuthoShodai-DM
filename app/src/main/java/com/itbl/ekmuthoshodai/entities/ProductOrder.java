package com.itbl.ekmuthoshodai.entities;

public class ProductOrder {

    private String odrId;
    private String odrName;
    private String odrQuantity;
    private String odrAmount;
    private String odrDate;
    private String dlvryId;
    private String odrStatus;

    public ProductOrder() {
    }

    public ProductOrder(String odrId, String odrName, String odrQuantity, String odrAmount, String odrDate,
                        String dlvryId, String odrStatus) {
        this.odrId = odrId;
        this.odrName = odrName;
        this.odrQuantity = odrQuantity;
        this.odrAmount = odrAmount;
        this.odrDate = odrDate;
        this.dlvryId = dlvryId;
        this.odrStatus = odrStatus;
    }

    public String getOdrId() {
        return odrId;
    }

    public void setOdrId(String odrId) {
        this.odrId = odrId;
    }

    public String getOdrName() {
        return odrName;
    }

    public void setOdrName(String odrName) {
        this.odrName = odrName;
    }

    public String getOdrQuantity() {
        return odrQuantity;
    }

    public void setOdrQuantity(String odrQuantity) {
        this.odrQuantity = odrQuantity;
    }

    public String getOdrAmount() {
        return odrAmount;
    }

    public void setOdrAmount(String odrAmount) {
        this.odrAmount = odrAmount;
    }

    public String getOdrDate() {
        return odrDate;
    }

    public void setOdrDate(String odrDate) {
        this.odrDate = odrDate;
    }

    public String getDlvryId() {
        return dlvryId;
    }

    public void setDlvryId(String dlvryId) {
        this.dlvryId = dlvryId;
    }

    public String getOdrStatus() {
        return odrStatus;
    }

    public void setOdrStatus(String odrStatus) {
        this.odrStatus = odrStatus;
    }

}
