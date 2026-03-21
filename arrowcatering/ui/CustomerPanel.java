package com.arrowcatering.ui;

import com.arrowcatering.business.CustomerService;
import com.arrowcatering.model.Customer;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class CustomerPanel extends JPanel {
    private CustomerService customerService;
    private JTextField txtCustomerId, txtLastName, txtFirstName, txtMi, txtEmail, txtContact;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbSearchType;
    private JTextField txtSearch;

    public CustomerPanel() {
        customerService = new CustomerService();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createSearchPanel(), BorderLayout.SOUTH);
        loadCustomers();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Customer Management",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(52, 152, 219)
        ));

        JPanel fields = new JPanel(new GridLayout(6, 2, 10, 10));
        fields.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        fields.add(createLabel("Customer ID:"));
        txtCustomerId = new JTextField();
        fields.add(txtCustomerId);

        fields.add(createLabel("Last Name:*"));
        txtLastName = new JTextField();
        fields.add(txtLastName);

        fields.add(createLabel("First Name:*"));
        txtFirstName = new JTextField();
        fields.add(txtFirstName);

        fields.add(createLabel("Middle Initial:"));
        txtMi = new JTextField(2);
        fields.add(txtMi);

        fields.add(createLabel("Email:"));
        txtEmail = new JTextField();
        fields.add(txtEmail);

        fields.add(createLabel("Contact Number:*"));
        txtContact = new JTextField();
        fields.add(txtContact);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnAdd = createStyledButton("➕ Register Customer", new Color(46, 204, 113));
        JButton btnUpdate = createStyledButton("✏️ Update Info", new Color(52, 152, 219));
        JButton btnDelete = createStyledButton("🗑️ Delete Customer", new Color(231, 76, 60));
        JButton btnClear = createStyledButton("🗑️ Clear Form", new Color(149, 165, 166));

        btnAdd.addActionListener(this::addCustomer);
        btnUpdate.addActionListener(this::updateCustomer);
        btnDelete.addActionListener(this::deleteCustomer);
        btnClear.addActionListener(e -> clearForm());

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);

        panel.add(fields, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        // Add info label
        JLabel infoLabel = new JLabel("* Required fields");
        infoLabel.setFont(infoLabel.getFont().deriveFont(Font.ITALIC, 10));
        infoLabel.setForeground(Color.GRAY);
        panel.add(infoLabel, BorderLayout.NORTH);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Customer List",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12)
        ));

        String[] cols = {"ID", "Last Name", "First Name", "MI", "Email", "Contact", "Registered Date"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.setRowHeight(25);
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedCustomer();
        });

        JScrollPane scroll = new JScrollPane(customerTable);
        scroll.setPreferredSize(new Dimension(0, 300));
        panel.add(scroll, BorderLayout.CENTER);

        // Add table info
        JLabel infoLabel = new JLabel("Click on a row to load customer data");
        infoLabel.setFont(infoLabel.getFont().deriveFont(Font.ITALIC, 10));
        infoLabel.setForeground(Color.GRAY);
        panel.add(infoLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createTitledBorder("Search Customers"));

        cmbSearchType = new JComboBox<>(new String[]{"ID", "Last Name", "First Name", "Email"});
        txtSearch = new JTextField(15);

        JButton btnSearch = createStyledButton("🔍 Search", new Color(52, 152, 219));
        JButton btnRefresh = createStyledButton("🔄 Refresh", new Color(149, 165, 166));

        btnSearch.addActionListener(e -> searchCustomers());
        btnRefresh.addActionListener(e -> loadCustomers());

        panel.add(new JLabel("Search by:"));
        panel.add(cmbSearchType);
        panel.add(txtSearch);
        panel.add(btnSearch);
        panel.add(btnRefresh);

        return panel;
    }

    private void addCustomer(ActionEvent e) {
        try {
            if (txtCustomerId.getText().trim().isEmpty() ||
                    txtLastName.getText().trim().isEmpty() ||
                    txtFirstName.getText().trim().isEmpty() ||
                    txtContact.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all required fields (*)\nCustomer ID, Last Name, First Name, Contact Number",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Customer c = new Customer();
            c.setCustomerId(Integer.parseInt(txtCustomerId.getText().trim()));
            c.setLastName(txtLastName.getText().trim());
            c.setFirstName(txtFirstName.getText().trim());
            c.setMi(txtMi.getText().trim());
            c.setEmail(txtEmail.getText().trim());
            c.setContactNumber(Integer.parseInt(txtContact.getText().trim()));
            c.setDateRegistered(LocalDate.now());

            if (customerService.registerCustomer(c)) {
                JOptionPane.showMessageDialog(this,
                        "✅ Customer added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadCustomers();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Failed to add customer. Customer ID might already exist.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid number format. Please check Customer ID and Contact Number.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCustomer(ActionEvent e) {
        int row = customerTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a customer to update from the table.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Customer c = new Customer();
            c.setCustomerId(Integer.parseInt(txtCustomerId.getText().trim()));
            c.setLastName(txtLastName.getText().trim());
            c.setFirstName(txtFirstName.getText().trim());
            c.setMi(txtMi.getText().trim());
            c.setEmail(txtEmail.getText().trim());
            c.setContactNumber(Integer.parseInt(txtContact.getText().trim()));

            if (customerService.updateCustomer(c)) {
                JOptionPane.showMessageDialog(this,
                        "✅ Customer updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadCustomers();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Update failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid number format.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer(ActionEvent e) {
        int row = customerTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a customer to delete from the table.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String name = tableModel.getValueAt(row, 1) + ", " + tableModel.getValueAt(row, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete customer: " + name + " (ID: " + id + ")?\nThis will also delete all associated bookings!",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (customerService.deleteCustomer(id)) {
                JOptionPane.showMessageDialog(this,
                        "✅ Customer deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loadCustomers();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Delete failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadCustomers() {
        tableModel.setRowCount(0);
        List<Customer> list = customerService.getAllCustomers();
        for (Customer c : list) {
            tableModel.addRow(new Object[]{
                    c.getCustomerId(),
                    c.getLastName(),
                    c.getFirstName(),
                    c.getMi(),
                    c.getEmail(),
                    c.getContactNumber(),
                    c.getDateRegistered()
            });
        }
    }

    private void searchCustomers() {
        String type = (String) cmbSearchType.getSelectedItem();
        String val = txtSearch.getText().trim().toLowerCase();

        if (val.isEmpty()) {
            loadCustomers();
            return;
        }

        tableModel.setRowCount(0);
        List<Customer> all = customerService.getAllCustomers();

        for (Customer c : all) {
            boolean match = false;
            switch (type) {
                case "ID":
                    match = String.valueOf(c.getCustomerId()).contains(val);
                    break;
                case "Last Name":
                    match = c.getLastName().toLowerCase().contains(val);
                    break;
                case "First Name":
                    match = c.getFirstName().toLowerCase().contains(val);
                    break;
                case "Email":
                    match = c.getEmail() != null && c.getEmail().toLowerCase().contains(val);
                    break;
            }
            if (match) {
                tableModel.addRow(new Object[]{
                        c.getCustomerId(),
                        c.getLastName(),
                        c.getFirstName(),
                        c.getMi(),
                        c.getEmail(),
                        c.getContactNumber(),
                        c.getDateRegistered()
                });
            }
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No customers found matching: " + val,
                    "No Results",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadSelectedCustomer() {
        int row = customerTable.getSelectedRow();
        if (row >= 0) {
            txtCustomerId.setText(tableModel.getValueAt(row, 0).toString());
            txtLastName.setText(tableModel.getValueAt(row, 1).toString());
            txtFirstName.setText(tableModel.getValueAt(row, 2).toString());
            txtMi.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
            txtEmail.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
            txtContact.setText(tableModel.getValueAt(row, 5).toString());
        }
    }

    private void clearForm() {
        txtCustomerId.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        txtMi.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtSearch.setText("");
        customerTable.clearSelection();
    }
}