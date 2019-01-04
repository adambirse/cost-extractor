package com.birse.extractor.exception;

public class ScaleException extends NumberExtractionException {
    public ScaleException(String value) {
        super("Could not parse: " + value + " too many digits");
    }
}
