package it.jedrzejewski.money2words.full;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class Money2WordsFullTest {
    private static final Locale PL = new Locale("pl", "PL");
    private static final Locale EN = Locale.ENGLISH;

    /* ---------- Happy paths ---------- */

    @Test
    @DisplayName("PLN: full wording for integer and cents")
    void plnExample() {
        String s = Money2WordsFull.asWords(new BigDecimal("1234.56"), "PLN", PL);
        assertEquals("jeden tysiąc dwieście trzydzieści cztery złote pięćdziesiąt sześć groszy", s);
    }

    @Test
    @DisplayName("USD EN: full wording")
    void usdEnglish() {
        String s = Money2WordsFull.asWords(new BigDecimal("1.01"), "USD", EN);
        assertEquals("one dollar one cent", s);
    }

    @Test
    @DisplayName("EUR in Polish text")
    void eurPolish() {
        String s = Money2WordsFull.asWords(new BigDecimal("2.12"), "EUR", PL);
        assertEquals("dwa euro dwanaście centów", s);
    }

    /* ---------- Rounding & edge cases ---------- */

    @Test
    @DisplayName("Zero cents omitted")
    void zeroCentsOmitted() {
        String s = Money2WordsFull.asWords(new BigDecimal("0.00"), "PLN", PL);
        assertEquals("zero złotych", s);
    }

    @Test
    @DisplayName("Negative amount")
    void negativeAmount() {
        String s = Money2WordsFull.asWords(new BigDecimal("-12.30"), "PLN", PL);
        assertEquals("minus dwanaście złotych trzydzieści groszy", s);
    }

    @Test
    @DisplayName("HALF_UP rounding to 2 decimals: 1.005 -> 1.01")
    void roundingHalfUp_005() {
        String s = Money2WordsFull.asWords(new BigDecimal("1.005"), "PLN", PL);
        assertEquals("jeden złoty jeden grosz", s);
    }

    @Test
    @DisplayName("HALF_UP rounding to 2 decimals: 1.004 -> 1.00")
    void roundingHalfUp_004() {
        String s = Money2WordsFull.asWords(new BigDecimal("1.004"), "PLN", PL);
        assertEquals("jeden złoty", s);
    }

    /* ---------- Polish plural forms (units) ---------- */

    @ParameterizedTest(name = "PLN units: {0} -> {1}")
    @CsvSource({
            "1,   jeden złoty",
            "2,   dwa złote",
            "5,   pięć złotych",
            "12,  dwanaście złotych",
            "22,  dwadzieścia dwa złote",
            "111, sto jedenaście złotych"
    })
    void plnUnitsForms(String amount, String expectedPrefix) {
        String s = Money2WordsFull.asWords(new BigDecimal(amount + ".00"), "PLN", PL);
        assertEquals(expectedPrefix, s);
    }

    /* ---------- Polish plural forms (cents) ---------- */

    @ParameterizedTest(name = "PLN cents: {0} -> {1}")
    @CsvSource({
            "0.01, zero złotych jeden grosz",
            "0.02, zero złotych dwa grosze",
            "0.05, zero złotych pięć groszy",
            "0.12, zero złotych dwanaście groszy",
            "0.22, zero złotych dwadzieścia dwa grosze"
    })
    void plnCentsForms(String amount, String expected) {
        String s = Money2WordsFull.asWords(new BigDecimal(amount), "PLN", PL);
        assertEquals(expected, s);
    }

    /* ---------- Large numbers sanity ---------- */

    @Test
    @DisplayName("Large PLN number")
    void largeNumber() {
        String s = Money2WordsFull.asWords(new BigDecimal("1000000.00"), "PLN", PL);
        assertEquals("jeden milion złotych", s);
    }

    /* ---------- Null handling ---------- */

    @Test
    @DisplayName("Null amount -> null")
    void nullAmount() {
        String s = Money2WordsFull.asWords(null, "PLN", PL);
        assertNull(s);
    }
}
