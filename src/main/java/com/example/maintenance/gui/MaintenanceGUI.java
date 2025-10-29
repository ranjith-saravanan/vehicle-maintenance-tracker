package com.example.maintenance.gui;

import com.example.maintenance.core.MaintenanceScheduler;
import com.example.maintenance.model.MaintenanceItem;
import com.example.maintenance.io.Storage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class MaintenanceGUI extends JFrame {
    private List<MaintenanceItem> items;
    private Storage storage;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    public MaintenanceGUI() {
        // Initialize storage and load items
        storage = new Storage(Storage.defaultPath());
        try {
            items = storage.load();
        } catch (Exception ex) {
            items = new ArrayList<>();
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }

        // Setup main window
        setTitle("Vehicle Maintenance Tracker");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize status label first
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(52, 152, 219);
                Color color2 = new Color(41, 128, 185);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("🚗 Vehicle Maintenance Tracker");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.setOpaque(false);
        
        JLabel countLabel = new JLabel(items.size() + " Vehicles", SwingConstants.RIGHT);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        countLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Real-time monitoring system", SwingConstants.RIGHT);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(236, 240, 241));
        
        rightPanel.add(countLabel);
        rightPanel.add(subtitleLabel);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create table model
        String[] columns = {"ID", "Vehicle Name", "Last Service", "Interval", "Next Service", "Days Until", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Populate table
        updateTable();

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(236, 240, 241));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new Dimension(10, 5));

        // Custom header renderer
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        // Custom cell renderer for status column with colors
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String status = value.toString();
                    if (status.contains("OVERDUE")) {
                        c.setBackground(new Color(255, 230, 230));
                        c.setForeground(new Color(231, 76, 60));
                    } else if (status.contains("DUE SOON")) {
                        c.setBackground(new Color(255, 245, 220));
                        c.setForeground(new Color(230, 126, 34));
                    } else {
                        c.setBackground(new Color(230, 255, 230));
                        c.setForeground(new Color(46, 204, 113));
                    }
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                }
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(280);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(110);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(206, 212, 218)));

        // Add Item button
        JButton addButton = createModernButton("➕ Add Vehicle", new Color(46, 204, 113), new Color(39, 174, 96));
        addButton.addActionListener(e -> showAddItemDialog());
        panel.add(addButton);

        // Show Alerts button
        JButton alertButton = createModernButton("⚠️ Show Alerts", new Color(241, 196, 15), new Color(243, 156, 18));
        alertButton.addActionListener(e -> showAlerts());
        panel.add(alertButton);

        // Refresh button
        JButton refreshButton = createModernButton("🔄 Refresh", new Color(52, 152, 219), new Color(41, 128, 185));
        refreshButton.addActionListener(e -> updateTable());
        panel.add(refreshButton);

        // Save button
        JButton saveButton = createModernButton("💾 Save", new Color(155, 89, 182), new Color(142, 68, 173));
        saveButton.addActionListener(e -> saveData());
        panel.add(saveButton);

        // Delete button
        JButton deleteButton = createModernButton("🗑️ Delete", new Color(231, 76, 60), new Color(192, 57, 43));
        deleteButton.addActionListener(e -> deleteItem());
        panel.add(deleteButton);

        // Add spacing
        panel.add(Box.createHorizontalStrut(20));

        // Status label with icon
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(52, 73, 94));
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.WHITE);
        panel.add(statusLabel);

        return panel;
    }

    private JButton createModernButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(bgColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            }
            public void mouseExited(MouseEvent e) {
                button.setFont(new Font("Segoe UI", Font.BOLD, 13));
            }
        });
        
        return button;
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (MaintenanceItem item : items) {
            LocalDate nextDate = MaintenanceScheduler.nextServiceDate(item);
            long daysUntil = MaintenanceScheduler.daysUntilNext(item);
            MaintenanceScheduler.Status status = MaintenanceScheduler.status(item);

            String statusText = status == MaintenanceScheduler.Status.OVERDUE ? "⚠️ OVERDUE" :
                               status == MaintenanceScheduler.Status.DUE_SOON ? "⏰ DUE SOON" : "✅ OK";

            Object[] row = {
                item.getId(),
                item.getName(),
                item.getLastServiceDate(),
                item.getIntervalDays() + " days",
                nextDate,
                daysUntil + " days",
                statusText
            };
            tableModel.addRow(row);
        }
        statusLabel.setText("Table updated - " + items.size() + " items");
    }

    private void showAddItemDialog() {
        JDialog dialog = new JDialog(this, "Add New Vehicle Maintenance Item", true);
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Title panel with gradient
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(46, 204, 113), 
                                                     getWidth(), 0, new Color(39, 174, 96));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        titlePanel.setPreferredSize(new Dimension(0, 60));
        JLabel titleLabel = new JLabel("➕ Add New Vehicle");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField nameField = new JTextField(20);
        JTextField dateField = new JTextField(LocalDate.now().toString(), 20);
        JTextField intervalField = new JTextField("90", 20);

        styleTextField(nameField);
        styleTextField(dateField);
        styleTextField(intervalField);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createStyledLabel("Vehicle Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createStyledLabel("Last Service Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createStyledLabel("Interval (days):"), gbc);
        gbc.gridx = 1;
        formPanel.add(intervalField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addBtn = createModernButton("✓ Add", new Color(46, 204, 113), new Color(39, 174, 96));
        JButton cancelBtn = createModernButton("✗ Cancel", new Color(149, 165, 166), new Color(127, 140, 141));

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                LocalDate date = LocalDate.parse(dateField.getText().trim());
                int interval = Integer.parseInt(intervalField.getText().trim());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vehicle name cannot be empty!", 
                                                 "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                MaintenanceItem newItem = new MaintenanceItem(name, date, interval);
                items.add(newItem);
                updateTable();
                statusLabel.setText("✓ Added: " + name);
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Vehicle added successfully!", 
                                             "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage(), 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private void showAlerts() {
        StringBuilder alertMsg = new StringBuilder();
        alertMsg.append("<html><body style='width: 500px'>");
        alertMsg.append("<h2>Maintenance Alerts</h2>");

        int overdueCount = 0;
        int dueSoonCount = 0;

        alertMsg.append("<h3 style='color: red;'>⚠️ OVERDUE:</h3><ul>");
        for (MaintenanceItem item : items) {
            MaintenanceScheduler.Status status = MaintenanceScheduler.status(item);
            long days = MaintenanceScheduler.daysUntilNext(item);
            
            if (status == MaintenanceScheduler.Status.OVERDUE) {
                overdueCount++;
                if (overdueCount <= 10) { // Show only first 10
                    alertMsg.append("<li><b>").append(item.getName()).append("</b> - ")
                           .append(Math.abs(days)).append(" days overdue</li>");
                }
            }
        }
        if (overdueCount > 10) {
            alertMsg.append("<li><i>...and ").append(overdueCount - 10).append(" more</i></li>");
        }
        if (overdueCount == 0) {
            alertMsg.append("<li>None</li>");
        }
        alertMsg.append("</ul>");

        alertMsg.append("<h3 style='color: orange;'>⏰ DUE SOON (within 7 days):</h3><ul>");
        for (MaintenanceItem item : items) {
            MaintenanceScheduler.Status status = MaintenanceScheduler.status(item);
            long days = MaintenanceScheduler.daysUntilNext(item);
            
            if (status == MaintenanceScheduler.Status.DUE_SOON) {
                dueSoonCount++;
                alertMsg.append("<li><b>").append(item.getName()).append("</b> - ")
                       .append(days).append(" days</li>");
            }
        }
        if (dueSoonCount == 0) {
            alertMsg.append("<li>None</li>");
        }
        alertMsg.append("</ul>");

        alertMsg.append("<p><b>Summary:</b><br>");
        alertMsg.append("Overdue: ").append(overdueCount).append("<br>");
        alertMsg.append("Due Soon: ").append(dueSoonCount).append("<br>");
        alertMsg.append("Total: ").append(items.size()).append("</p>");
        alertMsg.append("</body></html>");

        JOptionPane.showMessageDialog(this, alertMsg.toString(), "Maintenance Alerts", 
                                     JOptionPane.WARNING_MESSAGE);
        statusLabel.setText("Alerts: " + overdueCount + " overdue, " + dueSoonCount + " due soon");
    }

    private void saveData() {
        try {
            storage.save(items);
            JOptionPane.showMessageDialog(this, "Data saved successfully!\n" + items.size() + " items saved.");
            statusLabel.setText("Saved " + items.size() + " items");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Save failed!");
        }
    }

    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
            return;
        }

        String itemName = (String) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete:\n" + itemName + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            items.remove(selectedRow);
            updateTable();
            statusLabel.setText("Deleted: " + itemName);
        }
    }

    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MaintenanceGUI gui = new MaintenanceGUI();
            gui.setVisible(true);
        });
    }
}
