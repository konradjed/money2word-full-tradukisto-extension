package it.jedrzejewski.money2words.full;

import pl.allegro.finance.tradukisto.LongValueConverters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Map;

public class Money2WordsFull {
    public static String asWords(BigDecimal amount, String currency, Locale locale) {
        if (amount == null) return null;
        currency = (currency == null ? "PLN" : currency.toUpperCase(Locale.ROOT));
        String lang = (locale == null ? "pl" : locale.getLanguage().toLowerCase(Locale.ROOT));

        // pick integer converters for chosen language
        LongValueConverters conv = switch (lang) {
            case "pl" -> LongValueConverters.POLISH_LONG;
            case "en" -> LongValueConverters.ENGLISH_LONG;
            default -> LongValueConverters.POLISH_LONG;
        };

        // normalize: max 2 fractional digits (Tradukisto supports money with 2 decimals)
        BigDecimal normalized = amount.setScale(2, RoundingMode.HALF_UP);
        boolean neg = normalized.signum() < 0;
        normalized = normalized.abs();

        long units = normalized.longValue();                       // złoty / dollars
        long cents = normalized.remainder(BigDecimal.ONE)
                .movePointRight(2).intValueExact();  // grosz / cents

        String unitsWords = conv.asWords(units);
        String centsWords = conv.asWords(cents);

        UnitNames names = namesFor(currency, lang);

        String full = (neg ? "minus" : "")
                + unitsWords + " " + names.main(units)
                + (cents == 0 ? "" : " " + centsWords + " " + names.minor(cents));

        return full.trim();
    }

    /* ---------- currency names & plural rules ---------- */

    private interface UnitNames {
        String main(long n);
        String minor(long n);
    }

    private static final UnitNames PLN_PL = new UnitNames() {
        @Override public String main(long n)  { return plForm(n, "złoty", "złote", "złotych"); }
        @Override public String minor(long n) { return plForm(n, "grosz", "grosze", "groszy"); }
    };

    private static final UnitNames USD_EN = new UnitNames() {
        @Override public String main(long n)  { return n == 1 ? "dollar" : "dollars"; }
        @Override public String minor(long n) { return n == 1 ? "cent" : "cents"; }
    };

    private static final UnitNames EUR_PL = new UnitNames() { // Polish text for EUR
        @Override public String main(long n)  { return "euro"; } // invariable in Polish
        @Override public String minor(long n) { return plForm(n, "cent", "centy", "centów"); }
    };

    private static UnitNames namesFor(String currency, String lang) {
        // extend as you need
        Map<String, UnitNames> map = switch (lang) {
            case "pl" -> Map.of(
                    "PLN", PLN_PL,
                    "EUR", EUR_PL,
                    "USD", new UnitNames() { // Polish names for USD if needed
                        @Override public String main(long n)  { return plForm(n, "dolar", "dolary", "dolarów"); }
                        @Override public String minor(long n) { return plForm(n, "cent", "centy", "centów"); }
                    }
            );
            case "en" -> Map.of("USD", USD_EN);
            default -> Map.of("PLN", PLN_PL);
        };
        return map.getOrDefault(currency, lang.equals("en") ? USD_EN : PLN_PL);
    }

    /** Polish plural forms: 1 → singular; 2–4 (except 12–14) → few; others → many */
    private static String plForm(long n, String singular, String few, String many) {
        long mod10 = n % 10;
        long mod100 = n % 100;
        if (n == 1) return singular;
        if (mod10 >= 2 && mod10 <= 4 && (mod100 < 12 || mod100 > 14)) return few;
        return many; // includes 0
    }
}