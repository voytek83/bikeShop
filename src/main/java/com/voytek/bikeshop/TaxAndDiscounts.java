package com.voytek.bikeshop;

public class TaxAndDiscounts {

    private final static int taxRate = 23;
    private final static int returningClientDiscount = 10;

    public static int getTaxRate() {
        return taxRate;
    }

    public static int getReturningClientDiscount() {
        return returningClientDiscount;
    }
}
