package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class ProductQuantityException extends Exception{

    private final Messages message;

    public ProductQuantityException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
