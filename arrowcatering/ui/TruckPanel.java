package com.arrowcatering.ui;

import com.arrowcatering.dao.*;
import com.arrowcatering.model.Truck;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TruckPanel extends JPanel {

    private JTable bookingTable, truckTable;
    private DefaultTableModel bookingModel, truckModel;

    public TruckPanel() {
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(245,247,250));
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel title = new JLabel("🚚 Truck Scheduling");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(44,62,80));

        add(title, BorderLayout.NORTH);


        JPanel center = new JPanel(new GridLayout(1,2,15,0));
        center.setBackground(getBackground());


        bookingModel = new DefaultTableModel(
                new String[]{"Booking ID","Customer","Package","Date","Guests","Status"},0);

        bookingTable = new JTable(bookingModel);
        styleTable(bookingTable);

        center.add(createPanel("Confirmed Bookings", bookingTable));


        truckModel = new DefaultTableModel(
                new String[]{"Truck ID","Plate","Driver","Capacity","Status"},0);

        truckTable = new JTable(truckModel);
        styleTable(truckTable);

        center.add(createPanel("Available Trucks", truckTable));

        add(center, BorderLayout.CENTER);

        JButton assignBtn = new JButton("Assign Truck");
        assignBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        assignBtn.setBackground(new Color(52,152,219));
        assignBtn.setForeground(Color.WHITE);
        assignBtn.setFocusPainted(false);
        assignBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        assignBtn.addActionListener(e -> assignTruck());

        JPanel bottom = new JPanel();
        bottom.setBackground(getBackground());
        bottom.add(assignBtn);

        add(bottom, BorderLayout.SOUTH);

        loadBookings();
        loadTrucks();
    }


    private void styleTable(JTable table) {
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionBackground(new Color(52,152,219));
    }

    private JPanel createPanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(title));

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void loadBookings() {
        bookingModel.setRowCount(0);

        BookingDAO dao = new BookingDAO();
        List<String[]> list = dao.getConfirmedBookings();

        for (String[] row : list) {
            bookingModel.addRow(row);
        }
    }


    private void loadTrucks() {
        truckModel.setRowCount(0);

        TruckDAO dao = new TruckDAO();

        for (Truck t : dao.getAvailableTrucks()) {
            truckModel.addRow(new Object[]{
                    t.getTruckID(),
                    t.getPlateNumber(),
                    t.getDriverName(),
                    t.getCapacity(),
                    t.getStatus()
            });
        }
    }


    private void assignTruck() {
        int bRow = bookingTable.getSelectedRow();
        int tRow = truckTable.getSelectedRow();

        if (bRow < 0 || tRow < 0) {
            JOptionPane.showMessageDialog(this, "Select booking AND truck!");
            return;
        }

        int bookingID = Integer.parseInt(bookingModel.getValueAt(bRow,0).toString());
        int truckID = Integer.parseInt(truckModel.getValueAt(tRow,0).toString());

        DeliveryDAO d = new DeliveryDAO();
        TruckDAO t = new TruckDAO();

        d.createDelivery(truckID, bookingID);
        t.updateTruckStatus(truckID, "Scheduled");

        JOptionPane.showMessageDialog(this, "Truck Assigned!");

        loadTrucks();
    }
}