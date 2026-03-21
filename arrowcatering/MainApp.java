package com.arrowcatering;

import com.arrowcatering.dao.DBConnection;
import com.arrowcatering.ui.MainFrame;
import javax.swing.*;
import java.sql.Connection;

public class MainApp {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test database connection
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("✅ Database connected successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Database connection failed:\n" + e.getMessage() +
                            "\n\nPlease check your database.properties file and MySQL server.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Launch GUI
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}