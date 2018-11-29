package com.karli.exceptions.missingParameter;

public class MissingParameterException extends RuntimeException {
    public MissingParameterException(String[] missingParameter) {
        super("Request has missing parameters: " + String.join(",", missingParameter));
    }

    public MissingParameterException(String missingParameter) {
        super("Request has missing parameter: " + missingParameter);
    }
}
