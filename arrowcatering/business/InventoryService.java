package com.arrowcatering.business;

import com.arrowcatering.dao.*;
import com.arrowcatering.model.*;
import java.util.List;

public class InventoryService {
    private IngredientDAO ingredientDAO;
    private SupplierDAO supplierDAO;
    private PackageIngredientDAO packageIngredientDAO;

    public InventoryService() {
        this.ingredientDAO = new IngredientDAO();
        this.supplierDAO = new SupplierDAO();
        this.packageIngredientDAO = new PackageIngredientDAO();
    }

    // Ingredient Management
    public boolean addIngredient(Ingredient ingredient) {
        if (ingredient.getIngredientId() <= 0) return false;
        if (ingredient.getName() == null || ingredient.getName().trim().isEmpty()) return false;
        return ingredientDAO.addIngredient(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }

    public boolean updateIngredient(Ingredient ingredient) {
        return ingredientDAO.updateIngredient(ingredient);
    }

    public boolean deleteIngredient(int ingredientId) {
        return ingredientDAO.deleteIngredient(ingredientId);
    }

    public List<Ingredient> getLowStockIngredients() {
        return ingredientDAO.getLowStockIngredients();
    }

    // Stock Operations
    public boolean addStock(int ingredientId, int quantity) {
        return ingredientDAO.addStock(ingredientId, quantity);
    }

    public boolean deductStock(int ingredientId, int quantity) {
        return ingredientDAO.deductStock(ingredientId, quantity);
    }

    // Supplier Management
    public boolean addSupplier(Supplier supplier) {
        if (supplier.getSupplierId() <= 0) return false;
        if (supplier.getSupplierName() == null || supplier.getSupplierName().trim().isEmpty()) return false;
        return supplierDAO.addSupplier(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }

    public boolean updateSupplier(Supplier supplier) {
        return supplierDAO.updateSupplier(supplier);
    }

    public boolean deleteSupplier(int supplierId) {
        return supplierDAO.deleteSupplier(supplierId);
    }

    // Menu Operations
    public boolean checkPackageAvailability(int packageId, int guestCount) {
        return packageIngredientDAO.checkAvailability(packageId, guestCount);
    }

    public boolean deductPackageIngredients(int packageId, int guestCount) {
        return packageIngredientDAO.deductIngredients(packageId, guestCount);
    }
}