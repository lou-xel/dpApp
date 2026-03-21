package com.arrowcatering.ui;

import javax.swing.*;
import java.awt.*;

public class BookingPanel extends JPanel {
    public BookingPanel() {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        JTextArea info = new JTextArea(
                "═══════════════════════════════════════════════════════════\n" +
                        "                    EVENT BOOKING MODULE                      \n" +
                        "═══════════════════════════════════════════════════════════\n\n" +
                        "Assigned to: Catherine Liberty B. Felix\n\n" +
                        "This module will handle:\n" +
                        "  • Reading customer profile to verify contact details\n" +
                        "  • Checking package availability for the requested event date\n" +
                        "  • Calculating total cost based on guest count and selected package\n" +
                        "  • Creating booking record with deposit requirement\n" +
                        "  • Checking truck availability for delivery on event date\n" +
                        "  • Assigning appropriate truck based on capacity and distance\n" +
                        "  • Generating booking confirmation and delivery schedule\n\n" +
                        "═══════════════════════════════════════════════════════════"
        );
        info.setEditable(false);
        info.setFont(new Font("Monospaced", Font.PLAIN, 12));
        info.setBackground(new Color(240, 248, 255));
        info.setOpaque(true);
        info.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        centerPanel.add(info);
        add(centerPanel, BorderLayout.CENTER);
    }
}