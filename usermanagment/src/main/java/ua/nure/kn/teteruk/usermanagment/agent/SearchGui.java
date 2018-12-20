package ua.nure.kn.teteruk.usermanagment.agent;

import static java.util.Objects.isNull;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;

import javax.swing.*;

import org.dbunit.util.search.SearchException;

import ua.nure.kn.teteruk.usermanagment.User;
import ua.nure.kn.teteruk.usermanagment.gui.UserTableModel;
import ua.nure.kn.teteruk.usermanagment.gui.util.Messages;

public class SearchGui extends JFrame {

    private static final int FRAME_HEIGHT = 400;
    private static final int FRAME_WIDTH = 600;

    private SearchAgent agent;
    private JPanel contentPanel;
    private JTable table;

    public SearchGui(SearchAgent searchAgent) {
        agent = searchAgent;
        init();
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("SearcherFrame.name"));

        this.setContentPane(getContentPanel());
    }

    private JPanel getContentPanel() {
        if (isNull(contentPanel)) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getSearchPanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getSearchPanel() {
        return new SearchPanel(agent);
    }

    public void addUsers(Collection<User> users) {
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.addUsers(users);
        this.repaint();
    }

    private void clearUsers() {
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.clearUsers();
        this.repaint();
    }

    private JTable getTable() {
        if (table == null) {
            table = new JTable();
        }
        table.setName("table");
        initTable();
        return table;
    }

    private void initTable() {
        UserTableModel model;
        model = new UserTableModel(Collections.emptyList());
        table.setModel(model);
    }

    private void showTable() {
        getContentPane().add(getTable(), BorderLayout.CENTER);
        getTable().setVisible(true);
        getTable().repaint();
    }

    private class SearchPanel extends JPanel implements ActionListener {

        private SearchAgent agent;
        private JPanel buttonPanel;
        private JPanel fieldPanel;
        private JButton searchButton;
        private JTextField firstNameField;
        private JTextField lastNameField;

        SearchPanel(SearchAgent agent) {
            this.agent = agent;
            init();
        }

        private void init() {
            this.setName("searchPanel");
            this.setLayout(new BorderLayout());
            this.add(getFieldPanel(), BorderLayout.NORTH);
            add(getButtonsPanel(), BorderLayout.SOUTH);
        }

        private JPanel getButtonsPanel() {
            if (buttonPanel == null) {
                buttonPanel = new JPanel();
                buttonPanel.add(getSearchButton(), null);
            }
            return buttonPanel;
        }

        private JButton getSearchButton() {
            if (searchButton == null) {
                searchButton = new JButton();
                searchButton.setText(Messages.getString("SearchPanel.search"));
                searchButton.setName("searchButton");
                searchButton.setActionCommand("search");
                searchButton.addActionListener(this);
            }
            return searchButton;
        }

        private JPanel getFieldPanel() {
            if (fieldPanel == null) {
                fieldPanel = new JPanel();
                fieldPanel.setLayout(new GridLayout(2, 3));
                addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField());
                addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField());
                fieldPanel.add(getSearchButton());
            }
            return fieldPanel;
        }

        JTextField getLastNameField() {
            if (lastNameField == null) {
                lastNameField = new JTextField();
                lastNameField.setName("lastNameField");
            }
            return lastNameField;
        }

        private void addLabeledField(JPanel panel, String labelText,
                                     JTextField textField) {
            JLabel label = new JLabel(labelText);
            label.setLabelFor(textField);
            panel.add(label);
            panel.add(textField);
        }

        JTextField getFirstNameField() {
            if (firstNameField == null) {
                firstNameField = new JTextField();
                firstNameField.setName("firstNameField");
            }
            return firstNameField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if ("search".equalsIgnoreCase(e.getActionCommand())) {
                String firstName = getFirstNameField().getText();
                String lastName = getLastNameField().getText();
                try {
                    clearUsers();
                    agent.search(firstName, lastName);
                } catch (SearchException e1) {
                    throw new RuntimeException(e1);
                }
            }
            clearFields();
            this.setVisible(false);
            showTable();
        }

        private void clearFields() {
            getFirstNameField().setText("");
            getLastNameField().setText("");
        }
    }
}
