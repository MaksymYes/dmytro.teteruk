package ua.nure.kn.teteruk.usermanagment.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            addButton.setText("Добавить");
            addButton.setName("addButton");
        }
        return addButton;
    }

    private JButton getEditButton() {
        if (isNull(editButton)) {
            editButton = new JButton();
            editButton.setText("Редактировать");
            editButton.setName("editButton");
        }
        return editButton;
    }

    private JButton getDeleteButton() {
        if (isNull(deleteButton)) {
            deleteButton = new JButton();
            deleteButton.setText("Удалить");
            deleteButton.setName("deleteButton");
        }
        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (isNull(detailsButton)) {
            detailsButton = new JButton();
            detailsButton.setText("Подробнее");
            detailsButton.setName("detailsButton");
        }
        return detailsButton;
    }

    private JTable getUserTable() {
        if (isNull(userTable)) {
            userTable = new JTable();
            userTable.setName("userTable");
        }
        return userTable;
    }
}
