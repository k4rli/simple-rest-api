package com.karli.response.types;

public enum CustomResponseCodes {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private String code;

    CustomResponseCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
