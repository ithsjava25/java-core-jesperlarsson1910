package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable{
    private final int warrantyMonths;
    private final BigDecimal weight;

    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }
        if (weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
        super(id, name, category, price);
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
    }

    @Override
    public String productDetails() {
        return "Electronics: " + name() + ", Warranty: " + warrantyMonths + " months";
    }

    @Override
    public double weight(){
        return weight.doubleValue();
    }

    @Override
    public BigDecimal calculateShippingCost() {
        if (weight.compareTo(BigDecimal.valueOf(5)) == -1) {
            return BigDecimal.valueOf(79);
        }
        else  {
            return BigDecimal.valueOf(128);
        }

    }
}