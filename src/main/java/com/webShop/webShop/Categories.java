package com.webShop.webShop;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Categories {

    CATEGORY1("A"),
    CATEGORY2("B");

    private final String category;

    Categories(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @JsonCreator
    public static Categories convert(String category) {
        for (Categories c : Categories.values()) {
            if (c.getCategory().equals(category)) {
                return c;
            }
        }
        throw new IllegalArgumentException();
    }
}
