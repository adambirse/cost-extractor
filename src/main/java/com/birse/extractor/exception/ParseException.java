package com.birse.extractor.exception;

public class ParseException extends NumberExtractionException {
    public ParseException(String word) {
        super("Could not parse: " + word + " It does not contain a valid number");
    }
}
