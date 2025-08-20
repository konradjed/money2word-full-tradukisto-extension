package it.jedrzejewski.money2words.localization;

public abstract class EnglishUnitNames implements UnitNames {

    private EnglishUnitNames(){}
    
    public static class UsdNames extends EnglishUnitNames {
        @Override
        public String getMainUnitName(long amount) {
            return amount == 1 ? "dollar" : "dollars";
        }
        
        @Override
        public String getMinorUnitName(long amount) {
            return amount == 1 ? "cent" : "cents";
        }
    }
    
    public static class GbpNames extends EnglishUnitNames {
        @Override
        public String getMainUnitName(long amount) {
            return amount == 1 ? "pound" : "pounds";
        }
        
        @Override
        public String getMinorUnitName(long amount) {
            return amount == 1 ? "penny" : "pence";
        }
    }
    
    public static class JpyNames extends EnglishUnitNames {
        @Override
        public String getMainUnitName(long amount) {
            return "yen"; // yen is invariable
        }
        
        @Override
        public String getMinorUnitName(long amount) {
            return "sen"; // rarely used
        }
    }
    
    public static class ChfNames extends EnglishUnitNames {
        @Override
        public String getMainUnitName(long amount) {
            return amount == 1 ? "franc" : "francs";
        }
        
        @Override
        public String getMinorUnitName(long amount) {
            return amount == 1 ? "centime" : "centimes";
        }
    }
}
