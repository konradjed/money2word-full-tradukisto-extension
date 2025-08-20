package it.jedrzejewski.money2words.conversion;

import it.jedrzejewski.money2words.model.Language;
import pl.allegro.finance.tradukisto.LongValueConverters;

public class NumberConverter {
    private final LongValueConverters converter;
    
    public NumberConverter(Language language) {
        this.converter = switch (language) {
            case ENGLISH -> LongValueConverters.ENGLISH_LONG;
            default -> LongValueConverters.POLISH_LONG;
        };
    }
    
    public String convertToWords(long number) {
        return converter.asWords(number);
    }
}
