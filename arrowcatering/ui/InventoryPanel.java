package com.arrowcatering.ui;

import com.arrowcatering.business.InventoryService;
import com.arrowcatering.model.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

public class InventoryPanel extends JPanel {
    private InventoryService inventoryService;

    // Ingredient components
    private JTextField txtIngredientId, txtIngredientName, txtStock, txtUnitCost, txtReorderLevel;
    private JTable ingredientTable;
    private DefaultTableModel ingredientTableModel;

    // Supplier components
    private JTextField txtSupplierId, txtSupplierName, txtSupplierContact;
    private JTable supplierTable;
    private DefaultTableModel supplierTableModel;

    // Stock operation components
    private JComboBox<String> cmbStockOperation;
    private JTextField txtStockIngredientId, txtStockQuantity;

    // Low stock warning
    private JTextArea lowStockArea;

    public InventoryPanel() {
        inventoryService = new InventoryService();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create tabs for different inventory functions
        JTabbedPane inventoryTabs = new JTabbedPane();
        inventoryTabs.addTab("📦 Ingredients", createIngredientPanel());
        inventoryTabs.addTab("🏭 Suppliers", createSupplierPanel());
        inventoryTabs.addTab("📊 Stock Operations", createStockOperationPanel());
        inventoryTabs.addTab("⚠️ Low Stock Alert", createLowStockPanel());

        add(inventoryTabs, BorderLayout.CENTER);

        // Load initial data
        loadIngredients();
        loadSuppliers();
        updateLowStockAlert();
    }

    private JPanel createIngredientPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Ingredient Management",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(52, 152, 219)
        ));

        JPanel fields = new JPanel(new GridLayout(5, 2, 10, 10));
        fields.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fields.add(new JLabel("Ingredient ID:"));
        txtIngredientId = new JTextField();
        fields.add(txtIngredientId);

        fields.add(new JLabel("Name:"));
        txtIngredientName = new JTextField();
        fields.add(txtIngredientName);

        fields.add(new JLabel("Current Stock:"));
        txtStock = new JTextField();
        fields.add(txtStock);

        fields.add(new JLabel("Unit Cost:"));
        txtUnitCost = new JTextField();
        fields.add(txtUnitCost);

        fields.add(new JLabel("Reorder Level:"));
        txtReorderLevel = new JTextField();
        fields.add(txtReorderLevel);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton btnAdd = createStyledButton("➕ Add Ingredient", new Color(46, 204, 113));
        JButton btnUpdate = createStyledButton("✏️ Update", new Color(52, 152, 219));
        JButton btnDelete = createStyledButton("🗑️ Delete", new Color(231, 76, 60));
        JButton btnClear = createStyledButton("🗑️ Clear", new Color(149, 165, 166));

        btnAdd.addActionListener(this::addIngredient);
        btnUpdate.addActionListener(this::updateIngredient);
        btnDelete.addActionListener(this::deleteIngredient);
        btnClear.addActionListener(e -> clearIngredientForm());

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);

        formPanel.add(fields, BorderLayout.CENTER);
        formPanel.add(buttons, BorderLayout.SOUTH);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Ingredient List"));

        String[] cols = {"ID", "Name", "Stock", "Unit Cost", "Reorder Level", "Status"};
        ingredientTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        ingredientTable = new JTable(ingredientTableModel);
        ingredientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ingredientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedIngredient();
        });

        JScrollPane scroll = new JScrollPane(ingredientTable);
        tablePanel.add(scroll, BorderLayout.CENTER);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSupplierPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Supplier Management",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        JPanel fields = new JPanel(new GridLayout(3, 2, 10, 10));
        fields.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fields.add(new JLabel("Supplier ID:"));
        txtSupplierId = new JTextField();
        fields.add(txtSupplierId);

        fields.add(new JLabel("Supplier Name:"));
        txtSupplierName = new JTextField();
        fields.add(txtSupplierName);

        fields.add(new JLabel("Contact Number:"));
        txtSupplierContact = new JTextField();
        fields.add(txtSupplierContact);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton btnAdd = createStyledButton("➕ Add Supplier", new Color(46, 204, 113));
        JButton btnUpdate = createStyledButton("✏️ Update", new Color(52, 152, 219));
        JButton btnDelete = createStyledButton("🗑️ Delete", new Color(231, 76, 60));
        JButton btnClear = createStyledButton("🗑️ Clear", new Color(149, 165, 166));

        btnAdd.addActionListener(this::addSupplier);
        btnUpdate.addActionListener(this::updateSupplier);
        btnDelete.addActionListener(this::deleteSupplier);
        btnClear.addActionListener(e -> clearSupplierForm());

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);

        formPanel.add(fields, BorderLayout.CENTER);
        formPanel.add(buttons, BorderLayout.SOUTH);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Supplier List"));

        String[] cols = {"ID", "Name", "Contact Number"};
        supplierTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        supplierTable = new JTable(supplierTableModel);
        supplierTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedSupplier();
        });

        JScrollPane scroll = new JScrollPane(supplierTable);
        tablePanel.add(scroll, BorderLayout.CENTER);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStockOperationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel operationPanel = new JPanel(new GridBagLayout());
        operationPanel.setBorder(BorderFactory.createTitledBorder("Stock Adjustment"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        operationPanel.add(new JLabel("Operation:"), gbc);

        gbc.gridx = 1;
        cmbStockOperation = new JComboBox<>(new String[]{"Add Stock", "Deduct Stock"});
        operationPanel.add(cmbStockOperation, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        operationPanel.add(new JLabel("Ingredient ID:"), gbc);

        gbc.gridx = 1;
        txtStockIngredientId = new JTextField(10);
        operationPanel.add(txtStockIngredientId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        operationPanel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        txtStockQuantity = new JTextField(10);
        operationPanel.add(txtStockQuantity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton btnApply = createStyledButton("Apply Stock Change", new Color(52, 152, 219));
        btnApply.addActionListener(this::applyStockChange);
        operationPanel.add(btnApply, gbc);

        panel.add(operationPanel, BorderLayout.NORTH);

        // Instructions
        JTextArea instructions = new JTextArea(
                "Stock Operations Instructions:\n\n" +
                        "• Add Stock: Increase ingredient quantity (for restocking)\n" +
                        "• Deduct Stock: Decrease ingredient quantity (for usage/waste)\n\n" +
                        "Note: Deduct operation will only work if enough stock is available."
        );
        instructions.setEditable(false);
        instructions.setBackground(new Color(255, 248, 225));
        instructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(instructions, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLowStockPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lowStockArea = new JTextArea();
        lowStockArea.setEditable(false);
        lowStockArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        lowStockArea.setBackground(new Color(255, 240, 240));

        JScrollPane scroll = new JScrollPane(lowStockArea);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnRefresh = createStyledButton("🔄 Refresh Alert", new Color(52, 152, 219));
        btnRefresh.addActionListener(e -> updateLowStockAlert());

        JPanel bottom = new JPanel();
        bottom.add(btnRefresh);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
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

    // Ingredient operations
    private void addIngredient(ActionEvent e) {
        try {
            Ingredient i = new Ingredient();
            i.setIngredientId(Integer.parseInt(txtIngredientId.getText().trim()));
            i.setName(txtIngredientName.getText().trim());
            i.setStock(Integer.parseInt(txtStock.getText().trim()));
            i.setUnitCost(new BigDecimal(txtUnitCost.getText().trim()));
            i.setReorderLevel(Integer.parseInt(txtReorderLevel.getText().trim()));

            if (inventoryService.addIngredient(i)) {
                JOptionPane.showMessageDialog(this, "✅ Ingredient added successfully!");
                loadIngredients();
                clearIngredientForm();
                updateLowStockAlert();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to add ingredient.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please check all fields.");
        }
    }

    private void updateIngredient(ActionEvent e) {
        int row = ingredientTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an ingredient to update.");
            return;
        }

        try {
            Ingredient i = new Ingredient();
            i.setIngredientId(Integer.parseInt(txtIngredientId.getText().trim()));
            i.setName(txtIngredientName.getText().trim());
            i.setStock(Integer.parseInt(txtStock.getText().trim()));
            i.setUnitCost(new BigDecimal(txtUnitCost.getText().trim()));
            i.setReorderLevel(Integer.parseInt(txtReorderLevel.getText().trim()));

            if (inventoryService.updateIngredient(i)) {
                JOptionPane.showMessageDialog(this, "✅ Ingredient updated successfully!");
                loadIngredients();
                clearIngredientForm();
                updateLowStockAlert();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Update failed.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.");
        }
    }

    private void deleteIngredient(ActionEvent e) {
        int row = ingredientTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an ingredient to delete.");
            return;
        }

        int id = (int) ingredientTableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete ingredient ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (inventoryService.deleteIngredient(id)) {
                JOptionPane.showMessageDialog(this, "✅ Ingredient deleted!");
                loadIngredients();
                clearIngredientForm();
                updateLowStockAlert();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Delete failed.");
            }
        }
    }

    private void loadIngredients() {
        ingredientTableModel.setRowCount(0);
        List<Ingredient> ingredients = inventoryService.getAllIngredients();
        for (Ingredient i : ingredients) {
            String status = i.isLowStock() ? "⚠️ LOW STOCK" : "OK";
            ingredientTableModel.addRow(new Object[]{
                    i.getIngredientId(),
                    i.getName(),
                    i.getStock(),
                    String.format("₱ %.2f", i.getUnitCost()),
                    i.getReorderLevel(),
                    status
            });
        }
    }

    private void loadSelectedIngredient() {
        int row = ingredientTable.getSelectedRow();
        if (row >= 0) {
            txtIngredientId.setText(ingredientTableModel.getValueAt(row, 0).toString());
            txtIngredientName.setText(ingredientTableModel.getValueAt(row, 1).toString());
            txtStock.setText(ingredientTableModel.getValueAt(row, 2).toString());
            // Parse unit cost from formatted string
            String cost = ingredientTableModel.getValueAt(row, 3).toString().replace("₱ ", "");
            txtUnitCost.setText(cost);
            txtReorderLevel.setText(ingredientTableModel.getValueAt(row, 4).toString());
        }
    }

    private void clearIngredientForm() {
        txtIngredientId.setText("");
        txtIngredientName.setText("");
        txtStock.setText("");
        txtUnitCost.setText("");
        txtReorderLevel.setText("");
        ingredientTable.clearSelection();
    }

    // Supplier operations
    private void addSupplier(ActionEvent e) {
        try {
            Supplier s = new Supplier();
            s.setSupplierId(Integer.parseInt(txtSupplierId.getText().trim()));
            s.setSupplierName(txtSupplierName.getText().trim());
            s.setContactNumber(txtSupplierContact.getText().trim());

            if (inventoryService.addSupplier(s)) {
                JOptionPane.showMessageDialog(this, "✅ Supplier added successfully!");
                loadSuppliers();
                clearSupplierForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to add supplier.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid supplier ID format.");
        }
    }

    private void updateSupplier(ActionEvent e) {
        int row = supplierTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a supplier to update.");
            return;
        }

        try {
            Supplier s = new Supplier();
            s.setSupplierId(Integer.parseInt(txtSupplierId.getText().trim()));
            s.setSupplierName(txtSupplierName.getText().trim());
            s.setContactNumber(txtSupplierContact.getText().trim());

            if (inventoryService.updateSupplier(s)) {
                JOptionPane.showMessageDialog(this, "✅ Supplier updated successfully!");
                loadSuppliers();
                clearSupplierForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Update failed.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.");
        }
    }

    private void deleteSupplier(ActionEvent e) {
        int row = supplierTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a supplier to delete.");
            return;
        }

        int id = (int) supplierTableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete supplier ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (inventoryService.deleteSupplier(id)) {
                JOptionPane.showMessageDialog(this, "✅ Supplier deleted!");
                loadSuppliers();
                clearSupplierForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Delete failed.");
            }
        }
    }

    private void loadSuppliers() {
        supplierTableModel.setRowCount(0);
        List<Supplier> suppliers = inventoryService.getAllSuppliers();
        for (Supplier s : suppliers) {
            supplierTableModel.addRow(new Object[]{
                    s.getSupplierId(),
                    s.getSupplierName(),
                    s.getContactNumber()
            });
        }
    }

    private void loadSelectedSupplier() {
        int row = supplierTable.getSelectedRow();
        if (row >= 0) {
            txtSupplierId.setText(supplierTableModel.getValueAt(row, 0).toString());
            txtSupplierName.setText(supplierTableModel.getValueAt(row, 1).toString());
            txtSupplierContact.setText(supplierTableModel.getValueAt(row, 2).toString());
        }
    }

    private void clearSupplierForm() {
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtSupplierContact.setText("");
        supplierTable.clearSelection();
    }

    // Stock operation
    private void applyStockChange(ActionEvent e) {
        try {
            int ingredientId = Integer.parseInt(txtStockIngredientId.getText().trim());
            int quantity = Integer.parseInt(txtStockQuantity.getText().trim());
            boolean isAdd = cmbStockOperation.getSelectedItem().equals("Add Stock");

            boolean success;
            if (isAdd) {
                success = inventoryService.addStock(ingredientId, quantity);
            } else {
                success = inventoryService.deductStock(ingredientId, quantity);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Stock updated successfully!");
                loadIngredients();
                updateLowStockAlert();
                txtStockIngredientId.setText("");
                txtStockQuantity.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Stock update failed. Check ingredient ID and stock availability.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please check ingredient ID and quantity.");
        }
    }

    // Low stock alert
    private void updateLowStockAlert() {
        List<Ingredient> lowStock = inventoryService.getLowStockIngredients();
        StringBuilder sb = new StringBuilder();
        sb.append("⚠️ LOW STOCK ALERT ⚠️\n");
        sb.append("=" .repeat(50));
        sb.append("\n\n");

        if (lowStock.isEmpty()) {
            sb.append("✅ All ingredients have sufficient stock!");
        } else {
            sb.append("The following ingredients need to be reordered:\n\n");
            for (Ingredient i : lowStock) {
                sb.append(String.format("• %s (ID: %d)\n", i.getName(), i.getIngredientId()));
                sb.append(String.format("  Current Stock: %d | Reorder Level: %d\n", i.getStock(), i.getReorderLevel()));
                sb.append(String.format("  Need to reorder: %d units\n\n", i.getReorderLevel() - i.getStock() + 10));
            }
        }

        lowStockArea.setText(sb.toString());
    }
}