package com.itbl.ekmuthoshodai.entities;

public class ProductOrder {
    private Integer imCID;
    private String imClient;

    public ProductOrder(Integer imCID, String imClient) {
        this.imCID = imCID;
        this.imClient = imClient;
    }

    public Integer getImCID() {
        return imCID;
    }

    public void setImCID(Integer imCID) {
        this.imCID = imCID;
    }

    public String getImClient() {
        return imClient;
    }

    public void setImClient(String imClient) {
        this.imClient = imClient;
    }
}
