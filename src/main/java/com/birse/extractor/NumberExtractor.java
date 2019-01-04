package com.birse.extractor;

import com.birse.extractor.exception.NumberExtractionException;
import com.birse.extractor.exception.ParseException;
import com.birse.extractor.exception.ScaleException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class NumberExtractor {

    //TODO extend support for different digit separators . ,

    public List<BigDecimal> extract(String text) throws NumberExtractionException {

        List<BigDecimal> result = new ArrayList<>();

        if (text == null) {
            throw new IllegalArgumentException("A value must be provided");
        }

        if (!text.isEmpty()) {
            String[] words = tokenise(text);
            for (String word : words) {
                if (word.matches(".*\\d+.*")) {
                    result.add(extractFrom(word));
                }
            }
        }
        return result;
    }

    private String[] tokenise(String text) {
        return text.split(" ");
    }

    private BigDecimal extractFrom(String word) throws NumberExtractionException {

        try {
            BigDecimal number = new BigDecimal(word.replaceAll("[^.0123456789]", ""));
            return validate(word, number);
        } catch (NumberFormatException nfe) {
            throw new ParseException(word);
        }

    }

    private BigDecimal validate(String word, BigDecimal number) throws ScaleException {
        if (number.scale() <= 2) {
            return number;
        } else {
            throw new ScaleException(word);
        }
    }
}
