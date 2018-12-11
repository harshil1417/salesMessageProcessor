package com.harshil.salesnotificationprocessor.model;


import java.math.BigDecimal;

public class AdjustmentSale {
    private AdjustmentOperators operator;
    private String productName;
    private BigDecimal amount;

    public AdjustmentSale(AdjustmentOperators type, String productName, BigDecimal amount) {
        this.operator = type;
        this.productName = productName;
        this.amount = amount;
    }

    public AdjustmentOperators getType() {
        return operator;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdjustmentSale that = (AdjustmentSale) o;

        if (operator != that.operator) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        return amount != null ? amount.equals(that.amount) : that.amount == null;
    }

    @Override
    public int hashCode() {
        int result = operator != null ? operator.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Adjustment{" +
                "type=" + operator +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
