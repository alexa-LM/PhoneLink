import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class PhoneBookGUI extends JFrame {
    private JTextField nameField, phoneField, searchField;
    private JButton addButton, deleteButton, searchButton, showAllButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private Map<String, String> contacts;

    public PhoneBookGUI() {
        setTitle("📞 Simple Phone Book");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contacts = new HashMap<>();

        // ===== UI Components =====
        JLabel nameLabel = new JLabel("Name:");
        JLabel phoneLabel = new JLabel("Phone:");
        nameField = new JTextField(15);
        phoneField = new JTextField(15);

        addButton = new JButton("Add Contact");
        deleteButton = new JButton("Delete Contact");
        searchButton = new JButton("Search");
        showAllButton = new JButton("Show All");

        searchField = new JTextField(15);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Name", "Phone"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== Layout =====
        JPanel inputPanel = new JPanel();
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(addButton);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search by name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);
        searchPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        // ===== Event Handlers =====
        addButton.addActionListener(e -> addContact());
        deleteButton.addActionListener(e -> deleteContact());
        searchButton.addActionListener(e -> searchContact());
        showAllButton.addActionListener(e -> showAllContacts());
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        contacts.put(name, phone);
        updateTable();
        nameField.setText("");
        phoneField.setText("");
    }

    private void deleteContact() {
        String name = JOptionPane.showInputDialog(this, "Enter name to delete:");
        if (name != null && contacts.containsKey(name)) {
            contacts.remove(name);
            updateTable();
            JOptionPane.showMessageDialog(this, "Contact deleted.");
        } else if (name != null) {
            JOptionPane.showMessageDialog(this, "Contact not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchContact() {
        String search = searchField.getText().trim().toLowerCase();
        if (search.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a name to search.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);
        contacts.forEach((name, phone) -> {
            if (name.toLowerCase().contains(search)) {
                tableModel.addRow(new Object[]{name, phone});
            }
        });

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No contacts found.");
        }
    }

    private void showAllContacts() {
        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        contacts.forEach((name, phone) -> tableModel.addRow(new Object[]{name, phone}));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PhoneBookGUI().setVisible(true);
        });
    }
}
