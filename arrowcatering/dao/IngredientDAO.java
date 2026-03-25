package com.arrowcatering.dao;

import com.arrowcatering.model.Ingredient;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {

    // Add new ingredient
    public boolean addIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO ingredient (ingredientID, name, stock, unitcost, reorderlevel) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ingredient.getIngredientId());
            pstmt.setString(2, ingredient.getName());
            pstmt.setInt(3, ingredient.getStock());
            pstmt.setBigDecimal(4, ingredient.getUnitCost());
            pstmt.setInt(5, ingredient.getReorderLevel());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all ingredients
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT * FROM ingredient ORDER BY ingredientID";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ingredient i = new Ingredient();
                i.setIngredientId(rs.getInt("ingredientID"));
                i.setName(rs.getString("name"));
                i.setStock(rs.getInt("stock"));
                i.setUnitCost(rs.getBigDecimal("unitcost"));
                i.setReorderLevel(rs.getInt("reorderlevel"));
                list.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update ingredient stock
    public boolean updateStock(int ingredientId, int newStock) {
        String sql = "UPDATE ingredient SET stock = ? WHERE ingredientID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, ingredientId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update ingredient details
    public boolean updateIngredient(Ingredient ingredient) {
        String sql = "UPDATE ingredient SET name = ?, unitcost = ?, reorderlevel = ? WHERE ingredientID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ingredient.getName());
            pstmt.setBigDecimal(2, ingredient.getUnitCost());
            pstmt.setInt(3, ingredient.getReorderLevel());
            pstmt.setInt(4, ingredient.getIngredientId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete ingredient
    public boolean deleteIngredient(int ingredientId) {
        String sql = "DELETE FROM ingredient WHERE ingredientID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ingredientId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get low stock ingredients
    public List<Ingredient> getLowStockIngredients() {
        List<Ingredient> list = new ArrayList<>();
        String sql = "SELECT * FROM ingredient WHERE stock <= reorderlevel";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ingredient i = new Ingredient();
                i.setIngredientId(rs.getInt("ingredientID"));
                i.setName(rs.getString("name"));
                i.setStock(rs.getInt("stock"));
                i.setUnitCost(rs.getBigDecimal("unitcost"));
                i.setReorderLevel(rs.getInt("reorderlevel"));
                list.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Deduct ingredient stock (for menu preparation)
    public boolean deductStock(int ingredientId, int quantity) {
        String sql = "UPDATE ingredient SET stock = stock - ? WHERE ingredientID = ? AND stock >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, ingredientId);
            pstmt.setInt(3, quantity);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add stock (for restocking)
    public boolean addStock(int ingredientId, int quantity) {
        String sql = "UPDATE ingredient SET stock = stock + ? WHERE ingredientID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, ingredientId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}