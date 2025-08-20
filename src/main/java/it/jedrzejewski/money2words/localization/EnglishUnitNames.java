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
}
