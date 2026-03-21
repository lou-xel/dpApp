package com.arrowcatering.ui;

import javax.swing.*;
import java.awt.*;

public class ReportsPanel extends JPanel {
    public ReportsPanel() {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        JTextArea info = new JTextArea(
                "═══════════════════════════════════════════════════════════\n" +
                        "                       REPORTS MODULE                       \n" +
                        "═══════════════════════════════════════════════════════════\n\n" +
                        "Reports to be generated:\n\n" +
                        "  1. Annual Repeat Customer Analysis Report\n" +
                        "     • Total number of new customers per year\n" +
                        "     • Total number of repeat customers per year\n" +
                        "     Assigned to: Luis Andre P. Vito\n\n" +
                        "  2. Monthly Catering Bookings Summary\n" +
                        "     • Total bookings, guests, revenue per month\n" +
                        "     Assigned to: Joshua Gabriel D. Siriban\n\n" +
                        "  3. Stock & Ingredients Usage Report\n" +
                        "     • Total quantity used, remaining stock, low-stock items\n" +
                        "     Assigned to: Catherine Liberty B. Felix\n\n" +
                        "  4. Total Costs Report\n" +
                        "     • Total ingredient cost, service cost, average cost per booking\n" +
                        "     Assigned to: Alexandra Louise F. Sanson\n\n" +
                        "═══════════════════════════════════════════════════════════"
        );
        info.setEditable(false);
        info.setFont(new Font("Monospaced", Font.PLAIN, 12));
        info.setBackground(new Color(230, 242, 255));
        info.setOpaque(true);
        info.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        centerPanel.add(info);
        add(centerPanel, BorderLayout.CENTER);
    }
}