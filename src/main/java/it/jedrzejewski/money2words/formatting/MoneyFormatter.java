package it.jedrzejewski.money2words.formatting;

import it.jedrzejewski.money2words.conversion.NumberConverter;
import it.jedrzejewski.money2words.localization.UnitNames;
import it.jedrzejewski.money2words.localization.UnitNamesFactory;
import it.jedrzejewski.money2words.model.Currency;
import it.jedrzejewski.money2words.model.Language;
import it.jedrzejewski.money2words.model.MoneyAmount;

public class MoneyFormatter {
    private final NumberConverter numberConverter;
    private final UnitNames unitNames;
    
    public MoneyFormatter(Currency currency, Language language) {
        this.numberConverter = new NumberConverter(language);
        this.unitNames = UnitNamesFactory.getUnitNames(currency, language);
    }
    
    public String format(MoneyAmount moneyAmount) {
        if (moneyAmount == null) {
            return null;
        }
        
        String unitsWords = numberConverter.convertToWords(moneyAmount.getUnits());
        String mainUnit = unitNames.getMainUnitName(moneyAmount.getUnits());
        
        StringBuilder result = new StringBuilder();
        
        if (moneyAmount.isNegative()) {
            result.append("minus ");
        }
        
        result.append(unitsWords).append(" ").append(mainUnit);
        
        if (moneyAmount.hasCents()) {
            String centsWords = numberConverter.convertToWords(moneyAmount.getCents());
            String minorUnit = unitNames.getMinorUnitName(moneyAmount.getCents());
            result.append(" ").append(centsWords).append(" ").append(minorUnit);
        }
        
        return result.toString().trim();
    }
}
