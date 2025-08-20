# Money In Words - Full (Tradukisto wrapper)

Below is a small wrapper that uses Tradukisto’s integer converters for both parts and adds country-specific minor unit names (e.g., Polish _grosz/grosze/groszy_, English _cent/cents_).

> **Why not just use Tradukisto `MoneyConverters`?**  
> Tradukisto’s built-in money converters are “banking style”: they spell the integer part in words, but the fractional part is rendered as xx/100 (e.g., … PLN 56/100). So you won’t get “grosze/cents” as words out of the box. Many business documents (invoices, contracts, payment orders) require the **fractional part in words** and **correct plural forms by locale**. This small library fills that gap.

---

## Business requirement this solves

You can use this wrapper in organizations issuing Polish and international documents require that:

1. **The integer part is written in words** according to the document language.
2. **The fractional part is written in words** with **proper minor currency unit** names and **grammar** (e.g., `grosz/grosze/groszy` in PL, `cent/cents` in EN).
3. **Pluralization rules are correct** per locale (e.g., Polish `2–4` vs. `12–14`).
4. **Negative amounts** are supported (e.g., “minus …”).
5. **Rounding to 2 decimal places (banking)** is applied consistently.
6. **Custom currencies** can be added without changing the core number-to-words logic.

Tradukisto already provides excellent number wording across multiple languages; we extend it with **currency minor units** and **locale-aware plural forms**.

---

## Features

- Integer wording powered by Tradukisto `ValueConverters` (Here we used PL, EN. but it could be easly extended by DE, FR, ES, …).
- Fractional part as **words** (not `xx/100`).
- Locale-specific pluralization (Polish included).
- Negative values, zero values, and rounding to 2 decimals.
- Easily extensible currency → unit names mapping.
- Small, dependency-light API.

---

## Requirements

- Java 11+
- Maven/Gradle project with:
    - `pl.allegro.finance:tradukisto`
    - (Your wrapper module/classes)


## Quick start

```java
import java.math.BigDecimal;
import java.util.Locale;


Locale polish = Locale.of("pl", "PL");
// PLN in Polish
String pl = Money2WordsFull.asWords(new BigDecimal("1234.56"), "PLN", polish);
// → "jeden tysiąc dwieście trzydzieści cztery złote pięćdziesiąt sześć groszy"

// USD in English
String en = Money2WordsFull.asWords(new BigDecimal("1.01"), "USD", Locale.ENGLISH);
// → "one dollar one cent"

// EUR in Polish
String eurPl = Money2WordsFull.asWords(new BigDecimal("2.12"), "EUR", polish);
// → "dwa euro dwanaście centów"

// Negative amount
String neg = Money2WordsFull.asWords(new BigDecimal("-10.00"), "PLN", polish);
// → "minus dziesięć złotych"

```


## Extensibility Examples

### Adding a New Currency (GBP)



1. **Add to Currency enum**:
```java
GBP("GBP")
```

2. **Create unit names class**:
> Library supports also currency localization so it could be extended to transform money to words exactly how You speak in Your Country. Example "pounds" in Polish language refers British currency by speak "funty".

```java
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
```

3. **Register in factory `UnitNamesFactory`**:
```java
Currency.GBP, new EnglishUnitNames.GbpNames()
```

### Adding a New Language (German)

1. **Add to Language enum**:
```java
GERMAN("de")
```

2. **Create unit names classes**:
```java
public abstract class GermanUnitNames implements UnitNames {
    // German-specific plural logic
}
```

3. **Add to `NumberConverter`**:
```java
case GERMAN -> LongValueConverters.GERMAN_LONG;
```
4. **Register in factory `UnitNamesFactory`**:
```java

Language.GERMAN, Map.of(
    Currency.EUR, new GermanUnitNames.EurNames()
)
```