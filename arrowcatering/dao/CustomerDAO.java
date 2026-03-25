package com.arrowcatering.dao;

import com.arrowcatering.model.Customer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (customer_ID, last_name, first_name, mi, email, contact_number, date_registered) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customer.getCustomerId());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getFirstName());
            pstmt.setString(4, customer.getMi());
            pstmt.setString(5, customer.getEmail());
            pstmt.setInt(6, customer.getContactNumber());
            pstmt.setDate(7, Date.valueOf(customer.getDateRegistered()));  // LocalDate to SQL Date
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customer_ID"));
                c.setLastName(rs.getString("last_name"));
                c.setFirstName(rs.getString("first_name"));
                c.setMi(rs.getString("mi"));
                c.setEmail(rs.getString("email"));
                c.setContactNumber(rs.getInt("contact_number"));

                Date date = rs.getDate("date_registered");
                if (date != null) {
                    c.setDateRegistered(date.toLocalDate());  // SQL Date to LocalDate
                }

                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET last_name=?, first_name=?, mi=?, email=?, contact_number=? WHERE customer_ID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getLastName());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getMi());
            pstmt.setString(4, customer.getEmail());
            pstmt.setLong(5, customer.getContactNumber());  // Use setLong for BIGINT
            pstmt.setInt(6, customer.getCustomerId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customer WHERE customer_ID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}