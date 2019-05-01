package com.karli.response.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CustomResponseCodes {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    @Getter
    private String code;
}
