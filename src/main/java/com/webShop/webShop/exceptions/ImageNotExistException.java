package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class ImageNotExistException extends Exception{

    private final Messages message;

    public ImageNotExistException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
