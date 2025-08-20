package it.jedrzejewski.money2words.full;

import it.jedrzejewski.money2words.formatting.MoneyFormatter;
import it.jedrzejewski.money2words.model.Currency;
import it.jedrzejewski.money2words.model.Language;
import it.jedrzejewski.money2words.model.MoneyAmount;

import java.math.BigDecimal;
import java.util.Locale;

public class Money2WordsFull {

    private Money2WordsFull(){}

    public static String asWords(BigDecimal amount, String currency, Locale locale) {
        if (amount == null) {
            return null;
        }

        Currency currencyEnum = Currency.fromCode(currency);
        Language language = Language.fromLocale(locale);

        return asWords(amount, currencyEnum, language);
    }

    // Convenience methods for better API
    public static String asWords(BigDecimal amount, Currency currency, Language language) {
        if (amount == null) {
            return null;
        }

        MoneyAmount moneyAmount = new MoneyAmount(amount);
        MoneyFormatter formatter = new MoneyFormatter(currency, language);

        return formatter.format(moneyAmount);
    }

    public static String asWords(double amount, String currency, Locale locale) {
        return asWords(BigDecimal.valueOf(amount), currency, locale);
    }
}
