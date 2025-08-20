package it.jedrzejewski.money2words.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyAmount {
    private final BigDecimal amount;
    private final boolean isNegative;
    private final long units;
    private final long cents;
    
    public MoneyAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        
        BigDecimal normalized = amount.setScale(2, RoundingMode.HALF_UP);
        this.isNegative = normalized.signum() < 0;
        this.amount = normalized.abs();
        
        this.units = this.amount.longValue();
        this.cents = this.amount.remainder(BigDecimal.ONE)
                .movePointRight(2).intValueExact();
    }

    public boolean isNegative() {
        return isNegative;
    }
    
    public long getUnits() {
        return units;
    }
    
    public long getCents() {
        return cents;
    }
    
    public boolean hasCents() {
        return cents > 0;
    }
}
