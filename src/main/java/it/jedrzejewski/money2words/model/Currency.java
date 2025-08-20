package it.jedrzejewski.money2words.model;

public enum Currency {
    PLN("PLN"),
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    JPY("JPY"),
    CHF("CHF");
    
    private final String code;
    
    Currency(String code) {
        this.code = code;
    }
    
    public static Currency fromCode(String code) {
        if (code == null) return PLN;
        
        for (Currency currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }
        return PLN; // default fallback
    }
}
