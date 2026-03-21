package com.arrowcatering.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JLabel statusLabel;

    public MainFrame() {
        setTitle("Arrow Catering Services Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        initializeUI();
        setupMenuBar();
        setupStatusBar();
    }

    private void initializeUI() {
        tabbedPane = new JTabbedPane();

        // Add all the panels
        tabbedPane.addTab("👥 Customer Management", new CustomerPanel());
        tabbedPane.addTab("📅 Event Booking", new BookingPanel());
        tabbedPane.addTab("📦 Inventory Management", new InventoryPanel());
        tabbedPane.addTab("🚚 Truck Scheduling", new TruckPanel());
        tabbedPane.addTab("📊 Reports", new ReportsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void setupStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusLabel = new JLabel("Ready");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Arrow Catering Services Management System\n" +
                        "Version 1.0\n\n" +
                        "Developed by:\n" +
                        "• Felix, Catherine Liberty B.\n" +
                        "• Sanson, Alexandra Louise F.\n" +
                        "• Siriban, Joshua Gabriel D.\n" +
                        "• Vito, Luis Andre P.\n\n" +
                        "CCINFOM - Term 2, A.Y. 2025-2026",
                "About Arrow Catering System",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void setStatusMessage(String msg) {
        statusLabel.setText(msg);
    }
}