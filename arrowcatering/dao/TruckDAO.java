package com.arrowcatering.dao;

import com.arrowcatering.model.Truck;
import java.sql.*;
import java.util.*;

public class TruckDAO {

    public List<Truck> getAvailableTrucks() {
        List<Truck> list = new ArrayList<>();
        String sql = "SELECT * FROM truck WHERE currentStatus = 'Available'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Truck t = new Truck();
                t.setTruckID(rs.getInt("truckID"));
                t.setPlateNumber(rs.getString("plate_number"));
                t.setDriverName(rs.getString("driver_name"));
                t.setDriverContact(rs.getString("driver_contact"));
                t.setCapacity(rs.getInt("capacity_kg"));
                t.setStatus(rs.getString("currentStatus"));
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateTruckStatus(int id, String status) {
        String sql = "UPDATE truck SET currentStatus = ? WHERE truckID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}