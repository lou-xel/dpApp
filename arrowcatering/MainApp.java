package com.arrowcatering;

import com.arrowcatering.dao.DBConnection;
import com.arrowcatering.ui.MainFrame;
import javax.swing.*;
import java.sql.Connection;

public class MainApp {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }


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


        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}