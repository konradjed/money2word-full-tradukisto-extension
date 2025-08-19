# Money In Words (Tradukisto wrapper)

Convert monetary amounts into **fully verbalized phrases** (integer **and** fractional part) using [Tradukisto] numbers for words, plus **country-specific minor unit names** (e.g. Polish _grosz/grosze/groszy_, English _cent/cents_).

> **Why not just use Tradukisto `MoneyConverters`?**  
> Out of the box, Tradukisto’s money converters render the fractional part as a fraction (`56/100`). Many business documents (invoices, contracts, payment orders) require the **fractional part in words** and **correct plural forms by locale**. This small library fills that gap.

---

## Business requirement this solves

Organizations issuing Polish and international documents require that:

1. **The integer part is written in words** according to the document language.
2. **The fractional part is written in words** with **proper minor currency unit** names and **grammar** (e.g., `grosz/grosze/groszy` in PL, `cent/cents` in EN).
3. **Pluralization rules are correct** per locale (e.g., Polish `2–4` vs. `12–14`).
4. **Negative amounts** are supported (e.g., “minus …”).
5. **Rounding to 2 decimal places (banking)** is applied consistently.
6. **Custom currencies** can be added without changing the core number-to-words logic.

Tradukisto already provides excellent number wording across multiple languages; we extend it with **currency minor units** and **locale-aware plural forms**.

---

## Features

- Integer wording powered by Tradukisto `ValueConverters` (PL, EN, DE, FR, ES, …).
- Fractional part as **words** (not `xx/100`).
- Locale-specific pluralization (Polish included).
- Negative values, zero values, and rounding to 2 decimals.
- Easily extensible currency → unit names mapping.
- Small, dependency-light API.

---

## Requirements

- Java 19+
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
