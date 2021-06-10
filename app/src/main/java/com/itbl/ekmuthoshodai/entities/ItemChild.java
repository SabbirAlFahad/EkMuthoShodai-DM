package com.itbl.ekmuthoshodai.entities;

public class ItemChild {

    private int itemID;
    private int itemCId;
    private String itemCName;

    public ItemChild() {
    }

    public ItemChild(int itemID, int itemCId, String itemCName) {
        this.itemID = itemID;
        this.itemCId = itemCId;
        this.itemCName = itemCName;
    }

    public ItemChild(int itemCId, String itemCName) {
        this.itemCId = itemCId;
        this.itemCName = itemCName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getItemCId() {
        return itemCId;
    }

    public void setItemCId(int itemCId) {
        this.itemCId = itemCId;
    }

    public String getItemCName() {
        return itemCName;
    }

    public void setItemCName(String itemCName) {
        this.itemCName = itemCName;
    }
}
