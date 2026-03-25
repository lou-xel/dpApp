package com.arrowcatering.model;

import java.math.BigDecimal;

public class Ingredient {
    private int ingredientId;
    private String name;
    private int stock;
    private BigDecimal unitCost;
    private int reorderLevel;

    public Ingredient() {}

    public Ingredient(int ingredientId, String name, int stock, BigDecimal unitCost, int reorderLevel) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.stock = stock;
        this.unitCost = unitCost;
        this.reorderLevel = reorderLevel;
    }

    // Getters and Setters
    public int getIngredientId() { return ingredientId; }
    public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }

    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }

    public boolean isLowStock() {
        return stock <= reorderLevel;
    }

    @Override
    public String toString() {
        return name + " (" + stock + " units)";
    }
}