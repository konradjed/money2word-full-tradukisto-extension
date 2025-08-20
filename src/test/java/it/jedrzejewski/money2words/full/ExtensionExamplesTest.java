package it.jedrzejewski.money2words.full;

import it.jedrzejewski.money2words.model.Currency;
import it.jedrzejewski.money2words.model.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstrates the extensibility of the refactored Money2Words system
 * with examples of foreign currencies in different languages
 */
class ExtensionExamplesTest {

    /* ---------- Polish names for foreign currencies ---------- */

    @Test
    @DisplayName("GBP in Polish: 'funt' and 'pens'")
    void gbpInPolish() {
        String result = Money2WordsFull.asWords(
            new BigDecimal("1.50"), 
            Currency.GBP, 
            Language.POLISH
        );
        assertEquals("jeden funt pięćdziesiąt pensów", result);
    }

    @Test
    @DisplayName("JPY in Polish: 'jen' (no minor units typically)")
    void jpyInPolish() {
        String result = Money2WordsFull.asWords(
            new BigDecimal("100.00"), 
            Currency.JPY, 
            Language.POLISH
        );
        assertEquals("sto jenów", result);
    }

    @Test
    @DisplayName("CHF in Polish: 'frank' and 'centym'")
    void chfInPolish() {
        String result = Money2WordsFull.asWords(
            new BigDecimal("2.75"), 
            Currency.CHF, 
            Language.POLISH
        );
        assertEquals("dwa franki siedemdziesiąt pięć centymów", result);
    }

    /* ---------- English names for foreign currencies ---------- */

    @Test
    @DisplayName("JPY in English: 'yen' (invariable)")
    void jpyInEnglish() {
        String result = Money2WordsFull.asWords(
            new BigDecimal("1000.00"), 
            Currency.JPY, 
            Language.ENGLISH
        );
        assertEquals("one thousand yen", result);
    }

    @Test
    @DisplayName("CHF in English: 'franc' and 'centime'")
    void chfInEnglish() {
        String result = Money2WordsFull.asWords(
            new BigDecimal("1.50"), 
            Currency.CHF, 
            Language.ENGLISH
        );
        assertEquals("one franc fifty centimes", result);
    }

    /* ---------- Backward compatibility with string API ---------- */

    @Test
    @DisplayName("String-based API still works with new currencies")
    void stringApiWithNewCurrencies() {
        String result = Money2WordsFull.asWords(
            new BigDecimal("5.25"), 
            "GBP", 
            new Locale("pl", "PL")
        );
        assertEquals("pięć funtów dwadzieścia pięć pensów", result);
    }

    /* ---------- Polish plural forms for foreign currencies ---------- */

    @ParameterizedTest(name = "GBP in Polish: {0} -> {1}")
    @CsvSource({
            "1.00,   jeden funt",
            "2.00,   dwa funty", 
            "5.00,   pięć funtów",
            "22.00,  dwadzieścia dwa funty",
            "100.00, sto funtów"
    })
    void gbpPolishPluralForms(String amount, String expected) {
        String result = Money2WordsFull.asWords(
            new BigDecimal(amount), 
            Currency.GBP, 
            Language.POLISH
        );
        assertEquals(expected, result);
    }

    @ParameterizedTest(name = "JPY in Polish: {0} -> {1}")
    @CsvSource({
            "1.00,   jeden jen",
            "2.00,   dwa jeny",
            "5.00,   pięć jenów", 
            "22.00,  dwadzieścia dwa jeny",
            "100.00, sto jenów"
    })
    void jpyPolishPluralForms(String amount, String expected) {
        String result = Money2WordsFull.asWords(
            new BigDecimal(amount), 
            Currency.JPY, 
            Language.POLISH
        );
        assertEquals(expected, result);
    }

    /* ---------- English plural forms ---------- */

    @ParameterizedTest(name = "GBP in English: {0} -> {1}")
    @CsvSource({
            "1.00, one pound",
            "2.00, two pounds",
            "5.00, five pounds"
    })
    void gbpEnglishPluralForms(String amount, String expected) {
        String result = Money2WordsFull.asWords(
            new BigDecimal(amount), 
            Currency.GBP, 
            Language.ENGLISH
        );
        assertEquals(expected, result);
    }

    /* ---------- Penny vs Pence in English ---------- */

    @Test
    @DisplayName("English GBP: 1 penny vs 2 pence")
    void gbpEnglishMinorUnits() {
        String onePenny = Money2WordsFull.asWords(
            new BigDecimal("0.01"), 
            Currency.GBP, 
            Language.ENGLISH
        );
        assertEquals("zero pounds one penny", onePenny);

        String twoPence = Money2WordsFull.asWords(
            new BigDecimal("0.02"), 
            Currency.GBP, 
            Language.ENGLISH
        );
        assertEquals("zero pounds two pence", twoPence);
    }

    /* ---------- Demonstration of extensibility ---------- */

    @Test
    @DisplayName("Comprehensive example showing multi-currency, multi-language support")
    void comprehensiveExample() {
        // Same amount in different currencies and languages
        BigDecimal amount = new BigDecimal("123.45");

        // PLN in Polish (original)
        String plnPl = Money2WordsFull.asWords(amount, Currency.PLN, Language.POLISH);
        assertEquals("sto dwadzieścia trzy złote czterdzieści pięć groszy", plnPl);

        // USD in English
        String usdEn = Money2WordsFull.asWords(amount, Currency.USD, Language.ENGLISH);
        assertEquals("one hundred twenty-three dollars forty-five cents", usdEn);

        // GBP in Polish (extension)
        String gbpPl = Money2WordsFull.asWords(amount, Currency.GBP, Language.POLISH);
        assertEquals("sto dwadzieścia trzy funty czterdzieści pięć pensów", gbpPl);

        // CHF in English (extension)
        String chfEn = Money2WordsFull.asWords(amount, Currency.CHF, Language.ENGLISH);
        assertEquals("one hundred twenty-three francs forty-five centimes", chfEn);

        // All different, showing proper localization
        assertNotEquals(plnPl, usdEn);
        assertNotEquals(gbpPl, chfEn);
        assertNotEquals(plnPl, gbpPl);
    }
}
