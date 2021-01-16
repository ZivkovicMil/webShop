package com.webShop.webShop.exceptions;

import com.webShop.webShop.Messages;

public class RoleException extends Exception{

    private final Messages message;

    public RoleException(Messages message) {
        super();
        this.message = message;
    }

    public Messages getMessages() {
        return message;
    }
}
