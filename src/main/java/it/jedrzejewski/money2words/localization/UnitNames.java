package it.jedrzejewski.money2words.localization;

public interface UnitNames {
    String getMainUnitName(long amount);
    String getMinorUnitName(long amount);
}
