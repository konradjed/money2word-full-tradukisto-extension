package it.jedrzejewski.money2words.localization;

import it.jedrzejewski.money2words.model.Currency;
import it.jedrzejewski.money2words.model.Language;

import java.util.Map;

public class UnitNamesFactory {

    private UnitNamesFactory(){}

    private static final Map<Language, Map<Currency, UnitNames>> UNIT_NAMES = Map.of(
        Language.POLISH, Map.of(
            Currency.PLN, new PolishUnitNames.PlnNames(),
            Currency.EUR, new PolishUnitNames.EurNames(),
            Currency.USD, new PolishUnitNames.UsdNames()
        ),
        Language.ENGLISH, Map.of(
            Currency.USD, new EnglishUnitNames.UsdNames()
        )
    );
    
    public static UnitNames getUnitNames(Currency currency, Language language) {
        Map<Currency, UnitNames> currencyMap = UNIT_NAMES.get(language);
        if (currencyMap != null) {
            UnitNames unitNames = currencyMap.get(currency);
            if (unitNames != null) {
                return unitNames;
            }
        }
        
        // Fallback logic
        return getFallbackUnitNames(language);
    }
    
    private static UnitNames getFallbackUnitNames( Language language) {
        if (language == it.jedrzejewski.money2words.model.Language.ENGLISH) {
            return new EnglishUnitNames.UsdNames(); // Default to USD for English
        } else {
            return new PolishUnitNames.PlnNames(); // Default to PLN for Polish
        }
    }
}
