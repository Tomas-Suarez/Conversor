package com.clase.conversor;

import java.math.BigDecimal;

public class CurrencyModel {
    private String name;
    private BigDecimal value;
    private String symbol;

    public CurrencyModel(String name, BigDecimal value, String symbol) {
        this.name = name;
        this.value = value;
        this.symbol = symbol;
    }

    public CurrencyModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
