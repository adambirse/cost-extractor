package com.birse.extractor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO extend support for different digit separators . ,

public class NumberExtractor {


    public List<BigDecimal> extract(String text) {

        List<BigDecimal> filteredList = new ArrayList<>();

        if (text == null) {
            throw new IllegalArgumentException("A value must be provided.");
        }

        if (!text.isEmpty()) {
            String lines[] = text.split("\\r?\\n");

            Arrays.stream(lines).forEach(line -> {
                filteredList.addAll(processLine(line));
            });

        }
        return filteredList;
    }

    List<BigDecimal> processLine(String line) {

        String[] words = tokenise(line);

        return Arrays.stream(words).filter(word -> word.matches(".*\\d+.*"))
                .map(this::extractNumber)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


    private String[] tokenise(String text) {
        return text.split(" ");
    }

    private Optional<BigDecimal> extractNumber(String word) {
        try {
            BigDecimal number = new BigDecimal(word.replaceAll("[^.0123456789]", ""));
            return number.scale() <= 2 ? Optional.of(number) : Optional.empty();
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
    }
}
