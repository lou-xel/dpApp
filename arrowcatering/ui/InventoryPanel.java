package com.arrowcatering.ui;

import javax.swing.*;
import java.awt.*;

public class InventoryPanel extends JPanel {
    public InventoryPanel() {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        JTextArea info = new JTextArea(
                "═══════════════════════════════════════════════════════════\n" +
                        "                   INVENTORY MANAGEMENT                      \n" +
                        "═══════════════════════════════════════════════════════════\n\n" +
                        "Assigned to: Luis Andre P. Vito & Joshua Gabriel D. Siriban\n\n" +
                        "This module will handle:\n" +
                        "  • Ingredient inventory tracking\n" +
                        "  • Supplier management\n" +
                        "  • Menu operation and ingredient deductions\n" +
                        "  • Checking ingredient availability\n" +
                        "  • Updating stock levels\n\n" +
                        "═══════════════════════════════════════════════════════════"
        );
        info.setEditable(false);
        info.setFont(new Font("Monospaced", Font.PLAIN, 12));
        info.setBackground(new Color(255, 248, 225));
        info.setOpaque(true);
        info.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        centerPanel.add(info);
        add(centerPanel, BorderLayout.CENTER);
    }
}