package com.birse.extractor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NumberExtractorTest {


    @Before
    public void setup() {
        extractor = new NumberExtractor();
    }

    private NumberExtractor extractor;

    @Test
    public void testEmptyString() {
        assertThat(extractor.extract(""), is(empty()));
    }

    @Test
    public void testSimpleString() {
        verifyResults(extractor.extract("5.49"), BigDecimal.valueOf(5.49));
    }

    @Test
    public void testValueAsCurrency() {
        verifyResults(extractor.extract("£5.49"), BigDecimal.valueOf(5.49));
    }

    @Test
    public void testValueEmbeddedInText() {
        verifyResults(extractor.extract("Can you find the value £5.49, it is here somewhere."), BigDecimal.valueOf(5.49));
    }

    @Test
    public void testMultipleValuesEmbeddedInText() {
        verifyResults(extractor.extract("Can you find the value £5.49, it is here somewhere, but so am I 2.12"), BigDecimal.valueOf(5.49), BigDecimal.valueOf(2.12));
    }


    @Test
    public void testMultipleLinesWithSpace() {
        verifyResults(extractor.extract("foo\n" +
                        "bar\n" +
                        "\n" +
                        "1.21"),
                BigDecimal.valueOf(1.21));
    }

    @Test()
    public void testMalformedNumber() {
        extractor.extract("I am an invalid number 5.23.45");
    }

    @Test()
    public void testMalformedNumberTwo() {
        extractor.extract("555abc45");
    }

    @Test()
    public void testWholeNumber() {
        verifyResults(extractor.extract("55545"), BigDecimal.valueOf(55545));
    }

    @Test()
    public void testTooManyNumbersAfterDecimalPlace() {
        extractor.extract("I am an invalid number 5.232343254");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameter() {
        extractor.extract(null);
    }

    private void verifyResults(List<BigDecimal> actual, BigDecimal... expected) {
        assertThat(actual, contains(expected));
    }

}