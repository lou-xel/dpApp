package com.arrowcatering.ui;

import javax.swing.*;
import java.awt.*;

public class TruckPanel extends JPanel {
    public TruckPanel() {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        JTextArea info = new JTextArea(
                "═══════════════════════════════════════════════════════════\n" +
                        "                    TRUCK SCHEDULING                        \n" +
                        "═══════════════════════════════════════════════════════════\n\n" +
                        "Assigned to: Alexandra Louise F. Sanson\n\n" +
                        "This module will handle:\n" +
                        "  • Reading confirmed events requiring delivery\n" +
                        "  • Checking truck availability (status, capacity, temperature control)\n" +
                        "  • Updating truck status to 'Scheduled' or 'In Transit'\n" +
                        "  • Updating booking records with assigned truck and estimated arrival time\n\n" +
                        "═══════════════════════════════════════════════════════════"
        );
        info.setEditable(false);
        info.setFont(new Font("Monospaced", Font.PLAIN, 12));
        info.setBackground(new Color(232, 245, 233));
        info.setOpaque(true);
        info.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        centerPanel.add(info);
        add(centerPanel, BorderLayout.CENTER);
    }
}