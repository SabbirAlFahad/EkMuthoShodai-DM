package com.itbl.ekmuthoshodai.entities;

public class ProductOrder {
    private String imCID;
    private String imClient;

    public ProductOrder(String imCID, String imClient) {
        this.imCID = imCID;
        this.imClient = imClient;
    }

    public String getImCID() {
        return imCID;
    }

    public void setImCID(String imCID) {
        this.imCID = imCID;
    }

    public String getImClient() {
        return imClient;
    }

    public void setImClient(String imClient) {
        this.imClient = imClient;
    }
}
