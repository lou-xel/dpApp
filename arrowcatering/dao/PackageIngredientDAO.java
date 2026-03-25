package com.arrowcatering.dao;

import java.sql.*;
import java.util.*;

public class PackageIngredientDAO {

    // Get ingredients needed for a package
    public Map<Integer, Integer> getIngredientsForPackage(int packageId) {
        Map<Integer, Integer> ingredients = new HashMap<>();
        String sql = "SELECT ingredient_ID, quantity_required FROM package_ingredient WHERE package_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, packageId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ingredients.put(rs.getInt("ingredient_ID"), rs.getInt("quantity_required"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    // Check if all ingredients are available for a package with guest count
    public boolean checkAvailability(int packageId, int guestCount) {
        Map<Integer, Integer> required = getIngredientsForPackage(packageId);
        if (required.isEmpty()) return true;

        String sql = "SELECT ingredientID, stock FROM ingredient WHERE ingredientID IN (" +
                String.join(",", Collections.nCopies(required.size(), "?")) + ")";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (int id : required.keySet()) {
                pstmt.setInt(index++, id);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ingredientID");
                int stock = rs.getInt("stock");
                int needed = required.get(id) * guestCount;
                if (stock < needed) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Deduct ingredients for a package with guest count
    public boolean deductIngredients(int packageId, int guestCount) {
        Map<Integer, Integer> required = getIngredientsForPackage(packageId);
        if (required.isEmpty()) return true;

        IngredientDAO ingredientDAO = new IngredientDAO();
        for (Map.Entry<Integer, Integer> entry : required.entrySet()) {
            int needed = entry.getValue() * guestCount;
            if (!ingredientDAO.deductStock(entry.getKey(), needed)) {
                return false;
            }
        }
        return true;
    }
}