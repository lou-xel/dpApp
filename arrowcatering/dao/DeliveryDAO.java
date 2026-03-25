package com.arrowcatering.dao;

import java.sql.*;

public class DeliveryDAO {

    public void createDelivery(int truckID, int bookingID) {
        String sql = "INSERT INTO delivery (truckID, bookingID, delivery_date) VALUES (?, ?, CURDATE())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, truckID);
            ps.setInt(2, bookingID);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}