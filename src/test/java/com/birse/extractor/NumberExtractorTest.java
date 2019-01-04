package com.birse.extractor;

import com.birse.extractor.exception.NumberExtractionException;
import com.birse.extractor.exception.ParseException;
import com.birse.extractor.exception.ScaleException;
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
    public void testEmptyString() throws NumberExtractionException {
        assertThat(extractor.extract(""), is(empty()));
    }

    @Test
    public void testSimpleString() throws NumberExtractionException {
        verifyResults(extractor.extract("5.49"), BigDecimal.valueOf(5.49));
    }

    @Test
    public void testValueAsCurrency() throws NumberExtractionException {
        verifyResults(extractor.extract("£5.49"), BigDecimal.valueOf(5.49));
    }

    @Test
    public void testValueEmbeddedInText() throws NumberExtractionException {
        verifyResults(extractor.extract("Can you find the value £5.49, it is here somewhere."), BigDecimal.valueOf(5.49));
    }

    @Test
    public void testMultipleValuesEmbeddedInText() throws NumberExtractionException {
        verifyResults(extractor.extract("Can you find the value £5.49, it is here somewhere, but so am I 2.12"), BigDecimal.valueOf(5.49), BigDecimal.valueOf(2.12));
    }


    @Test
    public void testMultipleValuesInMultipleLines() throws NumberExtractionException {
        verifyResults(extractor.extract("Can you find the value £5.49, /n it is here somewhere, but so am I 2.12 /n Can you catch us all $4.32"),
                BigDecimal.valueOf(5.49),
                BigDecimal.valueOf(2.12),
                BigDecimal.valueOf(4.32));
    }

    @Test(expected = ParseException.class)
    public void testMalformedNumber() throws NumberExtractionException {
        extractor.extract("I am an invalid number 5.23.45");
    }

    @Test(expected = ParseException.class)
    @Ignore("Broken test code needs fixing.")
    public void testMalformedNumberTwo() throws NumberExtractionException {
        extractor.extract("555abc45");
    }

    @Test()
    public void testWholeNumber() throws NumberExtractionException {
        verifyResults(extractor.extract("55545"), BigDecimal.valueOf(55545));
    }

    @Test(expected = ScaleException.class)
    public void testTooManyNumbersAfterDecimalPlace() throws NumberExtractionException {
        extractor.extract("I am an invalid number 5.232343254");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameter() throws NumberExtractionException {
        extractor.extract(null);
    }

    private void verifyResults(List<BigDecimal> actual, BigDecimal... expected) {
        assertThat(actual, contains(expected));
    }

}