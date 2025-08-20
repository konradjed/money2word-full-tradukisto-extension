package it.jedrzejewski.money2words.localization;

public abstract class PolishUnitNames implements UnitNames {
    
    protected String getPolishPluralForm(long n, String singular, String few, String many) {
        long mod10 = n % 10;
        long mod100 = n % 100;
        
        if (n == 1) return singular;
        if (mod10 >= 2 && mod10 <= 4 && (mod100 < 12 || mod100 > 14)) return few;
        return many; // includes 0
    }
    
    public static class PlnNames extends PolishUnitNames {
        @Override
        public String getMainUnitName(long amount) {
            return getPolishPluralForm(amount, "złoty", "złote", "złotych");
        }
        
        @Override
        public String getMinorUnitName(long amount) {
            return getPolishPluralForm(amount, "grosz", "grosze", "groszy");
        }
    }
    
    public static class EurNames extends PolishUnitNames {
        @Override
        public String getMainUnitName(long amount) {
            return "euro"; // invariable in Polish
        }
        
        @Override
        public String getMinorUnitName(long amount) {
            return getPolishPluralForm(amount, "cent", "centy", "centów");
        }
    }
    
    public static class UsdNames extends PolishUnitNames {
        @Override
        public String getMainUnitName(long amount) {
            return getPolishPluralForm(amount, "dolar", "dolary", "dolarów");
        }
        
        @Override
        public String getMinorUnitName(long amount) {
            return getPolishPluralForm(amount, "cent", "centy", "centów");
        }
    }
}
