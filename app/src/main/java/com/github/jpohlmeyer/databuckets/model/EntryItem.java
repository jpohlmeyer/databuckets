package com.github.jpohlmeyer.databuckets.model;

public class EntryItem {

    private ItemType itemType;
    private String itemDescription;
    private String itemValue;

    public EntryItem(ItemType itemType, String itemDescription, String itemValue) {
        this.itemType = itemType;
        this.itemDescription = itemDescription;
        this.itemValue = itemValue;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
