package com.webShop.webShop.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDTO {

    private final String errorCode;

    private final String errorMessage;

    public ErrorDTO(
            @JsonProperty("errorCode") String errorCode,
            @JsonProperty("errorMsg") String errorMsg) {
        this.errorCode = errorCode;
        this.errorMessage = errorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
