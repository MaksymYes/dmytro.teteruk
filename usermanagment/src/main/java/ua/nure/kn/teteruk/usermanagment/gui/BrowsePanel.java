package ua.nure.kn.teteruk.usermanagment.gui;

import ua.nure.kn.teteruk.usermanagment.db.UserDao;
import ua.nure.kn.teteruk.usermanagment.db.exception.DatabaseException;
import ua.nure.kn.teteruk.usermanagment.gui.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import static java.util.Objects.isNull;

public class BrowsePanel extends JPanel implements ActionListener {

    private MainFrame parent;
    private JScrollPane tablePanel;
    private JPanel buttonsPanel;
    private JTable userTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;

    public BrowsePanel(MainFrame mainFrame) {
        parent = mainFrame;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        add(getTablePanel(), BorderLayout.CENTER);
        add(getButtonsPanel(), BorderLayout.SOUTH);
        setName("browsePanel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) {
            parent.showAddPanel();
        } else if ("edit".equalsIgnoreCase(actionCommand)) {
            parent.showEditPanel();
        } else if ("delete".equalsIgnoreCase(actionCommand)) {
            deleteUserAction();
            parent.showBrowsePanel();
        } else if ("details".equalsIgnoreCase(actionCommand)) {
            parent.showDetailPanel();
        } else {
            parent.showBrowsePanel();
        }
    }

    private void deleteUserAction() {
        Long selectedUserId = (Long) userTable.getValueAt(userTable.getSelectedRow(), 0);
        UserDao dao = parent.getDao();
        try {
            dao.delete(dao.find(selectedUserId));
        } catch (DatabaseException e1) {
            JOptionPane.showMessageDialog(this, e1.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JScrollPane getTablePanel() {
        if (isNull(tablePanel)) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JPanel getButtonsPanel() {
        if (isNull(buttonsPanel)) {
            buttonsPanel = new JPanel();
            buttonsPanel.add(getAddButton(), null);
            buttonsPanel.add(getEditButton(), null);
            buttonsPanel.add(getDeleteButton(), null);
            buttonsPanel.add(getDetailsButton(), null);
        }
        return buttonsPanel;
    }

    private JButton getAddButton() {
        if (isNull(addButton)) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.add"));
            addButton.setName("addButton");
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (isNull(editButton)) {
            editButton = new JButton();
            editButton.setText(Messages.getString("BrowsePanel.edit"));
            editButton.setName("editButton");
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);

        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (isNull(deleteButton)) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("BrowsePanel.delete"));
            deleteButton.setName("deleteButton");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (isNull(detailsButton)) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("BrowsePanel.details"));
            detailsButton.setName("detailsButton");
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    public JTable getUserTable() {
        if (isNull(userTable)) {
            userTable = new JTable();
        }
        userTable.setName("userTable");
        return userTable;
    }

    public void initTable() {
        UserTableModel model;
        try {
            model = new UserTableModel(parent.getDao().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(Collections.emptyList());
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        userTable.setModel(model);
    }
}
