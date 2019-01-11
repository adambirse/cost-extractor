package com.birse.extractor.exception;

public class ParseException extends NumberExtractionException {
    public ParseException(String word) {
        super("Could not parse: " + word + " it does not contain a valid number");
    }
}
