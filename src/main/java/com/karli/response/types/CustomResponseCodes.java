package com.karli.response.types;

public enum CustomResponseCodes {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private String responseCode;

    CustomResponseCodes(String responseCode) {
        this.responseCode = responseCode;
    }
}
