package com.arrowcatering.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SupplierIngredient {
    private int supplierId;
    private int ingredientId;
    private BigDecimal price;
    private LocalDate leadTime;

    public SupplierIngredient() {}

    public SupplierIngredient(int supplierId, int ingredientId, BigDecimal price, LocalDate leadTime) {
        this.supplierId = supplierId;
        this.ingredientId = ingredientId;
        this.price = price;
        this.leadTime = leadTime;
    }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getLeadTime() { return leadTime; }
    public void setLeadTime(LocalDate leadTime) { this.leadTime = leadTime; }
}