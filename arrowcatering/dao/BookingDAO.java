package com.arrowcatering.dao;

import java.sql.*;
import java.util.*;

public class BookingDAO {

    public List<String[]> getConfirmedBookings() {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT b.bookingID, c.first_name, c.last_name, b.event_date, b.guest_count, b.status " +
                "FROM booking b " +
                "JOIN customer c ON b.customerID = c.customer_ID " +
                "WHERE b.status = 'Confirmed'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new String[]{
                        String.valueOf(rs.getInt("bookingID")),
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        "-", // Package name - can be added later
                        rs.getDate("event_date").toString(),
                        String.valueOf(rs.getInt("guest_count")),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}