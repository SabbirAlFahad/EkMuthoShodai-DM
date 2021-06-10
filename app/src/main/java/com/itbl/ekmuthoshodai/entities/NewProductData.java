package com.itbl.ekmuthoshodai.entities;

public class NewProductData{

    String iNameSpin2;
    String cDateV;
    String createdByV;
    String iDisV;
    String iStockV;
    String iTAmountV;
    String iRateV;
    String iAmountV;
    String iQuantityV;

    public NewProductData(){
    }

    public NewProductData(String iNameSpin2, String cDateV, String createdByV,
                          String iDisV, String iStockV, String iTAmountV, String iRateV,
                          String iAmountV, String iQuantityV){

        this.iNameSpin2 = iNameSpin2;
        this.cDateV = cDateV;
        this.createdByV = createdByV;
        this.iDisV = iDisV;
        this.iStockV = iStockV;
        this.iTAmountV = iTAmountV;
        this.iRateV = iRateV;
        this.iAmountV = iAmountV;
        this.iQuantityV = iQuantityV;
    }

    public String getiNameSpin2() {
        return iNameSpin2;
    }

    public void setiNameSpin2(String iNameSpin2) {
        this.iNameSpin2 = iNameSpin2;
    }

    public String getcDateV() {
        return cDateV;
    }

    public void setcDateV(String cDateV) {
        this.cDateV = cDateV;
    }

    public String getCreatedByV() {
        return createdByV;
    }

    public void setCreatedByV(String createdByV) {
        this.createdByV = createdByV;
    }

    public String getiDisV() {
        return iDisV;
    }

    public void setiDisV(String iDisV) {
        this.iDisV = iDisV;
    }

    public String getiStockV() {
        return iStockV;
    }

    public void setiStockV(String iStockV) {
        this.iStockV = iStockV;
    }

    public String getiTAmountV() {
        return iTAmountV;
    }

    public void setiTAmountV(String iTAmountV) {
        this.iTAmountV = iTAmountV;
    }

    public String getiRateV() {
        return iRateV;
    }

    public void setiRateV(String iRateV) {
        this.iRateV = iRateV;
    }

    public String getiAmountV() {
        return iAmountV;
    }

    public void setiAmountV(String iAmountV) {
        this.iAmountV = iAmountV;
    }

    public String getiQuantityV() {
        return iQuantityV;
    }

    public void setiQuantityV(String iQuantityV) {
        this.iQuantityV = iQuantityV;
    }
}
