package it.jedrzejewski.money2words.model;

import java.util.Locale;

public enum Language {
    POLISH("pl"),
    ENGLISH("en");
    
    private final String code;
    
    Language(String code) {
        this.code = code;
    }

    public static Language fromLocale(Locale locale) {
        if (locale == null) return POLISH;
        
        String lang = locale.getLanguage().toLowerCase(Locale.ROOT);
        return switch (lang) {
            case "en" -> ENGLISH;
            case "pl" -> POLISH;
            default -> POLISH;
        };
    }
}
