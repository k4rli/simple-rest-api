package com.karli.response.types;

public enum CustomResponseCodes {
    SUCCESS("success"),
    ERROR("error");

    private String responseCode;

    CustomResponseCodes(String responseCode) {
        this.responseCode = responseCode;
    }
}
