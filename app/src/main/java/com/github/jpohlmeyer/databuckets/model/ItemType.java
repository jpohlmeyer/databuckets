package com.github.jpohlmeyer.databuckets.model;

public enum ItemType {
    TEXT, NUMBER;

    public static ItemType toItemType(String type) {
        if (type.toUpperCase().equals("NUMBER")) {
            return NUMBER;
        } else {
            return TEXT;
        }
    }
}
