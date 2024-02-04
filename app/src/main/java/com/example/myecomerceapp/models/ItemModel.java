package com.example.myecomerceapp.models;

public class ItemModel {
    private final int itemImage;
    private final String itemName;
    private final String itemPrice;
    private final String itemId;

    public final String itemSpecs;

    public ItemModel(int itemImage, String itemName, String itemPrice,String itemSpecs,String itemId) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemId=itemId;
        this.itemSpecs=itemSpecs;
    }

    public String getItemSpecs() {
        return itemSpecs;
    }

    public String getItemId() {
        return itemId;
    }

    public int getItemImage() {
        return itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }
}
